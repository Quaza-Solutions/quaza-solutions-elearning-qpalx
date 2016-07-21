package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 *
 * @author manyce400
 */
@Entity
@Table(name="ELearningCurriculum")
public class ELearningCurriculum {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public StudentTutorialGrade getStudentTutorialGrade() {
        return studentTutorialGrade;
    }

    public void setStudentTutorialGrade(StudentTutorialGrade studentTutorialGrade) {
        this.studentTutorialGrade = studentTutorialGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ELearningCurriculum that = (ELearningCurriculum) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(curriculumType, that.curriculumType)
                .append(curriculumName, that.curriculumName)
                .append(curriculumDescription, that.curriculumDescription)
                .append(studentTutorialGrade, that.studentTutorialGrade)
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
                .toString();
    }
}
