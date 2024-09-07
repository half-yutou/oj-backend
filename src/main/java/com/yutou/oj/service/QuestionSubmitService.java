package com.yutou.oj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yutou.oj.model.entity.QuestionSubmit;
import com.yutou.oj.model.entity.User;

/**
* @author xyt
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2024-09-06 18:43:28
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 提交题目信息
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);
}
