package bilingual.service.impl;

import bilingual.dto.response.answer.AnswerResponses;
import bilingual.dto.response.result.MyResultResponse;
import bilingual.dto.response.result.ResultResponse;
import bilingual.dto.response.result.ResultResponses;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.emailSender.JavaMailService;
import bilingual.entity.User;
import bilingual.entity.Answer;
import bilingual.entity.Question;
import bilingual.entity.Test;
import bilingual.entity.UserInfo;
import bilingual.entity.Result;
import bilingual.entity.enums.Status;
import bilingual.repo.AnswerRepo;
import bilingual.repo.ResultRepo;
import bilingual.repo.TestRepo;
import bilingual.repo.UserInfoRepo;
import bilingual.repo.jdbcRepo.ResultDao;
import bilingual.service.ResultService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.webjars.NotFoundException;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultDao resultDao;
    private final UserInfoRepo userInfoRepo;
    private final ResultRepo resultRepo;
    private final AnswerRepo answerRepo;
    private final TestRepo testRepo;
    private final JavaMailService javaMailService;
    private final TemplateEngine templateEngine;

    @Override
    public List<MyResultResponse> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserInfo user = userInfoRepo.findByEmail(email).orElseThrow(() -> {
            log.error(String.format("User with email %s is not found !!!", email));
            return new NotFoundException("User is not found !!!");
        });
        return resultDao.getAll(user.getUser().getId());
    }

    @Override
    public SimpleResponse deleteResult(Long id) {
        Result result = resultRepo.findById(id).orElseThrow(() ->
                new NotFoundException("Result not found"));
        Test test = result.getTest();
        test.getResult().remove(result);
        test.setIsPassed(false);
        testRepo.save(test);
        resultRepo.delete(result);
        log.info("Result with ID {} successfully deleted", id);
        return new SimpleResponse(HttpStatus.OK, "Result successfully deleted!");
    }

    @Override
    public SimpleResponse checkAnswer(Long answerId, double score) {
        Answer answer = answerRepo.findById(answerId).orElseThrow(() -> {
            String message = "Answer with id: " + answerId + " is not found";
            log.error(message);
            return new NotFoundException(message);
        });

        User user = answer.getUser();
        Question question = answer.getQuestion();

        if (question == null) {
            log.error("Answer with id: " + answerId + " does not have associated Question.");
            throw new IllegalStateException("Answer does not have associated Question.");
        }
        Test test = question.getTest();

        if (answer.getStatus() == null || Status.NOT_EVALUATED.equals(answer.getStatus())) {
            Result result = resultRepo.findResultByUserIdAndTestId(user.getId(), test.getId()).orElseThrow(() -> {
                log.error("Result with user ID: " + user.getId() + " and with test ID: " + test.getId() + " is not found");
                return new NotFoundException("Error. Given result is not found");
            });

            answer.setScore(score);
            answer.setStatus(Status.EVALUATED);
            answer.setIsChecked(true);
            answer.setResults(result);
            answerRepo.save(answer);
        } else {
            log.error("Unexpected state: Answer with id " + answerId + " is already EVALUATED.");
            throw new IllegalStateException("Unexpected state: Answer is already EVALUATED.");
        }

        log.info("Result successfully updated for user {} and test {}", user.getUserInfo().getEmail(), test.getId());
        return new SimpleResponse(HttpStatus.OK, "Your answer successfully scored");
    }

    @Override
    public SimpleResponse sendResult(Long resultId, String link) {
        Result result = resultRepo.findById(resultId)
                .orElseThrow(() -> new NotFoundException(String.format("Result with ID %d not found", resultId)));

        UserInfo userInfo = userInfoRepo.findByUserId(result.getUser().getId())
                .orElseThrow(() -> new NotFoundException(String.format("User info with ID %d not found", result.getUser().getId())));

        Test test = testRepo.findById(result.getTest().getId())
                .orElseThrow(() -> new NotFoundException(String.format("Test with ID %d not found", result.getTest().getId())));

        String subject = "Test result";

        Context context = new Context();
        context.setVariable("greeting", createGreeting());
        context.setVariable("name", String.format(userInfo.getUser().getFirstName()));
        context.setVariable("title", String.format("Your test %s successfully submitted and verified", test.getTitle()));
        context.setVariable("score", result.getScore());
        context.setVariable("maxScore", 100);
        context.setVariable("link", link);

        String htmlContent = templateEngine.process("result.html", context);

        javaMailService.sendVerificationCodeHtml(userInfo.getEmail(), subject, htmlContent);
        log.info("score sender");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("The score was sent to your email. Please check your email.")
                .build();

    }

    private String createGreeting() {
        ZoneId zoneId = ZoneId.of("Asia/Bishkek");
        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        String greeting;

        LocalTime noonTime = LocalTime.NOON;
        LocalTime eveningTime = LocalTime.of(18, 0);

        if (currentTime.toLocalTime().isBefore(noonTime)) {
            greeting = "Good morning! ";
        } else if (currentTime.toLocalTime().isBefore(eveningTime)) {
            greeting = "Good afternoon! ";
        } else {
            greeting = "Good evening! ";
        }
        log.debug("Generated greeting: {}", greeting);
        return greeting;
    }

    @Override
    public ResultResponses getAllAnswerByResultId(Long resultId) {
        List<Answer> answers = answerRepo.findAllByResultsId(resultId);
        if (answers.isEmpty()) {
            log.warn("No answers found for the given resultId: " + resultId);
            throw new EntityNotFoundException("No answers found for the given resultId");
        }

        boolean allAnswersEvaluated = true;
        double totalScore = 0.0;

        for (Answer answer : answers) {
            Result result = answer.getResults();
            ResultResponses resultResponses = resultDao.getAllAnswerByResultId(resultId);
            List<AnswerResponses> answerResponses = resultResponses.getAnswerResponses();

            if (answerResponses.stream().anyMatch(response -> response.getStatus() != Status.EVALUATED)) {
                allAnswersEvaluated = false;
                break;
            }

            Test test = answer.getQuestion().getTest();
            int size = test.getQuestions().size();
            int maxScorePerQuestion = 10;
            int totalMaxScore = size * maxScorePerQuestion;

            totalScore = answerResponses.stream()
                    .mapToDouble(AnswerResponses::getScore)
                    .sum();

            double percentageScore = (totalScore / totalMaxScore) * 100;
            long roundedPercentageScore = Math.round(percentageScore);

            result.setScore(roundedPercentageScore);
            result.setStatus(Status.EVALUATED);
            result.setIsSeen(true);
            resultRepo.save(result);
        }
        return resultDao.getAllAnswerByResultId(resultId);
    }

    @Override
    public List<ResultResponse> getAllTestResults() {
        return resultDao.getAllTestResults();
    }
}