package models;

public class QuestionModel {
    private String questionText;
    private String variant1;
    private String variant2;

    public QuestionModel() {}

    public QuestionModel(String questionText, String variant1, String variant2) {
        this.questionText = questionText;
        this.variant1 = variant1;
        this.variant2 = variant2;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getVariant1() {
        return variant1;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    public String getVariant2() {
        return variant2;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }
}
