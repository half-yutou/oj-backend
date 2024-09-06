package com.yutou.oj.model.dto.question;

import lombok.Data;

@Data
public class JudgeCase {
    /**
     * 一组输入用例
     */
    private String input;

    /**
     * 一组输出用例
     */
    private String output;
}
