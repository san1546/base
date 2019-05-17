package com.example.eurakeclient.model;

public class Mail {
    private Integer id;

    private String fromemail;

    private String subject;

    private String replyto;

    private String attachment;

    private String receiptTime;

    private Integer mailFlag;

    private Integer mailStatus;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromemail() {
        return fromemail;
    }

    public void setFromemail(String fromemail) {
        this.fromemail = fromemail == null ? null : fromemail.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getReplyto() {
        return replyto;
    }

    public void setReplyto(String replyto) {
        this.replyto = replyto == null ? null : replyto.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Integer getMailFlag() {
        return mailFlag;
    }

    public void setMailFlag(Integer mailFlag) {
        this.mailFlag = mailFlag;
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}