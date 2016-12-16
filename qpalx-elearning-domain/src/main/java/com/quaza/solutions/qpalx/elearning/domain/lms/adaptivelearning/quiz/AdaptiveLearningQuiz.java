package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="AdaptiveLearningQuiz2")
public class AdaptiveLearningQuiz {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="TimeToCompleteActivity", nullable=true)
    private Long timeToCompleteActivity;

    @Column(name="QuizTitle", nullable=false, length=100)
    private String quizTitle;

    @Column(name="QuizDescription", nullable=false, length=455)
    private String quizDesription;

    @Column(name="MaxPossibleActivityScore", nullable=false)
    private Double maxPossibleActivityScore;

    @Column(name="MinimumPassingActivityScore", nullable=false)
    private Double minimumPassingActivityScore;

    @Column(name="EntryDate", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    @Column(name="ModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modifyDate;

    // IF set to true then this Quiz is currently active
    @Column(name="Active", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXEMicroLessonID", nullable = false)
    private QPalXEMicroLesson qPalXEMicroLesson;

    // Collection of all questions for this quiz.  LinkedHashSet used to maintain ordering
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "adaptiveLearningQuiz")
    private Set<AdaptiveLearningQuizQuestion> adaptiveLearningQuizQuestions = new LinkedHashSet<>();
}
