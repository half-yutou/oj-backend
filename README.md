# oj-backend

## 阶段一

### 题目增删改查

1. 添加题目
- DTO -> QuestionAddRequest
> 为方便前端传递,
> 1. JudgeConfig字段 与 JudgeCase字段 使用对象接收
> 2. Tags字段 使用List<String> 存储
> 而实体类 Question 使用String存储

- VO -> QuestionVO
> 同上
> 存在objToVO() 与 voToObj()
