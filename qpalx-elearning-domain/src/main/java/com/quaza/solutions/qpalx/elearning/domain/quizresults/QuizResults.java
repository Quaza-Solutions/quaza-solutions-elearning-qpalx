package com.quaza.solutions.qpalx.elearning.domain.quizresults;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Created by Trading_1 on 8/25/2016.
 */
@Entity
@Table(name="QuizResults")
public class QuizResults {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="Score", nullable=false, length=12, unique = false)
    private double score;

    @Column(name="QuizDuration", nullable = false, length = 30, unique = false)
    private String quizDuration;

    @Column(name="LessonName", nullable = false, length = 30, unique = false)
    private String lessonName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXUserID", nullable = true)//set to false when we have the field
    private QPalXUser qPalXUser;

    //getter setters
    public void setScore(double score){this.score = score;}
    public double getScore(){return score;}
    public void setQuizDuration(String quizDuration){this.quizDuration = quizDuration;}
    public String getQuizDuration(){return quizDuration;}
    public void setLessonName(String lessonName){this.lessonName = lessonName;}
    public String getLessonName(){return lessonName;}
    public void setqPalXUser(QPalXUser qPalXUser){this.qPalXUser = qPalXUser;}
    public QPalXUser getqPalXUser(){return qPalXUser;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QuizResults that = (QuizResults) o;

        return new EqualsBuilder()
                .append(score, that.score)
                .append(quizDuration, that.quizDuration)
                .append(lessonName, that.lessonName)
                .append(qPalXUser, that.qPalXUser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(score)
                .append(quizDuration)
                .append(lessonName)
                .append(qPalXUser)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("score", score)
                .append("quizDuration", quizDuration)
                .append("lessonName", lessonName)
                .append("qpalxUser", qPalXUser)
                .toString();
    }

    public static final class Builder {

        private final QuizResults quizResults = new QuizResults();

        public Builder() {
        }

        public QuizResults.Builder score(double score) {
            quizResults.score = score;
            return this;
        }

        public QuizResults.Builder quizDuration(String quizDuration) {
            quizResults.quizDuration = quizDuration;
            return this;
        }

        public QuizResults.Builder lessonName(String lessonName) {
            quizResults.lessonName = lessonName;
            return this;
        }

        public QuizResults.Builder qpalxUser(QPalXUser qPalXUser) {
            quizResults.qPalXUser = qPalXUser;
            return this;
        }

        public QuizResults build(){ return quizResults; }
    }

    public static QuizResults.Builder builder(){ return new QuizResults.Builder(); }
}
