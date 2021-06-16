package models;

import javax.persistence.*;

@Entity(name = "QuestionEntity")
@Table(name = "questions")
public class Question {
    private int id;
    private String question_text;
    private String variant1;
    private String variant2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "question_text")
    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    @Column(name = "variant1")
    public String getVariant1() {
        return variant1;
    }

    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }

    @Column(name = "variant2")
    public String getVariant2() {
        return variant2;
    }

    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }
}
