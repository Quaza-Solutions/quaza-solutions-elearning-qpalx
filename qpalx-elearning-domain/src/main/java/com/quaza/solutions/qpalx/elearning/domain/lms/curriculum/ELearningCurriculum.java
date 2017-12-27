package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.HierarchicalLMSContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.util.BaseEntityHasOrderInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Optional;

/**
 *
 * @author manyce400
 */
@Entity
@Table(name="ELearningCurriculum")
public class ELearningCurriculum extends BaseEntityHasOrderInfo implements IHierarchicalLMSContent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="CurriculumType", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private CurriculumType curriculumType;

    @Column(name="CurriculumName", nullable=false, length=20)
    private String curriculumName;

    @Column(name="CurriculumDescription", nullable=false, length=255, unique=true)
    private String curriculumDescription;

    // The StudentTutorialGrade that this ELearning Curriculum has been designed for.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentTutorialGradeID", nullable = false)
    private StudentTutorialGrade studentTutorialGrade;

    // Full path and file name of the media content
    @Column(name="CurriculumIcon", nullable=false, length=255)
    private String curriculumIcon;

    @Column(name="CurriculumBannerIcon", nullable=true, length=255)
    private String curriculumBannerIcon;

    // Each Curriculum could have an optional Assessment Quiz which would have to be completed to gauge
    // Student's proficiency before student can attempt courses lessons and teaching content in that Curriculum
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AssessmentAdaptiveLearningQuizID", nullable = true)
    private AdaptiveLearningQuiz assessmentAdaptiveLearningQuiz;

    // IF set to true then this QPalXELesson is currently active
    @Column(name="Active", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;


    public static final String CLASS_ATTRIBUTE_IDENTIFIER = "ELearningCurriculum";

    public static final String CLASS_INSTANCES_IDENTIFIER = "ELearningCurricula";


    public ELearningCurriculum() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurriculumType getCurriculumType() {
        return curriculumType;
    }

    public void setCurriculumType(CurriculumType curriculumType) {
        this.curriculumType = curriculumType;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public void setCurriculumName(String curriculumName) {
        this.curriculumName = curriculumName;
    }

    public String getCurriculumDescription() {
        return curriculumDescription;
    }

    public void setCurriculumDescription(String curriculumDescription) {
        this.curriculumDescription = curriculumDescription;
    }

    public String getCurriculumIcon() {
        return curriculumIcon;
    }

    public void setCurriculumIcon(String curriculumIcon) {
        this.curriculumIcon = curriculumIcon;
    }

    public String getCurriculumBannerIcon() {
        return curriculumBannerIcon;
    }

    public void setCurriculumBannerIcon(String curriculumBannerIcon) {
        this.curriculumBannerIcon = curriculumBannerIcon;
    }

    public StudentTutorialGrade getStudentTutorialGrade() {
        return studentTutorialGrade;
    }

    public void setStudentTutorialGrade(StudentTutorialGrade studentTutorialGrade) {
        this.studentTutorialGrade = studentTutorialGrade;
    }

    public AdaptiveLearningQuiz getAssessmentAdaptiveLearningQuiz() {
        return assessmentAdaptiveLearningQuiz;
    }

    public void setAssessmentAdaptiveLearningQuiz(AdaptiveLearningQuiz assessmentAdaptiveLearningQuiz) {
        this.assessmentAdaptiveLearningQuiz = assessmentAdaptiveLearningQuiz;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getHierarchicalLMSContentName() {
        return getCurriculumName();
    }

    @Override
    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE() {
        return HierarchicalLMSContentTypeE.ELearningCurriculum;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContentParent() {
        return null;
    }

    @Override
    public Optional<Long> getOrderingDiscriminator() {
        return Optional.of(studentTutorialGrade.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ELearningCurriculum that = (ELearningCurriculum) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(curriculumType, that.curriculumType)
                .append(curriculumName, that.curriculumName)
                .append(curriculumDescription, that.curriculumDescription)
                .append(studentTutorialGrade, that.studentTutorialGrade)
                .append(curriculumIcon, that.curriculumIcon)
                .append(curriculumBannerIcon, that.curriculumBannerIcon)
                .append(assessmentAdaptiveLearningQuiz, that.assessmentAdaptiveLearningQuiz)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(curriculumType)
                .append(curriculumName)
                .append(curriculumDescription)
                .append(studentTutorialGrade)
                .append(curriculumIcon)
                .append(curriculumBannerIcon)
                .append(assessmentAdaptiveLearningQuiz)
                .append(active)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("curriculumType", curriculumType)
                .append("curriculumName", curriculumName)
                .append("curriculumDescription", curriculumDescription)
                .append("studentTutorialGrade", studentTutorialGrade)
                .append("curriculumIcon", curriculumIcon)
                .append("curriculumBannerIcon", curriculumBannerIcon)
                .append("assessmentAdaptiveLearningQuiz", assessmentAdaptiveLearningQuiz)
                .append("active", active)
                .toString();
    }

}
