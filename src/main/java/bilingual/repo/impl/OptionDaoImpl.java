package bilingual.repo.impl;

import bilingual.dto.response.answer.QuestionOptionResponse;
import bilingual.dto.response.option.OptionsResponse;
import bilingual.entity.enums.QuestionType;
import bilingual.repo.jdbcRepo.OptionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Types;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionDaoImpl implements OptionDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public OptionsResponse getOptionByQuestionId(Long questionId) {
        String questionQuery = """
                               SELECT
                               q.title as questionTitle,
                               q.duration as duration,
                               q.question_type as questionType
                               FROM question q 
                               WHERE q.id = ?
                               GROUP BY q.id;
                              """;

        OptionsResponse question = jdbcTemplate.query(questionQuery, new Object[]{questionId},
                new int[]{Types.INTEGER}, (resultSet, i) ->
                        new OptionsResponse(
                                resultSet.getString("questionTitle"),
                                QuestionType.valueOf(resultSet.getString("questionType")),
                                resultSet.getInt("duration"),
                                null
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Question with id %s was not found", questionId)));

        List<QuestionOptionResponse> questionOptionResponses = getOptions(questionId);
        question.setOptionResponses(questionOptionResponses);
        return question;

    }

    private List<QuestionOptionResponse> getOptions(Long questionId) {
        String optionQuery = """
        SELECT 
        o.title as optionTitle,
        o.file_url,
        o.is_true_option as isCorrectOption,
        o.id as optionId,
        o.question_id as questionId
        FROM Option o 
        JOIN Question q on o.question_id = q.id
        WHERE q.id = ? 
        GROUP BY o.id, o.title, o.file_url, o.is_true_option, o.question_id
        ORDER BY o.id;
        """;

        return jdbcTemplate.query(optionQuery, new Object[]{questionId}, new int[]{Types.INTEGER}, (resultSet, i) ->
                new QuestionOptionResponse(
                        i + 1 ,
                        resultSet.getString("optionTitle"),
                        resultSet.getString("file_url"),
                        resultSet.getBoolean("isCorrectOption"),
                        resultSet.getLong("questionId"),
                        resultSet.getLong("optionId")
                ));
    }
}