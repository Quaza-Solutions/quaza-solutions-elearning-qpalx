package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
public class ContentAdminProfileHolder {


    // Content developers admin profile
    private ContentAdminProfileRecord contentAdminProfileRecord;

    // Student tutorial grades which this user is authorized to create content for.
    private List<StudentTutorialGrade> studentTutorialGrades;

    private Table<StudentTutorialGrade, CurriculumType, List<ELearningCurriculum>> tutorialGradeToELearningCurricula = HashBasedTable.create();


    public ContentAdminProfileHolder(ContentAdminProfileRecord contentAdminProfileRecord, List<StudentTutorialGrade> studentTutorialGrades) {
        this.contentAdminProfileRecord = contentAdminProfileRecord;
        this.studentTutorialGrades = studentTutorialGrades;
    }

    public ContentAdminProfileRecord getContentAdminProfileRecord() {
        return contentAdminProfileRecord;
    }

    public List<StudentTutorialGrade> getStudentTutorialGrades() {
        return studentTutorialGrades;
    }

    public void addELearningCurriculumByStudentTutorialGrade(StudentTutorialGrade studentTutorialGrade, CurriculumType curriculumType, List<ELearningCurriculum> eLearningCurricula) {
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        Assert.notNull(curriculumType, "curriculumType cannot be null");
        Assert.notNull(eLearningCurricula, "eLearningCurricula cannot be null");
        Assert.isTrue(!eLearningCurricula.isEmpty(), "eLearningCurricula cannot be null");
        tutorialGradeToELearningCurricula.put(studentTutorialGrade, curriculumType, eLearningCurricula);
    }

    public List<ELearningCurriculum> getELearningCurriculaByTutorialGrade(StudentTutorialGrade studentTutorialGrade, CurriculumType curriculumType) {
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        Assert.notNull(curriculumType, "curriculumType cannot be null");
        return tutorialGradeToELearningCurricula.get(studentTutorialGrade, curriculumType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("contentAdminProfileRecord", contentAdminProfileRecord)
                .append("studentTutorialGrades", studentTutorialGrades)
                .append("tutorialGradeToELearningCurricula", tutorialGradeToELearningCurricula)
                .toString();
    }
}
