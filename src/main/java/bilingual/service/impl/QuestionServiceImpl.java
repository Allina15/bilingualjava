package bilingual.service.impl;

import bilingual.dto.request.option.OptionRequest;
import bilingual.dto.request.question.OptionUpdate;
import bilingual.dto.request.question.QuestionRequest;
import bilingual.dto.request.question.UpdateRequest;
import bilingual.dto.response.question.QuestionResponse;
import bilingual.dto.response.question.QuestionResponses;
import bilingual.dto.response.question.QuestionsResponse;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.dto.response.test.TestResponse;
import bilingual.entity.Test;
import bilingual.entity.Question;
import bilingual.entity.Option;
import bilingual.entity.User;
import bilingual.entity.UserInfo;
import bilingual.entity.Result;
import bilingual.entity.enums.QuestionType;
import bilingual.exception.InvalidQuestionException;
import bilingual.repo.OptionRepo;
import bilingual.repo.QuestionRepo;
import bilingual.repo.TestRepo;
import bilingual.repo.UserInfoRepo;
import bilingual.repo.jdbcRepo.QuestionDao;
import bilingual.repo.jdbcRepo.TestDao;
import bilingual.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final TestRepo testRepo;
    private final QuestionRepo questionRepo;
    private final OptionRepo optionRepo;
    private final QuestionDao questionDao;
    private final UserInfoRepo userInfoRepo;
    private final TestDao testDao;

    @Override
    public SimpleResponse createQuestion(Long testId, QuestionType questionType, QuestionRequest questionRequest) {
        Test test = testRepo.getTestById(testId).orElseThrow(() -> {
            String message = "Test is does not exist to add the questions";
            log.error(message);
            return new NotFoundException(message);
        });

        Question question = createQuestion(test, questionType, questionRequest);

        testRepo.save(test);
        questionRepo.save(question);

        log.info("Question is successfully created ({})", questionType);
        return new SimpleResponse(
                HttpStatus.CREATED,
                "Question is successfully saved");
    }

    private Question createQuestion(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        switch (questionType) {
            case SELECT_REAL_ENGLISH_WORD,
                    LISTEN_AND_SELECT_WORD -> {
                return createQuestionWithOptions(test, questionType, questionRequest);
            }
            case RECORD_SAYING -> {
                return createRecordSayingStatement(test, questionType, questionRequest);
            }
            case TYPE_WHAT_YOU_HEAR -> {
                return createTypeWhatYouHear(test, questionType, questionRequest);
            }
            case DESCRIBE_IMAGE -> {
                return createDescribeImage(test, questionType, questionRequest);
            }
            case HIGHLIGHTS_THE_ANSWER -> {
                return highlightTheAnswer(test, questionType, questionRequest);
            }
            case RESPOND_IN_AT_LEAST_N_WORDS -> {
                return respondInAtLeastNWords(test, questionType, questionRequest);
            }
            case SELECT_MAIN_IDEA,
                    SELECT_THE_BEST_TITLE -> {
                return createSelectTheBestTitleAndSelectTheMainIdea(test, questionType, questionRequest);
            }
            default -> throw new BadCredentialsException("Question saving failed");
        }
    }

    private Question createQuestionWithOptions(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.title())
                .questionType(questionType)
                .duration(questionRequest.duration())
                .enable(false)
                .test(test)
                .build();

        if (questionType == QuestionType.LISTEN_AND_SELECT_WORD) {
            validateAudioUrl(questionRequest.option());
        }
        if (questionRequest.option() != null) {
            List<Option> options = questionRequest.option().stream()
                    .map(optionRequest -> createOption(question, optionRequest))
                    .collect(Collectors.toList());

            optionRepo.saveAll(options);
        } else {
            log.warn("No options provided for the question");
        }
        return question;
    }

    private Option createOption(Question question, OptionRequest optionRequest) {
        Option option = new Option();
        option.setTitle(optionRequest.getOptionTitle());
        option.setIsTrueOption(optionRequest.getIsCorrectOption());
        option.setQuestion(question);

        if (question.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
            option.setFileUrl(optionRequest.getFileUrl());
        }
        return option;
    }

    private void validateAudioUrl(List<OptionRequest> options) {
        if (options.stream().anyMatch(optionRequest ->
                optionRequest.getFileUrl() == null || optionRequest.getFileUrl().isEmpty())) {
            log.error("Option must contain audio file");
            throw new BadCredentialsException("Option must contain audio file");
        }
    }

    private Question respondInAtLeastNWords(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.title())
                .questionType(questionType)
                .duration(questionRequest.duration())
                .attempts(questionRequest.attempts())
                .statement(questionRequest.statement())
                .enable(false)
                .test(test)
                .build();
        testRepo.save(test);
        questionRepo.save(question);
        return question;
    }

    private Question createTypeWhatYouHear(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.title())
                .questionType(questionType)
                .duration(questionRequest.duration())
                .correctAnswer(questionRequest.correctAnswer())
                .fileUrl(questionRequest.fileUrl())
                .attempts(questionRequest.attempts())
                .enable(false)
                .test(test)
                .build();
        testRepo.save(test);
        questionRepo.save(question);
        return question;
    }

    private Question highlightTheAnswer(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.title())
                .statement(questionRequest.statement())
                .questionType(questionType)
                .duration(questionRequest.duration())
                .correctAnswer(questionRequest.correctAnswer())
                .passage(questionRequest.passage())
                .enable(false)
                .test(test)
                .build();
        testRepo.save(test);
        questionRepo.save(question);
        return question;
    }

    private Question createDescribeImage(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.title())
                .duration(questionRequest.duration())
                .questionType(questionType)
                .fileUrl(questionRequest.fileUrl())
                .correctAnswer(questionRequest.correctAnswer())
                .enable(false)
                .test(test)
                .build();
        questionRepo.save(question);
        testRepo.save(test);
        return question;
    }

    private Question createRecordSayingStatement(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        return Question.builder()
                .title(questionRequest.title())
                .questionType(questionType)
                .statement(questionRequest.statement())
                .correctAnswer(questionRequest.correctAnswer())
                .duration(questionRequest.duration())
                .enable(false)
                .test(test)
                .build();
    }

    private Question createSelectTheBestTitleAndSelectTheMainIdea(Test test, QuestionType questionType, QuestionRequest questionRequest) {
        Question question = Question.builder()
                .title(questionRequest.title())
                .questionType(questionType)
                .duration(questionRequest.duration())
                .passage(questionRequest.passage())
                .enable(false)
                .test(test)
                .build();

        int trueOptionCount = 0;
        List<Option> optionList = new ArrayList<>(questionRequest.option().size());

        for (OptionRequest optionRequest : questionRequest.option()) {
            Option option = new Option();
            option.setTitle(optionRequest.getOptionTitle());
            option.setIsTrueOption(Boolean.TRUE.equals(optionRequest.getIsCorrectOption()));

            if (option.getIsTrueOption()) {
                trueOptionCount++;
                if (trueOptionCount > 1) {
                    log.error("Only one correct answer must be provided!");
                    throw new InvalidQuestionException("Only one correct answer must be provided");
                }
            }
            option.setQuestion(question);
            optionList.add(option);
        }
        optionRepo.saveAll(optionList);
        testRepo.save(test);
        questionRepo.save(question);

        return question;
    }

    @Override
    public QuestionsResponse getQuestionById(Long questionId) {
        return questionDao.getQuestionById(questionId);
    }

    @Override
    public List<QuestionResponses> getAllQuestionToPassTest(Long testId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserInfo user = userInfoRepo.findByEmail(email).orElseThrow(() -> {
            log.error(String.format("User with email %s is not found !!!", email));
            return new NotFoundException("User is not found !!!");
        });
        User user1 = user.getUser();
        for (Result result : user1.getResults()) {
            if (result.getTest().getId().equals(testId)) {
                if (result.getTest().getIsPassed()) {
                    log.error("You have already passed the test");
                    throw new IllegalStateException("Oops. You have already passed the test");
                }
            }
        }
        return questionDao.getAllQuestionToPassTest(testId);
    }

    @Override
    public TestResponse updateQuestionBoolean(Long questionId, Boolean isEnable) {
        Question question = questionRepo.findById(questionId).orElseThrow(() -> {
            log.warn(String.format("Question with: " + questionId + " is not found"));
            return new NotFoundException("Question is not found");
        });

        Test test = question.getTest();
        question.setEnable(isEnable);
        questionRepo.save(question);

        TestResponse testResponse = testDao.getAllQuestionByTestId(test.getId());
        int totalDuration = testResponse.getQuestion().stream()
                .filter(QuestionResponse::getEnable)
                .mapToInt(QuestionResponse::getDuration)
                .sum();

        test.setDuration(totalDuration);
        testRepo.save(test);
        log.info("Question with ID {} successfully updated (isEnable={})", questionId, isEnable);
        return testDao.getAllQuestionByTestId(test.getId());
    }

    @Override
    public TestResponse deleteQuestion(Long questionId) {
        Question question = questionRepo.findById(questionId).orElseThrow(() -> {
            log.error("Question with: " + questionId + "is not found");
            return new NotFoundException("Question is not found");
        });

        if(question.getTest() == null){
            log.error("Question with id: " + questionId + " has no associated tests");
            throw new NotFoundException("Question has no associated tests");
        }

        Test test = question.getTest();
        int setDuration = test.getDuration() - question.getDuration();
        test.setDuration(setDuration);
        test.getQuestions().remove(question);

        questionRepo.delete(question);
        testRepo.save(test);
        log.info("Question with ID {} successfully deleted", questionId);
        return testDao.getAllQuestionByTestId(test.getId());
    }

    @Override
    public SimpleResponse updateQuestion(Long questionId, UpdateRequest questionRequest) {
        Question question = questionRepo.findById(questionId).orElseThrow(() -> {
            String message = "Question with ID " + questionId + " is not found.";
            log.error(message);
            return new NotFoundException(message);
        });
        if (questionRequest.title() != null && question.getTitle() != null &&
                !question.getTitle().equals(questionRequest.title())) {
            question.setTitle(questionRequest.title());
        }

        if (questionRequest.duration() > 0 && question.getDuration() != questionRequest.duration()) {
            question.setDuration(questionRequest.duration());
        }

        if (questionRequest.correctAnswer() != null && question.getCorrectAnswer() != null &&
                !question.getCorrectAnswer().equals(questionRequest.correctAnswer())) {
            question.setCorrectAnswer(questionRequest.correctAnswer());
        }

        if (questionRequest.fileUrl() != null && question.getFileUrl() != null &&
                !question.getFileUrl().equals(questionRequest.fileUrl())) {
            question.setFileUrl(questionRequest.fileUrl());
        }

        if (questionRequest.attempts() > 0 && question.getAttempts() != questionRequest.attempts()) {
            question.setAttempts(questionRequest.attempts());
        }

        if (questionRequest.passage() != null && question.getPassage() != null &&
                !question.getPassage().equals(questionRequest.passage())) {
            question.setPassage(questionRequest.passage());
        }

        if (questionRequest.statement() != null && question.getStatement() != null &&
                !question.getStatement().equals(questionRequest.statement())) {
            question.setStatement(questionRequest.statement());
        }

        List<Option> options = question.getOption();
        for (OptionUpdate optionRequest : questionRequest.optionRequest()) {
            boolean optionExists = false;
            for (Option option : options) {
                if (option.getId().equals(optionRequest.getId())) {
                    optionExists = true;
                    if (!optionRequest.getOptionTitle().isEmpty()) {
                        option.setTitle(optionRequest.getOptionTitle());
                    }
                    if (!optionRequest.getFileUrl().isEmpty()) {
                        option.setFileUrl(optionRequest.getFileUrl());
                    }
                    if (optionRequest.getIsCorrectOption() != null) {
                        option.setIsTrueOption(optionRequest.getIsCorrectOption());
                    }
                    optionRepo.save(option);
                    break;
                }
            }

            if (!optionExists) {
                Option newOption = new Option();
                newOption.setTitle(optionRequest.getOptionTitle());
                newOption.setFileUrl(optionRequest.getFileUrl());
                newOption.setIsTrueOption(optionRequest.getIsCorrectOption());
                newOption.setQuestion(question);
                optionRepo.save(newOption);
                question.getOption().add(newOption);
            }
        }

        questionRepo.save(question);
        log.info("Question with ID {} successfully updated: ", questionId);
        return new SimpleResponse(HttpStatus.OK, "Question successfully updated");
    }
}