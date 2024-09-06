package com.yutou.oj.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {
    /**
     * 时间限制
     */
    private long timeLimit;

    /**
     * 内存限制
     */
    private long memoryLimit;

    /**
     * 栈限制
     */
    private long stackLimit;
}
