package bilingual.repo.impl;

import bilingual.dto.response.answer.HighlightTheAnswer;
import bilingual.dto.response.answer.RecordSayingStatement;
import bilingual.dto.response.answer.TypeWhatYouHear;
import bilingual.dto.response.answer.DescribeImage;
import bilingual.dto.response.answer.RespondInNWord;
import bilingual.dto.response.answer.QuestionOptionResponse;
import bilingual.dto.response.answer.UserOptionResponse;
import bilingual.dto.response.answer.AnswerOptionResponse;
import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import bilingual.repo.jdbcRepo.AnswerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerDaoImpl implements AnswerDao{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public TypeWhatYouHear typeWhatYouHear(Long answerId) {
        String answerQuery = """
                SELECT
                q.duration,
                q.question_type,
                a.score,
                q.attempts as questionAttempts,
                q.correct_answer,
                q.file_url,
                a.id as answerId,
                a.data as userAnswer,
                a.attempts as answerAttempts,
                t.title as testTitle,
                q.title as questionTitle,
                concat(u.first_name, ' ', u.last_name) as full_name,
                a.status as status
                FROM Answer a
                LEFT JOIN Question q ON q.id = a.question_id
                LEFT JOIN Test t ON q.test_id = t.id
                LEFT JOIN Users u ON a.user_id = u.id
                WHERE a.id = ?;
                """;

        return jdbcTemplate.query(answerQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new TypeWhatYouHear(
                                rs.getLong("answerId"),
                                rs.getString("questionTitle"),
                                rs.getString("testTitle"),
                                rs.getInt("duration"),
                                QuestionType.valueOf(rs.getString("question_type")),
                                rs.getDouble("score"),
                                rs.getString("file_url"),
                                rs.getInt("questionAttempts"),
                                rs.getString("correct_answer"),
                                rs.getString("userAnswer"),
                                rs.getInt("answerAttempts"),
                                rs.getString("full_name"),
                                Status.valueOf(rs.getString("status"))
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Answer with id %s was not found", answerId)));
    }

    @Override
    public RecordSayingStatement recordSayingStatement(Long answerId) {
        String answerQuery = """
            SELECT
            q.duration,
            q.statement as statement,
            q.question_type,
            a.score,
            q.correct_answer as correct_answer,
            a.id as answerId,
            a.audio_file,
            t.title as testTitle,
            q.title as questionTitle,
            concat(u.first_name,' ',u.last_name) as full_name,
            a.status as status
            FROM Answer a
            LEFT JOIN Question q ON q.id = a.question_id
            LEFT JOIN Test t ON q.test_id = t.id
            LEFT JOIN Users u ON a.user_id = u.id
            WHERE a.id = ?; 
            """;
        return jdbcTemplate.query(answerQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new RecordSayingStatement(
                                rs.getLong("answerId"),
                                rs.getInt("duration"),
                                QuestionType.valueOf(rs.getString("question_type")),
                                rs.getDouble("score"),
                                rs.getString("audio_file"),
                                rs.getString("correct_answer"),
                                rs.getString("testTitle"),
                                rs.getString("questionTitle"),
                                rs.getString("full_name"),
                                rs.getString("statement"),
                                Status.valueOf(rs.getString("status"))
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Answer with id %s was not found", answerId)));
    }

    @Override
    public HighlightTheAnswer highlightAnswer(Long answerId) {
        String answerQuery = """
                SELECT
                q.duration,
                q.question_type,
                a.score,
                q.title as questionTitle,
                q.correct_answer as correctAnswer,
                q.statement as statement,
                q.passage as passage,
                a.id as answerId,
                a.data as userAnswer,
                t.title as testTitle,
                t.id as testId,
                concat(u.first_name,' ',u.last_name) as full_name,
                a.status as status
                FROM answer a
                LEFT JOIN question q ON q.id = a.question_id
                LEFT JOIN test t ON q.test_id = t.id
                LEFT JOIN users u ON a.user_id = u.id
                WHERE a.id = ?
                """;
        return jdbcTemplate.query(answerQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new HighlightTheAnswer(
                                rs.getLong("answerId"),
                                rs.getLong("testId"),
                                rs.getInt("duration"),
                                QuestionType.valueOf(rs.getString("question_type")),
                                rs.getDouble("score"),
                                rs.getString("questionTitle"),
                                rs.getString("correctAnswer"),
                                rs.getString("statement"),
                                rs.getString("passage"),
                                rs.getString("userAnswer"),
                                rs.getString("testTitle"),
                                rs.getString("full_name"),
                                Status.valueOf(rs.getString("status"))
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Answer with id %s was not found", answerId)));
    }

    @Override
    public DescribeImage describeImage(Long answerId) {
        String answerQuery = """
            SELECT
            q.duration,
            q.question_type,
            a.score,
            q.file_url as fileUrl,
            q.title as questionTitle,
            q.correct_answer as correctAnswer,
            a.id as answerId,
            a.data as userAnswer,
            t.title as testTitle,
            t.id as testId,
            concat(u.first_name,' ',u.last_name) as full_name,
            a.status as status
           FROM answer a
           LEFT JOIN question q ON q.id = a.question_id
           LEFT JOIN test t ON q.test_id = t.id
           LEFT JOIN users u ON a.user_id = u.id
           WHERE a.id = ?
           """;
        return jdbcTemplate.query(answerQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new DescribeImage(
                                rs.getLong("answerId"),
                                rs.getLong("testId"),
                                rs.getInt("duration"),
                                QuestionType.valueOf(rs.getString("question_type")),
                                rs.getDouble("score"),
                                rs.getString("fileUrl"),
                                rs.getString("questionTitle"),
                                rs.getString("correctAnswer"),
                                rs.getString("userAnswer"),
                                rs.getString("testTitle"),
                                rs.getString("full_name"),
                                Status.valueOf(rs.getString("status"))
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Answer with id %s was not found", answerId)));
    }

    @Override
    public RespondInNWord respondInNWord(Long answerId) {
        String answerQuery = """
            SELECT
            q.duration,
            q.question_type,
            a.score,
            q.title as questionTitle,
            q.statement as statement,
            q.attempts as questionAttempts,
            a.id as answerId,
            a.data as userAnswer,
            a.attempts as answerAttempts,
            t.title as testTitle,
            t.id as testId,
            concat(u.first_name,' ',u.last_name) as full_name,
            a.status as status
           FROM answer a
           LEFT JOIN question q ON q.id = a.question_id
           LEFT JOIN test t ON q.test_id = t.id
           LEFT JOIN users u ON a.user_id = u.id
           WHERE a.id = ?
           """;
        return jdbcTemplate.query(answerQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new RespondInNWord(
                                rs.getLong("answerId"),
                                rs.getLong("testId"),
                                rs.getString("questionTitle"),
                                rs.getString("testTitle"),
                                rs.getInt("duration"),
                                QuestionType.valueOf(rs.getString("question_type")),
                                rs.getDouble("score"),
                                rs.getString("statement"),
                                rs.getInt("questionAttempts"),
                                rs.getString("userAnswer"),
                                rs.getInt("answerAttempts"),
                                rs.getString("full_name"),
                                Status.valueOf(rs.getString("status"))
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Answer with id %s was not found", answerId)));
    }

    @Override
    public List<QuestionOptionResponse> getOptionByQuestionId(Long questionId) {
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
        """;
        
        return jdbcTemplate.query(optionQuery, new Object[]{questionId}, new int[]{Types.INTEGER}, (resultSet, i) ->
                new QuestionOptionResponse(
                        i + 1 ,
                        resultSet.getString("optionTitle"),
                        resultSet.getString("file_url"),
                        resultSet.getBoolean("is_correct_option"),
                        resultSet.getLong("questionId"),
                        resultSet.getLong("optionId")
                ));
    }

    @Override
    public AnswerOptionResponse getAnswerWithOption(Long answerId) {
        String answerQuery = """
        SELECT
        q.duration,
        q.question_type,
        q.id as questionId,
        q.passage,
        a.score,
        q.title as questionTitle,
        a.id as answerId,
        t.title as testTitle,
        t.id as testId,
        concat(u.first_name,' ',u.last_name) as full_name,
        a.status as status
       FROM answer a
       LEFT JOIN question q ON q.id = a.question_id
       LEFT JOIN test t ON q.test_id = t.id
       LEFT JOIN users u ON a.user_id = u.id
       WHERE a.id = ?
       """;

        AnswerOptionResponse answerOptionResponse = jdbcTemplate.query(answerQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new AnswerOptionResponse(
                                rs.getLong("answerId"),
                                rs.getLong("testId"),
                                rs.getLong("questionId"),
                                rs.getString("questionTitle"),
                                rs.getString("testTitle"),
                                rs.getInt("duration"),
                                QuestionType.valueOf(rs.getString("question_type")),
                                rs.getDouble("score"),
                                rs.getString("full_name"),
                                rs.getString("passage"),
                                Status.valueOf(rs.getString("status")),
                                null,
                                null
                        )).stream().findAny().orElseThrow(
                () -> new NotFoundException(String.format("Answer with id %s was not found", answerId)));

        List<AnswerOptionResponse> answer = new ArrayList<>();
        answer.add(answerOptionResponse);
        answer.forEach(question -> {
            List<QuestionOptionResponse> options = getOptionByQuestionId(question.getQuestionId());
            question.setQuestionOptionResponses(options);
        });

        String optionQuery = """
               SELECT
               o.id AS optionId,
               o.title AS optionTitle,
               o.file_url,
               o.is_true_option as is_correct_option,
               ao.answer_id as answerId
               FROM option o
               LEFT JOIN answer_options ao ON o.id = ao.options_id
               WHERE ao.answer_id = ?
               """;

        List<UserOptionResponse> optionResponses = jdbcTemplate.query(optionQuery, new Object[]{answerId},
                new int[]{Types.INTEGER}, (rs, i) ->
                        new UserOptionResponse(
                                i + 1,
                                rs.getString("optionTitle"),
                                rs.getString("file_url"),
                                rs.getBoolean("is_correct_option"),
                                rs.getLong("optionId"),
                                rs.getLong("answerId")
                        ));

        answer.forEach(question -> {
            List<UserOptionResponse> optionResponseList = optionResponses.stream()
                    .filter(option -> option.getAnswerId().equals(question.getAnswerId()))
                    .collect(Collectors.toList());
            question.setUserOptionResponses(optionResponseList);
        });
        return answerOptionResponse;
    }
}

