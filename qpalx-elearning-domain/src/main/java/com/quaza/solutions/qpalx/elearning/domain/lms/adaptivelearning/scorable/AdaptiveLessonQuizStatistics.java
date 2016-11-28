package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class AdaptiveLessonQuizStatistics {



    private Long studentID;

    private Long microLessonID;

    private String microLessonTitle;

    private Long adaptiveLearningQuizID;

    private Integer scorableActivityID;

    private String scorableActivityName;

    private Double proficiencyScore;

    private DateTime learningExperienceStartDate;

    private DateTime learningExperienceCompletedDate;

    private QPalXTutorialContentTypeE qpalxTutorialContentType;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss.SSS");


    public AdaptiveLessonQuizStatistics() {

    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getMicroLessonID() {
        return microLessonID;
    }

    public void setMicroLessonID(Long microLessonID) {
        this.microLessonID = microLessonID;
    }

    public String getMicroLessonTitle() {
        return microLessonTitle;
    }

    public void setMicroLessonTitle(String microLessonTitle) {
        this.microLessonTitle = microLessonTitle;
    }

    public Long getAdaptiveLearningQuizID() {
        return adaptiveLearningQuizID;
    }

    public void setAdaptiveLearningQuizID(Long adaptiveLearningQuizID) {
        this.adaptiveLearningQuizID = adaptiveLearningQuizID;
    }

    public Integer getScorableActivityID() {
        return scorableActivityID;
    }

    public void setScorableActivityID(Integer scorableActivityID) {
        this.scorableActivityID = scorableActivityID;
    }

    public String getScorableActivityName() {
        return scorableActivityName;
    }

    public void setScorableActivityName(String scorableActivityName) {
        this.scorableActivityName = scorableActivityName;
    }

    public Double getProficiencyScore() {
        return proficiencyScore;
    }

    public void setProficiencyScore(Double proficiencyScore) {
        this.proficiencyScore = proficiencyScore;
    }

    public DateTime getLearningExperienceStartDate() {
        return learningExperienceStartDate;
    }

    public void setLearningExperienceStartDate(DateTime learningExperienceStartDate) {
        this.learningExperienceStartDate = learningExperienceStartDate;
    }

    public DateTime getLearningExperienceCompletedDate() {
        return learningExperienceCompletedDate;
    }

    public void setLearningExperienceCompletedDate(DateTime learningExperienceCompletedDate) {
        this.learningExperienceCompletedDate = learningExperienceCompletedDate;
    }

    public QPalXTutorialContentTypeE getQpalxTutorialContentType() {
        return qpalxTutorialContentType;
    }

    public void setQpalxTutorialContentType(QPalXTutorialContentTypeE qpalxTutorialContentType) {
        this.qpalxTutorialContentType = qpalxTutorialContentType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("studentID", studentID)
                .append("microLessonID", microLessonID)
                .append("microLessonTitle", microLessonTitle)
                .append("adaptiveLearningQuizID", adaptiveLearningQuizID)
                .append("scorableActivityID", scorableActivityID)
                .append("scorableActivityName", scorableActivityName)
                .append("proficiencyScore", proficiencyScore)
                .append("learningExperienceStartDate", learningExperienceStartDate)
                .append("learningExperienceCompletedDate", learningExperienceCompletedDate)
                .append("qpalxTutorialContentType", qpalxTutorialContentType)
                .toString();
    }


    public static AdaptiveLessonQuizStatisticsRowMapper newRowMapper() {
        return new AdaptiveLessonQuizStatisticsRowMapper();
    }


    public static class AdaptiveLessonQuizStatisticsRowMapper implements RowMapper<AdaptiveLessonQuizStatistics> {
        @Override
        public AdaptiveLessonQuizStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
            AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics = new AdaptiveLessonQuizStatistics();
            adaptiveLessonQuizStatistics.setStudentID(resultSet.getLong("StudentID"));
            adaptiveLessonQuizStatistics.setMicroLessonID(resultSet.getLong("MicroLessonID"));
            adaptiveLessonQuizStatistics.setMicroLessonTitle(resultSet.getString("MicroLessonName"));
            adaptiveLessonQuizStatistics.setAdaptiveLearningQuizID(resultSet.getLong("QuizID"));
            adaptiveLessonQuizStatistics.setScorableActivityID(resultSet.getInt("ScorableActivityID"));
            adaptiveLessonQuizStatistics.setScorableActivityName(resultSet.getString("ScorableActivityName"));
            adaptiveLessonQuizStatistics.setProficiencyScore(resultSet.getDouble("ProficiencyScore"));

            String date1 = resultSet.getString("LearningExperienceStartDate");
            String date2 = resultSet.getString("LearningExperienceCompletedDate");
            if (date1 != null && date2 != null) {
                DateTime startDate = dateTimeFormatter.parseDateTime(date1);
                DateTime endDate = dateTimeFormatter.parseDateTime(date2);
                adaptiveLessonQuizStatistics.setLearningExperienceStartDate(startDate);
                adaptiveLessonQuizStatistics.setLearningExperienceCompletedDate(endDate);
            }

            return adaptiveLessonQuizStatistics;
        }
    }


    public static void main(String[] args) {
        String test = "2016-11-27 19:34:26.0";
        DateTime startDate = dateTimeFormatter.parseDateTime(test);
        System.out.println("startDate = " + startDate);
    }
}
