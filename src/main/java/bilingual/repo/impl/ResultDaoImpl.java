package bilingual.repo.impl;

import bilingual.dto.response.answer.AnswerResponses;
import bilingual.dto.response.result.MyResultResponse;
import bilingual.dto.response.result.ResultResponse;
import bilingual.dto.response.result.ResultResponses;
import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import bilingual.repo.jdbcRepo.ResultDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultDaoImpl implements ResultDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ResultResponses getAllAnswerByResultId(Long resultId) {
        String resultQuery = """
                      SELECT
                      CONCAT(u.first_name,' ',u.last_name) AS full_name,
                      MAX(a.date_of_submission) AS date_of_submission,
                      t.title AS test_name,
                      CASE WHEN MAX(a.status) = 'EVALUATED' THEN COALESCE(MAX(r.score), 0) ELSE 0 END AS final_score,
                      r.status as status
                      FROM answer a
                      left join result r ON r.id = a.results_id
                      left join question q ON a.question_id = q.id
                      left join users u ON a.user_id = u.id
                      left join test t ON q.test_id = t.id
                      WHERE r.id = ?
                      GROUP BY u.id, full_name, test_name, r.status
                      """;

        ResultResponses result = jdbcTemplate.query(resultQuery, new Object[]{resultId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new ResultResponses(
                                rs.getString("full_name"),
                                rs.getObject("date_of_submission", LocalDateTime.class),
                                rs.getString("test_name"),
                                rs.getDouble("final_score"),
                                Status.valueOf(rs.getString("status")),
                                new ArrayList<>()
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Result with id %s was not found", resultId)));


        if (result != null) {
            LocalDateTime dateTime = result.getDateOfSubmission();
            result.setSubmissionDate(dateTime.toLocalDate());
            result.setSubmissionTime(dateTime.toLocalTime());

            String answerQuery = """
                    SELECT
                    a.is_checked,
                    a.status,
                    a.id AS answer_id,
                    a.user_id,
                    COALESCE(q.test_id, 0) AS test_id,
                    COALESCE(a.score, 0) AS score,
                    q.question_type,
                    ROW_NUMBER() OVER (ORDER BY a.user_id, a.id) AS number
                    FROM answer a
                    LEFT JOIN result r ON r.id = a.results_id
                    LEFT JOIN question q ON a.question_id = q.id
                    LEFT JOIN users u ON a.user_id = u.id
                    WHERE r.id = ? OR r.id IS NULL
                    """;

            List<AnswerResponses> answerResponses = jdbcTemplate.query(answerQuery, new Object[]{resultId},
                    new int[]{Types.INTEGER}, (rs, i) -> {
                        AnswerResponses answerResponse = new AnswerResponses();
                        answerResponse.setIsChecked(rs.getBoolean("is_checked"));
                        answerResponse.setStatus(Status.valueOf(rs.getString("status")));
                        answerResponse.setUserId(rs.getLong("user_id"));
                        answerResponse.setAnswerId(rs.getLong("answer_id"));
                        answerResponse.setTestId(rs.getLong("test_id"));
                        answerResponse.setScore(rs.getDouble("score"));
                        answerResponse.setNumber(rs.getInt("number"));
                        answerResponse.setQuestionType(QuestionType.valueOf(rs.getString("question_type")));
                        return answerResponse;
                    });
            result.setAnswerResponses(answerResponses);
        }
        return result;
    }

    @Override
    public List<MyResultResponse> getAll(Long userId) {
        String myResultQuery = """
                SELECT
                r.id as result_id,
                r.date_of_submission as date_of_submission,
                t.title as test_name,
                r.status as status,
                r.score score
                FROM result r
                JOIN test t ON r.test_id = t.id
                JOIN users u ON r.user_id = u.id
                WHERE r.user_id = ?
                """;

        return jdbcTemplate.query(myResultQuery, (result, i) ->
                        new MyResultResponse(
                                result.getLong("result_id"),
                                result.getObject("date_of_submission", LocalDateTime.class),
                                result.getString("test_name"),
                                Status.valueOf(result.getString("status")),
                                result.getInt("score")
                        ), userId)
                .stream()
                .peek(myResultResponse -> {
                    LocalDateTime dateTime = myResultResponse.getDateOfSubmission();
                    myResultResponse.setSubmissionDate(dateTime.toLocalDate());
                    myResultResponse.setSubmissionTime(dateTime.toLocalTime());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultResponse> getAllTestResults() {
        String sql = """
        SELECT
            full_name,
            date_of_submission,
            title,
            is_seen,
            status,
            user_id,
            test_id,
            result_id,
            score,
            ROW_NUMBER() OVER (ORDER BY user_id, test_id) AS number
        FROM (
            SELECT
                CONCAT(u.first_name,' ',u.last_name) AS full_name,
                MAX(r.date_of_submission) AS date_of_submission,
                t.title,
                r.is_seen,
                r.status,
                r.id as result_id,
                u.id AS user_id,
                t.id AS test_id,
                r.score AS score
            FROM result r
                JOIN answer a ON r.id = a.results_id
                JOIN question q ON a.question_id = q.id
                JOIN users u ON a.user_id = u.id
                JOIN test t ON q.test_id = t.id
            GROUP BY full_name, t.title, r.is_seen, r.status, u.id, t.id, r.score, r.id
            ORDER BY date_of_submission DESC
        ) AS subquery;
        """;

        return jdbcTemplate.query(sql, ((rs, rowNum) -> {
            ResultResponse testResult = new ResultResponse();
            testResult.setUserFullName(rs.getString("full_name"));
            testResult.setDateOfSubmission(rs.getObject("date_of_submission", LocalDateTime.class));
            testResult.setTestName(rs.getString("title"));
            testResult.setChecked(rs.getBoolean("is_seen"));
            testResult.setStatus(Status.valueOf(rs.getString("status")));
            testResult.setUserId(rs.getLong("user_id"));
            testResult.setTestId(rs.getLong("test_id"));
            testResult.setResultId(rs.getLong("result_id"));
            testResult.setScore(rs.getInt("score"));
            testResult.setNumber(rs.getInt("number"));
            LocalDateTime dateTime = testResult.getDateOfSubmission();
            testResult.setSubmissionDate(dateTime.toLocalDate());
            testResult.setSubmissionTime(dateTime.toLocalTime());
            return testResult;
        }));
    }}



