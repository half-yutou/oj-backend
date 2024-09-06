package com.yutou.oj.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yutou.oj.model.dto.question.QuestionQueryRequest;
import com.yutou.oj.model.entity.Question;
import com.yutou.oj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author xyt
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-09-06 18:43:28
*/
public interface QuestionService extends IService<Question> {

    void validQuestion(Question question, boolean add);

    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    Wrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);
}
