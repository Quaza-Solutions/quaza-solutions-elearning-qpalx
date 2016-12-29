package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.HierarchicalLMSContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
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
@Table(name="QPalXEMicroLesson")
public class QPalXEMicroLesson implements IHierarchicalLMSContent {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="MicroLessonName", nullable=false, length=255)
    private String microLessonName;

    @Column(name="MicroLessonDescription", nullable=false, length=255)
    private String microLessonDescription;

    // Provides information on Media Content that is associated with this activity.
    @Embedded
    private ELearningMediaContent eLearningMediaContent;

    // Provides information on static micro lesson content w/o any narration.  These should be swf files
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="eLearningMediaType", column = @Column(name="StaticELearningMediaType") ),
            @AttributeOverride(name="qPalXTutorialContentTypeE", column = @Column(name="StaticQPalXTutorialContentType")),
            @AttributeOverride(name="eLearningMediaFile", column = @Column(name="StaticELearningMediaFile")),
            @AttributeOverride(name="eLearningMediaPhysicalFile", column = @Column(name="StaticELearningMediaPhysicalFile"))
    } )
    private ELearningMediaContent staticELearningMediaContent;

    // Always fetch this Eager as we always need the parent QPalXELesson always avaialable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalXELessonID", nullable = false)
    private QPalXELesson qPalXELesson;


    // DateTime that the ELearning Media Content was uploaded on the QPalX platform.
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    // IF set to true then this QPalXEMicroLesson is currently active
    @Column(name="MicroLessonActive", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean microLessonActive;


    // Collection of all the QPalXEMicroLessonActivity available as part of this micro lesson
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qPalXEMicroLesson")
    private Set<AdaptiveLearningQuiz> adaptiveLearningQuizzes = new HashSet<>();


    public QPalXEMicroLesson() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicroLessonName() {
        return microLessonName;
    }

    public void setMicroLessonName(String microLessonName) {
        this.microLessonName = microLessonName;
    }

    public String getMicroLessonDescription() {
        return microLessonDescription;
    }

    public void setMicroLessonDescription(String microLessonDescription) {
        this.microLessonDescription = microLessonDescription;
    }

    public ELearningMediaContent geteLearningMediaContent() {
        return eLearningMediaContent;
    }

    public void seteLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    public ELearningMediaContent getStaticELearningMediaContent() {
        return staticELearningMediaContent;
    }

    public void setStaticELearningMediaContent(ELearningMediaContent staticELearningMediaContent) {
        this.staticELearningMediaContent = staticELearningMediaContent;
    }

    public QPalXELesson getQPalXELesson() {
        return qPalXELesson;
    }

    public void setQPalXELesson(QPalXELesson qPalXELesson) {
        this.qPalXELesson = qPalXELesson;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isMicroLessonActive() {
        return microLessonActive;
    }

    public void setMicroLessonActive(boolean microLessonActive) {
        this.microLessonActive = microLessonActive;
    }

    public Set<AdaptiveLearningQuiz> getAdaptiveLearningQuizzes() {
        return ImmutableSet.copyOf(adaptiveLearningQuizzes);
    }

    public void addAdaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");
        adaptiveLearningQuizzes.add(adaptiveLearningQuiz);
    }

    @Override
    public String getHierarchicalLMSContentName() {
        return getMicroLessonName();
    }

    @Override
    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE() {
        return HierarchicalLMSContentTypeE.MicroLesson;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContentParent() {
        return getQPalXELesson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXEMicroLesson that = (QPalXEMicroLesson) o;

        return new EqualsBuilder()
                .append(microLessonActive, that.microLessonActive)
                .append(id, that.id)
                .append(microLessonName, that.microLessonName)
                .append(microLessonDescription, that.microLessonDescription)
                .append(eLearningMediaContent, that.eLearningMediaContent)
                .append(staticELearningMediaContent, that.staticELearningMediaContent)
                .append(qPalXELesson, that.qPalXELesson)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(microLessonName)
                .append(microLessonDescription)
                .append(eLearningMediaContent)
                .append(staticELearningMediaContent)
                .append(entryDate)
                .append(microLessonActive)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("microLessonName", microLessonName)
                .append("microLessonDescription", microLessonDescription)
                .append("eLearningMediaContent", eLearningMediaContent)
                .append("staticELearningMediaContent", staticELearningMediaContent)
                .append("qPalXELesson", qPalXELesson)
                .append("entryDate", entryDate)
                .append("microLessonActive", microLessonActive)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final QPalXEMicroLesson qpalxMicroLesson = new QPalXEMicroLesson();

        public Builder microLessonName(String microLessonName) {
            qpalxMicroLesson.microLessonName = microLessonName;
            return this;
        }

        public Builder microLessonDescription(String microLessonDescription) {
            qpalxMicroLesson.microLessonDescription = microLessonDescription;
            return this;
        }

        public Builder eLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
            qpalxMicroLesson.eLearningMediaContent = eLearningMediaContent;
            return this;
        }

        public Builder staticELearningMediaContent(ELearningMediaContent staticELearningMediaContent) {
            qpalxMicroLesson.staticELearningMediaContent = staticELearningMediaContent;
            return this;
        }

        public Builder qPalXELesson(QPalXELesson qPalXELesson) {
            qpalxMicroLesson.qPalXELesson = qPalXELesson;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            qpalxMicroLesson.entryDate = entryDate;
            return this;
        }

        public Builder microLessonActive(boolean microLessonActive) {
            qpalxMicroLesson.microLessonActive = microLessonActive;
            return this;
        }

        public Builder adaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
            qpalxMicroLesson.addAdaptiveLearningQuiz(adaptiveLearningQuiz);
            return this;
        }

        public QPalXEMicroLesson build() {
            return qpalxMicroLesson;
        }
    }
}
