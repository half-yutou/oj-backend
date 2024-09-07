package com.yutou.oj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yutou.oj.common.BaseResponse;
import com.yutou.oj.common.ErrorCode;
import com.yutou.oj.common.ResultUtils;
import com.yutou.oj.exception.BusinessException;
import com.yutou.oj.exception.ThrowUtils;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yutou.oj.model.entity.QuestionSubmit;
import com.yutou.oj.model.entity.User;
import com.yutou.oj.model.vo.QuestionSubmitVO;
import com.yutou.oj.service.QuestionSubmitService;
import com.yutou.oj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        if (!Objects.equals(user.getId(), questionSubmitQueryRequest.getUserId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "仅可查看自己的提交记录");
        }
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<QuestionSubmit> questionPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        return ResultUtils.success(questionSubmitService.getQuestionVOPage(questionPage, request));
    }
}
