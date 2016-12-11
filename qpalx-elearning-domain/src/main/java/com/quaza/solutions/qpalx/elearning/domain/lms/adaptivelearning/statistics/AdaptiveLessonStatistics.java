package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.Precision;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class AdaptiveLessonStatistics {



    private final Long LessonID;

    private final String lessonName;

    private final String lessonMediaFile;

    private final Integer questionBankItemsAttempted;

    private final Integer totalQuestionBankItems;

    private final Integer microLessonsAttempted;

    private final Integer totalMicroLessons;

    private final Integer uniqueQuizzesAttempted;

    private final Integer totalQuizzes;


    public AdaptiveLessonStatistics(Long lessonID, String lessonName, String lessonMediaFile,
                                    Integer questionBankItemsAttempted, Integer totalQuestionBankItems,
                                    Integer microLessonsAttempted, Integer totalMicroLessons, Integer uniqueQuizzesAttempted, Integer totalQuizzes) {
        LessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonMediaFile = lessonMediaFile;
        this.questionBankItemsAttempted = questionBankItemsAttempted;
        this.totalQuestionBankItems = totalQuestionBankItems;
        this.microLessonsAttempted = microLessonsAttempted;
        this.totalMicroLessons = totalMicroLessons;
        this.uniqueQuizzesAttempted = uniqueQuizzesAttempted;
        this.totalQuizzes = totalQuizzes;
    }

    public Long getLessonID() {
        return LessonID;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getLessonMediaFile() {
        return lessonMediaFile;
    }

    public Integer getQuestionBankItemsAttempted() {
        return questionBankItemsAttempted;
    }

    public Integer getTotalQuestionBankItems() {
        return totalQuestionBankItems;
    }

    public Integer getMicroLessonsAttempted() {
        return microLessonsAttempted;
    }

    public Integer getTotalMicroLessons() {
        return totalMicroLessons;
    }

    public Integer getUniqueQuizzesAttempted() {
        return uniqueQuizzesAttempted;
    }

    public Integer getTotalQuizzes() {
        return totalQuizzes;
    }

    public double getTotalLessonCompletionRate() {
        // completion rate = (unique_micro_lessons_attempt + quiz_attempts) / (total_microlessons + total_quizzes)
        BigDecimal totalAdaptiveItems = new BigDecimal(totalQuestionBankItems).add(new BigDecimal(totalMicroLessons)).add(new BigDecimal(totalQuizzes));
        BigDecimal totalAttempted = new BigDecimal(questionBankItemsAttempted).add(new BigDecimal(microLessonsAttempted)).add(new BigDecimal(uniqueQuizzesAttempted));
        double completionRate = totalAttempted.divide(totalAdaptiveItems, 2, RoundingMode.HALF_UP).doubleValue() * 100;
        return Precision.round(completionRate, 0);
    }

    public double getMicroLessonCompletionRate() {
        if (microLessonsAttempted > 0 && totalMicroLessons > 0) {
            double completion = (microLessonsAttempted / totalMicroLessons) * 100;
            return Precision.round(completion, 0);
        }

        return 0;
    }

    public double getQuizzesCompletionRate() {
        if (uniqueQuizzesAttempted > 0 && totalQuizzes > 0) {
            double completion = (uniqueQuizzesAttempted / totalQuizzes) * 100;
            return Precision.round(completion, 0);
        }

        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("LessonID", LessonID)
                .append("lessonName", lessonName)
                .append("lessonMediaFile", lessonMediaFile)
                .append("questionBankItemsAttempted", questionBankItemsAttempted)
                .append("totalQuestionBankItems", totalQuestionBankItems)
                .append("microLessonsAttempted", microLessonsAttempted)
                .append("totalMicroLessons", totalMicroLessons)
                .append("uniqueQuizzesAttempted", uniqueQuizzesAttempted)
                .append("totalQuizzes", totalQuizzes)
                .toString();
    }

    public static AdaptiveLessonProgressStatisticsRowMapper newRowMapper() {
        return new AdaptiveLessonProgressStatisticsRowMapper();
    }


    public static class AdaptiveLessonProgressStatisticsRowMapper implements RowMapper<AdaptiveLessonStatistics> {
        @Override
        public AdaptiveLessonStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
            long lessonID = resultSet.getLong("LessonID");
            String lessonName = resultSet.getString("LessonName");
            String lessonMediaFile = resultSet.getString("LessonIntroVideo");

            Integer uniqueQuestionBankItemsAttempted = resultSet.getInt("UniqueQuestionBankItemsAttempted");
            Integer totalNumberOfQuestionBankItems = resultSet.getInt("TotalNumberOfQuestionBankItems");

            Integer microLessonsAttempted = resultSet.getInt("UniqueMicroLessonsAttempted");
            Integer totalMicroLessons = resultSet.getInt("TotalNumberOfMicroLessons");
            Integer uniqueQuizzesAttempted = resultSet.getInt("UniqueQuizzesAttempted");
            Integer totalQuizzes = resultSet.getInt("TotalNumberOfQuizzes");
            return new AdaptiveLessonStatistics(lessonID, lessonName, lessonMediaFile, uniqueQuestionBankItemsAttempted, totalNumberOfQuestionBankItems, microLessonsAttempted, totalMicroLessons, uniqueQuizzesAttempted, totalQuizzes);
        }
    }

}
