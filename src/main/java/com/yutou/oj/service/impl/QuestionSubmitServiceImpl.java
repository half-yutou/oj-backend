package com.yutou.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yutou.oj.common.ErrorCode;
import com.yutou.oj.exception.BusinessException;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yutou.oj.model.entity.Question;
import com.yutou.oj.model.entity.QuestionSubmit;
import com.yutou.oj.model.entity.User;
import com.yutou.oj.model.enums.LanguageEnum;
import com.yutou.oj.model.enums.QuestionSubmitStatusEnum;
import com.yutou.oj.service.QuestionService;
import com.yutou.oj.service.QuestionSubmitService;
import com.yutou.oj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author xyt
* @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2024-09-06 18:43:28
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionService questionService;

    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null || question.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        LanguageEnum languageEnum = LanguageEnum.getEnumByValue(questionSubmitAddRequest.getLanguage());
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }

        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);

        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目提交数据插入失败");
        }
        return questionSubmit.getId();

    }
}




