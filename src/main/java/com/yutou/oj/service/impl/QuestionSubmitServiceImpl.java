package com.yutou.oj.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yutou.oj.common.ErrorCode;
import com.yutou.oj.constant.CommonConstant;
import com.yutou.oj.exception.BusinessException;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yutou.oj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yutou.oj.model.entity.Question;
import com.yutou.oj.model.entity.QuestionSubmit;
import com.yutou.oj.model.entity.User;
import com.yutou.oj.model.enums.LanguageEnum;
import com.yutou.oj.model.enums.QuestionSubmitStatusEnum;
import com.yutou.oj.model.vo.QuestionSubmitVO;
import com.yutou.oj.model.vo.QuestionVO;
import com.yutou.oj.service.QuestionService;
import com.yutou.oj.service.QuestionSubmitService;
import com.yutou.oj.mapper.QuestionSubmitMapper;
import com.yutou.oj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String searchText = questionSubmitQueryRequest.getSearchText();
        String title = questionSubmitQueryRequest.getTitle();
        String content = questionSubmitQueryRequest.getContent();
        List<String> tags = questionSubmitQueryRequest.getTags();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();


        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.and(
                    qw -> qw.like("title", searchText)
                            .or()
                            .like("content", searchText)
            );
        }
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        if (CollUtil.isNotEmpty(tags)) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }

        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionVOPage(Page<QuestionSubmit> questionSubmitPage, HttpServletRequest request) {
        List<QuestionSubmit> questionList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionList)) {
            return questionSubmitVOPage;
        }
        // 填充信息
        List<QuestionSubmitVO> questionSubmitVOList = questionList.stream().map(QuestionSubmitVO::objToVo).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }
}




