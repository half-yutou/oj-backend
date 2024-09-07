package com.yutou.oj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 判题结果类型枚举
 *
 * @author xyt
 * @from csu
 */
public enum JudgeInfoEnum {
    ACCEPTED("成功", "AC"),
    WRONG_ANSWER("答案错误", "WA"),
    COMPILE_ERROR("编译错误", "Compile Error"),
    MEMORY_LIMIT_EXCEEDED("内存溢出", "Memory out"),
    TIME_LIMIT_EXCEEDED("超时", "Timeout"),
    OUTPUT_LIMIT_EXCEEDED("输出溢出", "Output Out"),
    WAITING("等待中", "Waiting"),
    DANGEROUS_OPERATION("危险操作", "Dangerous"),
    RUNTIME_ERROR("运行错误", "Runtime Error"),
    SYSTEM_ERROR("系统错误", "System Error");

    private final String text;

    private final String value;

    JudgeInfoEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoEnum anEnum : JudgeInfoEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
