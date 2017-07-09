package com.quaza.solutions.qpalx.elearning.domain.lms.assessment;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.HierarchicalLMSContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="CurriculumProficiencyAssessment")
public class CurriculumProficiencyAssessment implements IHierarchicalLMSContent {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // ELearningCurriculum that this assessment is
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCurriculumID", nullable = false)
    private ELearningCurriculum eLearningCurriculum;


    // DateTime that the CurriculumProficiencyAssessment was added to the system
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;


    /// DateTime that the CurriculumProficiencyAssessment was last updated
    @Column(name="LastModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModifyDate;


    // Collection of all Course Assessment focus areas
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "curriculumProficiencyAssessment")
    private Set<CourseAssessmentFocusArea> courseAssessmentFocusAreas = new HashSet<>();

    public static final String CLASS_ATTRIBUTE_IDENTIFIER = "CurriculumProficiencyAssessment";


    public CurriculumProficiencyAssessment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ELearningCurriculum getELearningCurriculum() {
        return eLearningCurriculum;
    }

    public void setELearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        this.eLearningCurriculum = eLearningCurriculum;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public DateTime getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(DateTime lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public void addCourseAssessmentFocusArea(CourseAssessmentFocusArea courseAssessmentFocusArea) {
        Assert.notNull(courseAssessmentFocusArea);
        courseAssessmentFocusAreas.add(courseAssessmentFocusArea);
    }

    public Set<CourseAssessmentFocusArea> getCourseAssessmentFocusAreas() {
        return ImmutableSet.copyOf(courseAssessmentFocusAreas);
    }

    public Optional<CourseAssessmentFocusArea> getCourseAssessmentFocusAreaByID(Long courseAssessmentFocusAreaID) {
        Assert.notNull(courseAssessmentFocusAreaID, "courseAssessmentFocusAreaID cannot be null");

        for(CourseAssessmentFocusArea courseAssessmentFocusArea : courseAssessmentFocusAreas) {
            if(courseAssessmentFocusAreaID.equals(courseAssessmentFocusArea.getId())) {
                return Optional.of(courseAssessmentFocusArea);
            }
        }

        return Optional.empty();
    }

    @Override
    public String getHierarchicalLMSContentName() {
        String name = getELearningCurriculum().getCurriculumName() + " Assessment";
        return null;
    }

    @Override
    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE() {
        return HierarchicalLMSContentTypeE.Assessment;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContentParent() {
        return getELearningCurriculum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CurriculumProficiencyAssessment that = (CurriculumProficiencyAssessment) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(eLearningCurriculum, that.eLearningCurriculum)
                .append(entryDate, that.entryDate)
                .append(lastModifyDate, that.lastModifyDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(eLearningCurriculum)
                .append(entryDate)
                .append(lastModifyDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("eLearningCurriculum", eLearningCurriculum)
                .append("entryDate", entryDate)
                .append("lastModifyDate", lastModifyDate)
                .append("courseAssessmentFocusAreas", courseAssessmentFocusAreas)
                .toString();
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CurriculumProficiencyAssessment curriculumProficiencyAssessment = new CurriculumProficiencyAssessment();

        public Builder() {

        }

        public Builder eLearningCurriculum(ELearningCurriculum eLearningCurriculum) {
            curriculumProficiencyAssessment.eLearningCurriculum = eLearningCurriculum;
            return this;
        }

        public Builder courseAssessmentFocusArea(CourseAssessmentFocusArea courseAssessmentFocusArea) {
            curriculumProficiencyAssessment.addCourseAssessmentFocusArea(courseAssessmentFocusArea);
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            curriculumProficiencyAssessment.entryDate = entryDate;
            return this;
        }

        public Builder lastModifyDate(DateTime lastModifyDate) {
            curriculumProficiencyAssessment.lastModifyDate = lastModifyDate;
            return this;
        }

        public CurriculumProficiencyAssessment build() {
            return curriculumProficiencyAssessment;
        }

    }

}
