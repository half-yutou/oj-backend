package com.yutou.oj.model.vo;

import com.yutou.oj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交VO
 */
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息（json 对象）
     */
    private String judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static QuestionSubmitVO objToVo(QuestionSubmit obj) {
        if (obj == null) return null;

        QuestionSubmitVO vo = new QuestionSubmitVO();
        BeanUtils.copyProperties(obj, vo);

        return vo;
    }

    public static QuestionSubmit voToObj(QuestionVO vo) {
        if (vo == null) return null;

        QuestionSubmit obj = new QuestionSubmit();
        BeanUtils.copyProperties(vo, obj);

        obj.setIsDelete(0);
        return obj;
    }
}