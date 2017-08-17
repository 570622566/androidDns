package net.shenru.dns;

/**
 * Created by xtdhwl on 3/15/17.
 */

public class DnsResponse {
    private String transactionId;
    private String falgs;
    private String questions;
    private String answerRRs;
    private String authorityRRs;
    private String additionalRRs;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFalgs() {
        return falgs;
    }

    public void setFalgs(String falgs) {
        this.falgs = falgs;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswerRRs() {
        return answerRRs;
    }

    public void setAnswerRRs(String answerRRs) {
        this.answerRRs = answerRRs;
    }

    public String getAuthorityRRs() {
        return authorityRRs;
    }

    public void setAuthorityRRs(String authorityRRs) {
        this.authorityRRs = authorityRRs;
    }

    public String getAdditionalRRs() {
        return additionalRRs;
    }

    public void setAdditionalRRs(String additionalRRs) {
        this.additionalRRs = additionalRRs;
    }
}
