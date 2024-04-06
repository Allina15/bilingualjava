package bilingual.service.impl;

import bilingual.dto.request.option.OptionRequest;
import bilingual.dto.response.option.OptionsResponse;
import bilingual.dto.response.test.SimpleResponse;
import bilingual.entity.Answer;
import bilingual.entity.Option;
import bilingual.entity.Question;
import bilingual.entity.enums.QuestionType;
import bilingual.repo.AnswerRepo;
import bilingual.repo.OptionRepo;
import bilingual.repo.QuestionRepo;
import bilingual.repo.jdbcRepo.OptionDao;
import bilingual.service.OptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionDao optionDao;
    private final OptionRepo optionRepo;
    private final QuestionRepo questionRepo;
    private final AnswerRepo answerRepo;


    @Override
    public OptionsResponse getOptionByQuestionId(Long questionId) {
        return optionDao.getOptionByQuestionId(questionId);
    }

    @Override
    public SimpleResponse deleteOption(Long optionId) {
        Option option = optionRepo.findById(optionId).orElseThrow(() -> {
            String message = "Question with: " + optionId + " is not found";
            log.error(message);
            return new NotFoundException(message);
        });

        Question question = option.getQuestion();
        question.getOption().remove(option);
        questionRepo.save(question);

        List<Answer> answers = answerRepo.findByOptionsContaining(option);

        for (Answer answer : answers) {
            answer.getOptions().remove(option);
            answerRepo.save(answer);
        }

        optionRepo.delete(option);
        log.info("Option with ID {} successfully deleted", optionId);
        return new SimpleResponse(HttpStatus.OK, "Option successfully deleted");
    }

    @Override
    public SimpleResponse addOption (Long questionId, List < OptionRequest > optionRequest){
        Question question = questionRepo.findById(questionId).orElseThrow(() -> {
            String message = "Question with: " + questionId + " is not found";
            log.error(message);
            return new NotFoundException("Oops. Question is not found");
        });

        List<Option> optionList = new ArrayList<>(optionRequest.size());

        for (OptionRequest options : optionRequest) {
            Option option = new Option();
            option.setTitle(options.getOptionTitle());
            option.setIsTrueOption(options.getIsCorrectOption());
            if (question.getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD) {
                option.setFileUrl(options.getFileUrl());
            } else {
                option.setFileUrl(null);
            }
            option.setQuestion(question);
            optionList.add(option);
        }
        question.getOption().addAll(optionList);
        optionRepo.saveAll(optionList);
        questionRepo.save(question);
        return new SimpleResponse(HttpStatus.OK, "Option successfully saved");
    }
}


