package models;

public class VoteModel {
    private String questionId;
    private String variant;

    public VoteModel(String questionId, String variant) {
        this.questionId = questionId;
        this.variant = variant;
    }

    public VoteModel() {}

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }
}
