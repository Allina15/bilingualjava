package bilingual.repo.impl;

import bilingual.dto.response.question.QuestionResponse;
import bilingual.dto.response.test.TestForUpdate;
import bilingual.dto.response.test.TestResponse;
import bilingual.dto.response.test.TestResponses;
import bilingual.entity.enums.QuestionType;
import bilingual.repo.jdbcRepo.TestDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Types;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDaoImpl implements TestDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TestResponses> getAllTest() {
        String testQuery = """
                SELECT
                t.id,
                t.title,
                t.enable,
                t.short_description,
                t.is_passed,
                t.duration
                FROM test t
                ORDER BY t.id DESC
                """;

        return jdbcTemplate.query(testQuery, (resultSet, i) ->
                new TestResponses(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBoolean("enable"),
                        resultSet.getString("short_description"),
                        resultSet.getBoolean("is_passed"),
                        resultSet.getInt("duration")
                ));
    }

    @Override
    public List<TestForUpdate> getTestForUpdate() {
        String testQuery = """
                SELECT
                t.id,
                t.title,
                t.enable
                FROM test t
                ORDER BY t.id
                """;

        return jdbcTemplate.query(testQuery, (resultSet, i) ->
                new TestForUpdate(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getBoolean("enable")
                ));
    }

    @Override
    public TestResponses getTestById(Long testId) {
        String testQuery = """
                SELECT
                t.id,
                t.title,
                t.enable,
                t.short_description,
                t.is_passed,
                t.duration
                FROM test t
                WHERE t.id = ?
                ORDER BY t.id DESC
                """;

        return jdbcTemplate.query(testQuery, new Object[]{testId},
                new int[]{Types.INTEGER}, (resultSet, i) ->
                        new TestResponses(
                                resultSet.getLong("id"),
                                resultSet.getString("title"),
                                resultSet.getBoolean("enable"),
                                resultSet.getString("short_description"),
                                resultSet.getBoolean("is_passed"),
                                resultSet.getInt("duration")
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Test with id %s was not found", testId)));
    }

    @Override
    public TestResponse getAllQuestionByTestId(Long testId) {
        String testQuery = """   
                        SELECT
                        t.id as id,
                        COALESCE((SELECT SUM(duration) FROM question q WHERE q.test_id = t.id AND q.enable = true), 0) AS duration,
                        t.title,
                        t.short_description
                        FROM test t
                        WHERE t.id = ?
                        GROUP BY t.id;
                        """;
        TestResponse test = jdbcTemplate.query(testQuery, new Object[]{testId},
                new int[]{Types.INTEGER}, (resultSet, i) ->
                        new TestResponse(
                                resultSet.getLong("id"),
                                resultSet.getString("title"),
                                resultSet.getString("short_description"),
                                resultSet.getInt("duration"),
                                null
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Test with id %s was not found", testId)));

        String questionQuery = """
                       SELECT
                       q.id as id,
                       q.title as title,
                       q.duration as duration,
                       q.question_type as question_type,
                       q.enable as enable
                       FROM question q
                       JOIN test t ON t.id = q.test_id
                       WHERE t.id = ?
                       ORDER BY q.id;
                       """;
        List<QuestionResponse> questions = jdbcTemplate.query(questionQuery, new Object[]{testId}, new int[]{Types.INTEGER}, (resultSet, i) ->
                new QuestionResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getInt("duration"),
                        QuestionType.valueOf(resultSet.getString("question_type")),
                        resultSet.getBoolean("enable")
                ));

        test.setQuestion(questions);
        return test;
    }
}