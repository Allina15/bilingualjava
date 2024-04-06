package bilingual.service.impl;

import bilingual.dto.request.answer.AnswerRequest;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.entity.Option;
import bilingual.entity.Question;
import bilingual.entity.Answer;
import bilingual.entity.Test;
import bilingual.entity.User;
import bilingual.entity.Result;
import bilingual.entity.enums.QuestionType;
import bilingual.entity.enums.Status;
import bilingual.repo.*;
import bilingual.repo.jdbcRepo.AnswerDao;
import bilingual.service.AnswerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bilingual.entity.enums.QuestionType.SELECT_MAIN_IDEA;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final OptionRepo optionRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;
    private final ResultRepo resultRepo;
    private final UserRepo userRepo;
    private final AnswerDao answerDao;

    @Override
    public SimpleResponse saveUserAnswer(List<AnswerRequest> answerRequests) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("User must be authenticated to create the answers");
        }

        String email = authentication.getName();
        User user = userRepo.findUserByUserInfoEmail(email);

        for (AnswerRequest answerRequest : answerRequests) {
            Question question = questionRepo.findById(answerRequest.getQuestionID()).orElseThrow(() -> {
                log.warn(String.format("Question with ID %d is not found", answerRequest.getQuestionID()));
                return new NotFoundException("Question not found");
            });

            if (answerRepo.existsByUserAndQuestion(user, question)) {
                log.warn(String.format("Answer for question ID %d already exists for user %s",
                        answerRequest.getQuestionID(), user.getFirstName()));
                throw new IllegalStateException("Your answer has already been submitted");
            }

            switch (question.getQuestionType()) {
                case DESCRIBE_IMAGE:
                case RECORD_SAYING:
                case RESPOND_IN_AT_LEAST_N_WORDS:
                case HIGHLIGHTS_THE_ANSWER:
                case TYPE_WHAT_YOU_HEAR:
                    saveAnswerWithoutOptions(answerRequest, user, question);
                    break;
                default:
                    saveAnswerWithOptions(answerRequest, user, question);
            }
        }
        return new SimpleResponse(HttpStatus.CREATED, "Your answer is successfully submitted");
    }

    private void saveAnswerWithoutOptions(AnswerRequest answerRequest, User user, Question question) {
        Test test = question.getTest();

        Answer answer = new Answer();
        answer.setUser(user);
        answer.setStatus(Status.NOT_EVALUATED);
        answer.setIsChecked(false);
        ZoneId zoneId = ZoneId.of("Asia/Bishkek");
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        answer.setDateOfSubmission(currentDateTime.toLocalDateTime());
        answer.setQuestion(question);
        test.setIsPassed(true);

        if(answerRequest.getInput() != null && !answerRequest.getInput().isEmpty()){
            answer.setData(answerRequest.getInput());
        }

        if (answerRequest.getAttempts() != null) {
            answer.setAttempts(answerRequest.getAttempts());
        }

        if (answerRequest.getAudioFile() != null &&
                question.getQuestionType().equals(QuestionType.RECORD_SAYING)) {
            answer.setAudioFile(answerRequest.getAudioFile());
        }

        checkAnswerWithoutOptions(question.getTest(), user, Collections.singletonList(answer));
        answerRepo.save(answer);
    }

    private void checkAnswerWithoutOptions(Test test, User user, List<Answer> answers) {
        boolean existsResult = resultRepo.existsByUserIdAndTestId(user.getId(), test.getId());

        for (Answer answer : answers) {
            if (!existsResult) {
                List<Answer> answerList = new ArrayList<>();
                answerList.add(answer);
                Result result = Result.builder()
                        .test(test)
                        .user(user)
                        .dateOfSubmission(answer.getDateOfSubmission())
                        .status(Status.NOT_EVALUATED)
                        .score(0)
                        .answer(answerList)
                        .isSeen(false)
                        .build();
                answer.setResults(result);
                resultRepo.save(result);
            } else {
                Result result = resultRepo.findResultByUserIdAndTestId(user.getId(), test.getId()).orElseThrow(() -> {
                    log.warn("Result with user ID: " + user.getId() + "and with test ID: " + test.getId() + " is not found");
                    return new EntityNotFoundException("Result for user is does not exist!");
                });

                result.getAnswer().add(answer);
                answer.setResults(result);
                answerRepo.save(answer);
            }

            log.info("Result processing completed for user {} and test {}", user.getFirstName(), test.getId());
            new SimpleResponse(HttpStatus.OK, "Result successfully saved");
        }
    }

    public void saveAnswerWithOptions(AnswerRequest answerRequest, User user, Question question) {
        List<Option> options = optionRepo.findAllById(answerRequest.getOptionId());
        Test test = question.getTest();

        if ((question.getQuestionType() == SELECT_MAIN_IDEA ||
                question.getQuestionType() == QuestionType.SELECT_THE_BEST_TITLE) &&
                options.size() != 1) {
            log.warn("For this question, you can choose only one option.");
            throw new IllegalArgumentException("For this question, you can choose only one option.");
        }

        Answer answer = new Answer();
        answer.setUser(user);
        answer.setOptions(options);
        answer.setQuestion(question);
        answer.setStatus(Status.NOT_EVALUATED);
        answer.setIsChecked(false);
        ZoneId zoneId = ZoneId.of("Asia/Bishkek");
        ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);
        answer.setDateOfSubmission(currentDateTime.toLocalDateTime());
        test.setIsPassed(true);
        checkAnswerWithOptions(question.getTest(), user, Collections.singletonList(answer));
    }

    public void checkAnswerWithOptions(Test test, User user, List<Answer> answers) {
        boolean existsResult = resultRepo.existsByUserIdAndTestId(user.getId(), test.getId());

        for (Answer answer : answers) {
            Question question = answer.getQuestion();
            List<Option> options = optionRepo.getAllByQuestionId(question.getId());

            long correctOptionsCount = question.getOption().stream()
                    .filter(Option::getIsTrueOption)
                    .count();

            double score;
            long rounded = 0;
            switch (question.getQuestionType()) {
                case SELECT_REAL_ENGLISH_WORD:
                case LISTEN_AND_SELECT_WORD:
                    int toCompare = answer.getOptions().size();
                    if(options.size() == toCompare){
                        answer.setScore(0);
                        answer.setStatus(Status.EVALUATED);
                        answer.setIsChecked(true);
                        break;
                    }
                    long selectedOptionsCount = answer.getOptions().stream()
                            .filter(Option::getIsTrueOption)
                            .count();
                    if (correctOptionsCount > 0) {
                        score = ((double) selectedOptionsCount / correctOptionsCount) * 10;
                        rounded = Math.round(score);
                        log.info("========={}========={}========={}", rounded,selectedOptionsCount,correctOptionsCount);
                    }
                    break;

                case SELECT_MAIN_IDEA:
                case SELECT_THE_BEST_TITLE:
                    long selectedCorrectOptionsCount = answer.getOptions().stream()
                            .filter(Option::getIsTrueOption)
                            .count();
                    if (selectedCorrectOptionsCount == 1) {
                        rounded = 10;
                        log.info("========={}=========", selectedCorrectOptionsCount);
                    }
                    break;
                default:
                    break;
            }
            answer.setScore(rounded);
            answer.setStatus(Status.EVALUATED);
            answer.setIsChecked(true);

            if (!existsResult) {
                List<Answer> answerList = new ArrayList<>();
                answerList.add(answer);
                Result result = Result.builder()
                        .test(test)
                        .user(user)
                        .dateOfSubmission(answer.getDateOfSubmission())
                        .status(Status.NOT_EVALUATED)
                        .score(0)
                        .answer(answerList)
                        .isSeen(false)
                        .build();
                answer.setResults(result);
                resultRepo.save(result);
            } else {
                Result result = resultRepo.findResultByUserIdAndTestId(user.getId(), test.getId()).orElseThrow(() -> {
                    log.warn("Result with user ID: " + user.getId() + "and with test ID: " + test.getId() + " is not found");
                    return new EntityNotFoundException("Result for user is does not exist");
                });

                result.getAnswer().add(answer);
                answer.setResults(result);
                answerRepo.save(answer);
            }
            log.info("Result processing completed for user {} and test {}", user.getFirstName(), test.getId());
            new SimpleResponse(HttpStatus.OK, "Result successfully saved");
        }
    }

    @Override
    public Object retrieveAnswerByQuestionType(Long answerId) {
        Answer answers = answerRepo.findById(answerId).orElseThrow(() -> {
            log.error("Answer with id : " + answerId + " is not found!!");
            return new NotFoundException("Answer with id : " + answerId + " is not found");
        });

        QuestionType questionType = answers.getQuestion().getQuestionType();

        return switch (questionType) {
            case TYPE_WHAT_YOU_HEAR -> answerDao.typeWhatYouHear((answerId));
            case RECORD_SAYING -> answerDao.recordSayingStatement(answerId);
            case HIGHLIGHTS_THE_ANSWER -> answerDao.highlightAnswer(answerId);
            case DESCRIBE_IMAGE -> answerDao.describeImage(answerId);
            case RESPOND_IN_AT_LEAST_N_WORDS -> answerDao.respondInNWord(answerId);
            case SELECT_MAIN_IDEA, SELECT_THE_BEST_TITLE, SELECT_REAL_ENGLISH_WORD,
                    LISTEN_AND_SELECT_WORD -> answerDao.getAnswerWithOption(answerId);
            default -> throw new IllegalArgumentException("Unsupported QuestionType: " + questionType);
        };
    }
}

