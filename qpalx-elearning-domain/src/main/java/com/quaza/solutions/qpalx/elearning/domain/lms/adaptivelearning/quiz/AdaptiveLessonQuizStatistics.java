package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author manyce400
 */
public class AdaptiveLessonQuizStatistics {



    private Long lessonID;

    private String lessonName;

    private ProficiencyRankingScaleE proficiencyRankingScaleFloor;

    private ProficiencyRankingScaleE proficiencyRankingScaleCeiling;

    private Long microLessonID;

    private String microLessonTitle;

    private Long adaptiveLearningQuizID;

    private String adaptiveLearningQuizTitle;

    private Double proficiencyScore;

    private DateTime learningExperienceStartDate;

    private DateTime learningExperienceCompletedDate;

    private QPalXTutorialContentTypeE qpalxTutorialContentType;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss.SSS");


    public AdaptiveLessonQuizStatistics() {

    }

    public Long getLessonID() {
        return lessonID;
    }

    public void setLessonID(Long lessonID) {
        this.lessonID = lessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
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

    public String getAdaptiveLearningQuizTitle() {
        return adaptiveLearningQuizTitle;
    }

    public void setAdaptiveLearningQuizTitle(String adaptiveLearningQuizTitle) {
        this.adaptiveLearningQuizTitle = adaptiveLearningQuizTitle;
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

    public boolean hasQuizAttempt() {
        if(learningExperienceStartDate != null) {
            return true;
        }

        return false;
    }

    public boolean isPerformanceAboveAverage() {
        if(hasQuizAttempt()) {
            Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(proficiencyScore);

            if(proficiencyScoreRangeE.isPresent()) {
                return proficiencyScoreRangeE.get() == ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER || proficiencyScoreRangeE.get().isAboveScoreRange(ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER);
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("microLessonID", microLessonID)
                .append("microLessonTitle", microLessonTitle)
                .append("adaptiveLearningQuizID", adaptiveLearningQuizID)
                .append("adaptiveLearningQuizTitle", adaptiveLearningQuizTitle)
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

            // Set Lesson details
            adaptiveLessonQuizStatistics.setLessonID(resultSet.getLong("LessonID"));
            adaptiveLessonQuizStatistics.setLessonName(resultSet.getString("LessonName"));

            ProficiencyRankingScaleE floor = ProficiencyRankingScaleE.valueOf(resultSet.getString("ProficiencyRankingScaleFloor"));
            ProficiencyRankingScaleE ceiling = ProficiencyRankingScaleE.valueOf(resultSet.getString("ProficiencyRankingScaleCeiling"));
            adaptiveLessonQuizStatistics.setProficiencyRankingScaleFloor(floor);
            adaptiveLessonQuizStatistics.setProficiencyRankingScaleCeiling(ceiling);


            adaptiveLessonQuizStatistics.setMicroLessonID(resultSet.getLong("MicroLessonID"));
            adaptiveLessonQuizStatistics.setMicroLessonTitle(resultSet.getString("MicroLessonName"));
            adaptiveLessonQuizStatistics.setAdaptiveLearningQuizID(resultSet.getLong("QuizID"));
            adaptiveLessonQuizStatistics.setAdaptiveLearningQuizTitle(resultSet.getString("QuizTitle"));
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

}
