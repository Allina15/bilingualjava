package bilingual.repo.impl;

import bilingual.dto.response.answer.QuestionOptionResponse;
import bilingual.dto.response.option.OptionResponses;
import bilingual.dto.response.question.QuestionResponses;
import bilingual.dto.response.question.QuestionsResponse;
import bilingual.entity.enums.QuestionType;
import bilingual.repo.jdbcRepo.QuestionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public QuestionsResponse getQuestionById(Long questionId) {
        String questionQuery = """
                SELECT
                q.id,
                q.title,
                q.duration,
                q.question_type,
                q.statement,
                q.passage,
                q.attempts,
                q.correct_answer,
                q.file_url,
                q.enable
                FROM question q WHERE 
                q.id = ?
                """;

        QuestionsResponse question = jdbcTemplate.query(questionQuery, new Object[]{questionId},
                new int[]{Types.INTEGER}, (resultSet, i) ->
                        new QuestionsResponse(
                                resultSet.getLong("id"),
                                resultSet.getString("title"),
                                resultSet.getInt("duration"),
                                QuestionType.valueOf(resultSet.getString("question_type")),
                                resultSet.getString("statement"),
                                resultSet.getString("passage"),
                                resultSet.getInt("attempts"),
                                resultSet.getString("correct_answer"),
                                resultSet.getString("file_url"),
                                resultSet.getBoolean("enable"),
                                null
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Question with id %s was not found", questionId)));

        String optionQuery = """
        SELECT 
        o.title as optionTitle,
        o.file_url,
        o.is_true_option as is_correct_option,
        o.id as optionId,
        o.question_id as questionId
        FROM Option o 
        JOIN Question q on o.question_id = q.id
        WHERE q.id = ? 
        GROUP BY o.id, o.title, o.file_url, o.is_true_option, o.question_id
        ORDER BY o.id
        """;

        List<QuestionOptionResponse> questionOptionResponses = jdbcTemplate.query(optionQuery, new Object[]{questionId}, new int[]{Types.INTEGER}, (resultSet, i) ->
                new QuestionOptionResponse(
                        i + 1 ,
                        resultSet.getString("optionTitle"),
                        resultSet.getString("file_url"),
                        resultSet.getBoolean("is_correct_option"),
                        resultSet.getLong("questionId"),
                        resultSet.getLong("optionId")
                ));

       question.setQuestionOptionResponses(questionOptionResponses);
       return question;

    }

    @Override
    public List<QuestionResponses> getAllQuestionToPassTest(Long testId) {
        String questionQuery = """
                   SELECT
                   q.id AS question_id,
                   t.id AS test_id,
                   q.title,
                   q.question_type,
                   q.duration,
                   q.attempts,
                   q.statement,
                   q.passage,
                   q.file_url,
                   q.enable
                   FROM question q
                   JOIN test t ON t.id = q.test_id
                   WHERE q.enable = true and t.id = ?;
                   """;
        List<QuestionResponses> questions = jdbcTemplate.query(
                questionQuery, new Object[]{testId},
                (resultSet, i) -> new QuestionResponses(
                        resultSet.getLong("question_id"),
                        resultSet.getLong("test_Id"),
                        resultSet.getString("title"),
                        resultSet.getInt("duration"),
                        QuestionType.valueOf(resultSet.getString("question_type")),
                        resultSet.getString("statement"),
                        resultSet.getString("passage"),
                        resultSet.getInt("attempts"),
                        resultSet.getString("file_url"),
                        resultSet.getBoolean("enable"),
                        null
                ));

        String optionQuery = """
               SELECT
               o.id AS option_id,
               o.title AS optionTitle,
               o.file_url,
               o.question_id
               FROM option o
               GROUP BY o.id;
               """;

        List<OptionResponses> optionResponses = jdbcTemplate.query(
                optionQuery,
                (resultSet, i) -> new OptionResponses(
                        resultSet.getLong("option_id"),
                        resultSet.getString("optionTitle"),
                        resultSet.getString("file_url"),
                        resultSet.getLong("question_id")
                ));

        questions.forEach(question -> {
            List<OptionResponses> optionResponseList = optionResponses.stream()
                    .filter(option -> option.getQuestionId().equals(question.getQuestionId()))
                    .collect(Collectors.toList());
            question.setOptionResponses(optionResponseList);
        });
        return questions;
    }
}