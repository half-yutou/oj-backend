package com.yutou.oj.controller;

import com.yutou.oj.common.BaseResponse;
import com.yutou.oj.common.ErrorCode;
import com.yutou.oj.common.ResultUtils;
import com.yutou.oj.exception.BusinessException;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yutou.oj.model.entity.User;
import com.yutou.oj.service.QuestionSubmitService;
import com.yutou.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 获取所有提交详情(仅可查看自己的提交记录)
     */
}
