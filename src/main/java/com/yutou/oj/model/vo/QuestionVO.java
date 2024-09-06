package com.yutou.oj.model.vo;

import cn.hutool.json.JSONUtil;
import com.yutou.oj.model.dto.question.JudgeConfig;
import com.yutou.oj.model.entity.Question;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionVO implements Serializable {
    /**
     * 题目 id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签(json 数组字符串)
     */
    private List<String> tags;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptNum;

    /**
     * 判题配置(json 数组字符串)
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 用户(创建者) id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;

    public static QuestionVO objToVo(Question question) {
        if (question == null) return null;

        QuestionVO vo = new QuestionVO();
        BeanUtils.copyProperties(question, vo);

        String tagStr = question.getTags();
        if (StringUtils.isNotBlank(tagStr)) {
            vo.setTags(JSONUtil.toList(tagStr, String.class));
        }

        String configStr = question.getJudgeConfig();
        if (StringUtils.isNotBlank(configStr)) {
            vo.setJudgeConfig(JSONUtil.toBean(configStr, JudgeConfig.class));
        }

        return vo;
    }

    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) return null;

        Question obj = new Question();
        BeanUtils.copyProperties(questionVO, obj);

        String tagStr = JSONUtil.toJsonStr(questionVO.getTags());
        obj.setTags(tagStr);

        String configStr = JSONUtil.toJsonStr(questionVO.getJudgeConfig());
        obj.setJudgeConfig(configStr);

        return obj;
    }
}
