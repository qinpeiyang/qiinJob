package com.xxl.job.service.entity;

import lombok.Data;

@Data
public class MailBean {
    //邮件接收账户
    private String mailTo;
    //邮件标题
    private String title;
    //邮件正文
    private String content;
}
