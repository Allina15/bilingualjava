package bilingual.repo.jdbcRepo;

import bilingual.dto.response.answer.DescribeImage;
import bilingual.dto.response.answer.HighlightTheAnswer;
import bilingual.dto.response.answer.RecordSayingStatement;
import bilingual.dto.response.answer.TypeWhatYouHear;
import bilingual.dto.response.answer.RespondInNWord;
import bilingual.dto.response.answer.AnswerOptionResponse;
import bilingual.dto.response.answer.QuestionOptionResponse;

import java.util.List;

public interface AnswerDao {

    TypeWhatYouHear typeWhatYouHear(Long answerId);
    RecordSayingStatement recordSayingStatement(Long answerId);
    HighlightTheAnswer highlightAnswer(Long answerId);
    DescribeImage describeImage(Long answerId);
    RespondInNWord respondInNWord(Long answerId);
    AnswerOptionResponse getAnswerWithOption(Long answerId);
    List<QuestionOptionResponse> getOptionByQuestionId(Long questionId);
}
