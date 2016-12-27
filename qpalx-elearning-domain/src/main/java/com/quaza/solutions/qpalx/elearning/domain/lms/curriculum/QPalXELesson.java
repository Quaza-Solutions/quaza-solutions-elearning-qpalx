package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.HierarchicalLMSContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="QPalXELesson")
public class QPalXELesson implements IHierarchicalLMSContent {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="LessonName", nullable=false, length=255)
    private String lessonName;

    @Column(name="LessonDescription", nullable=false, length=255)
    private String lessonDescription;

    // Provides information on Media Content that is associated with this activity.
    @Embedded
    private ELearningMediaContent eLearningMediaContent;

    // Floor ProficiencyRankingScale should always be specified for each ELearningCourse.
    // Students can only take ElearningCourse's inclusive between the specified ceiling and floor ProficiencyRanking
    @Column(name="ProficiencyRankingScaleFloor", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScaleE proficiencyRankingScaleFloor;

    // Ceiling ProficiencyRankingScale should always be specified for each ELearningCourse.
    // Students can only take ElearningCourse's inclusive between the specified ceiling and floor ProficiencyRanking
    @Column(name="ProficiencyRankingScaleCeiling", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScaleE proficiencyRankingScaleCeiling;

    // Always fetch this Eager as we always need the course readily available to use.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCourseID", nullable = false)
    private ELearningCourse eLearningCourse;

    // Optional QPalXEducationalInstitution.  A Lesson could be developed for a specific educational institution
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalXEducationalInstitutionID", nullable = true)
    private QPalXEducationalInstitution qPalXEducationalInstitution;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TutorialLevelCalendarID", nullable = false)
    private TutorialLevelCalendar tutorialLevelCalendar;

    // DateTime that the ELearning Media Content was uploaded on the QPalX platform.
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    // IF set to true then this QPalXELesson is currently active
    @Column(name="LessonActive", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean lessonActive;

    // Collection of all the QPalXEMicroLesson available as part of this lesson
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qPalXELesson")
    private Set<QPalXEMicroLesson> qPalXEMicroLessons = new HashSet<>();

    // Collection of all the QuestionBankItem available as part of this lesson
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qPalXELesson")
    private Set<QuestionBankItem> questionBankItems = new HashSet<>();


    public QPalXELesson() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    public ELearningMediaContent geteLearningMediaContent() {
        return eLearningMediaContent;
    }

    public void seteLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    public ProficiencyRankingScaleE getProficiencyRankingScaleFloor() {
        return proficiencyRankingScaleFloor;
    }

    public void setProficiencyRankingScaleFloor(ProficiencyRankingScaleE proficiencyRankingScaleFloor) {
        this.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
    }

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeiling() {
        return proficiencyRankingScaleCeiling;
    }

    public void setProficiencyRankingScaleCeiling(ProficiencyRankingScaleE proficiencyRankingScaleCeiling) {
        this.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
    }

    public ELearningCourse geteLearningCourse() {
        return eLearningCourse;
    }

    public void seteLearningCourse(ELearningCourse eLearningCourse) {
        this.eLearningCourse = eLearningCourse;
    }

    public QPalXEducationalInstitution getQPalXEducationalInstitution() {
        return qPalXEducationalInstitution;
    }

    public void setQPalXEducationalInstitution(QPalXEducationalInstitution qPalXEducationalInstitution) {
        this.qPalXEducationalInstitution = qPalXEducationalInstitution;
    }

    public TutorialLevelCalendar getTutorialLevelCalendar() {
        return tutorialLevelCalendar;
    }

    public void setTutorialLevelCalendar(TutorialLevelCalendar tutorialLevelCalendar) {
        this.tutorialLevelCalendar = tutorialLevelCalendar;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isLessonActive() {
        return lessonActive;
    }

    public void setLessonActive(boolean lessonActive) {
        this.lessonActive = lessonActive;
    }

    public Set<QPalXEMicroLesson> getQPalXEMicroLessons() {
        return ImmutableSet.copyOf(qPalXEMicroLessons);
    }

    public void addQPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        qPalXEMicroLessons.add(qPalXEMicroLesson);
    }

    public Set<QuestionBankItem> getQuestionBankItems() {
        return ImmutableSet.copyOf(questionBankItems);
    }

    public void addQuestionBankItem(QuestionBankItem questionBankItem) {
        Assert.notNull(questionBankItem, "questionBankItem cannot be null");
        questionBankItems.add(questionBankItem);
    }

    @Override
    public String getHierarchicalLMSContentName() {
        return getLessonName();
    }

    @Override
    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE() {
        return HierarchicalLMSContentTypeE.Lesson;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContentParent() {
        return geteLearningCourse();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXELesson that = (QPalXELesson) o;

        return new EqualsBuilder()
                .append(lessonActive, that.lessonActive)
                .append(id, that.id)
                .append(lessonName, that.lessonName)
                .append(lessonDescription, that.lessonDescription)
                .append(eLearningMediaContent, that.eLearningMediaContent)
                .append(proficiencyRankingScaleFloor, that.proficiencyRankingScaleFloor)
                .append(proficiencyRankingScaleCeiling, that.proficiencyRankingScaleCeiling)
                .append(eLearningCourse, that.eLearningCourse)
                .append(qPalXEducationalInstitution, that.qPalXEducationalInstitution)
                .append(tutorialLevelCalendar, that.tutorialLevelCalendar)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(lessonName)
                .append(lessonDescription)
                .append(eLearningMediaContent)
                .append(proficiencyRankingScaleFloor)
                .append(proficiencyRankingScaleCeiling)
                .append(eLearningCourse)
                .append(qPalXEducationalInstitution)
                .append(tutorialLevelCalendar)
                .append(entryDate)
                .append(lessonActive)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("lessonName", lessonName)
                .append("lessonDescription", lessonDescription)
                .append("eLearningMediaContent", eLearningMediaContent)
                .append("proficiencyRankingScaleFloor", proficiencyRankingScaleFloor)
                .append("proficiencyRankingScaleCeiling", proficiencyRankingScaleCeiling)
                .append("eLearningCourse", eLearningCourse)
                .append("qPalXEducationalInstitution", qPalXEducationalInstitution)
                .append("tutorialLevelCalendar", tutorialLevelCalendar)
                .append("entryDate", entryDate)
                .append("lessonActive", lessonActive)
                .toString();
    }


    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder{

        private final QPalXELesson qPalXELesson = new QPalXELesson();

        public Builder lessonName(String lessonName) {
            qPalXELesson.lessonName = lessonName;
            return this;
        }

        public Builder lessonDescription(String lessonDescription) {
            qPalXELesson.lessonDescription = lessonDescription;
            return this;
        }

        public Builder eLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
            qPalXELesson.eLearningMediaContent = eLearningMediaContent;
            return this;
        }

        public Builder proficiencyRankingScaleFloor(ProficiencyRankingScaleE proficiencyRankingScaleFloor) {
            qPalXELesson.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
            return this;
        }

        public Builder proficiencyRankingScaleCeiling(ProficiencyRankingScaleE proficiencyRankingScaleCeiling) {
            qPalXELesson.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
            return this;
        }

        public Builder eLearningCourse(ELearningCourse eLearningCourse) {
            qPalXELesson.eLearningCourse = eLearningCourse;
            return this;
        }

        public Builder qPalXEducationalInstitution(QPalXEducationalInstitution qPalXEducationalInstitution) {
            qPalXELesson.qPalXEducationalInstitution = qPalXEducationalInstitution;
            return this;
        }

        public Builder tutorialLevelCalendar(TutorialLevelCalendar tutorialLevelCalendar) {
            qPalXELesson.tutorialLevelCalendar = tutorialLevelCalendar;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            qPalXELesson.entryDate = entryDate;
            return this;
        }

        public Builder lessonActive(boolean lessonActive) {
            qPalXELesson.lessonActive = lessonActive;
            return this;
        }

        public Builder qPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
            qPalXELesson.addQPalXEMicroLesson(qPalXEMicroLesson);
            return this;
        }

        public QPalXELesson build() {
            return qPalXELesson;
        }
    }
}
