package com.yutou.oj.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
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
     * 参考答案
     */
    private String answer;

    /**
     * 判题配置(json 数组字符串)
     */
    private JudgeConfig judgeConfig;

    /**
     * 判题用例(json 数组字符串)
     */
    private List<JudgeCase> judgeCase;

    /**
     * 用户(创建者) id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
