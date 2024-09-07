package com.yutou.oj.model.dto.questionsubmit;

import com.yutou.oj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 *
 * @author xyt
 * @from   csu
 */
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 提交本条记录的用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
