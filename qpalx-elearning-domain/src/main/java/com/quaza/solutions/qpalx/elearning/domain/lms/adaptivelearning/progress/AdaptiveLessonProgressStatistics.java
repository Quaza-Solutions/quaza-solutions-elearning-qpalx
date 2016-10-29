package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Precision;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class AdaptiveLessonProgressStatistics {


    private final int microLessonsAttempted;

    private final int totalMicroLessons;

    private final int uniqueQuizzesAttempted;

    private final int totalQuizzes;

    private final String lessonName;


    public AdaptiveLessonProgressStatistics(int microLessonsAttempted, int totalMicroLessons, int uniqueQuizzesAttempted, int totalQuizzes, String lessonName) {
        this.microLessonsAttempted = microLessonsAttempted;
        this.totalMicroLessons = totalMicroLessons;
        this.uniqueQuizzesAttempted = uniqueQuizzesAttempted;
        this.totalQuizzes = totalQuizzes;
        this.lessonName = lessonName;
    }

    public int getMicroLessonsAttempted() {
        return microLessonsAttempted;
    }

    public int getTotalMicroLessons() {
        return totalMicroLessons;
    }

    public int getUniqueQuizzesAttempted() {
        return uniqueQuizzesAttempted;
    }

    public int getTotalQuizzes() {
        return totalQuizzes;
    }

    public String getLessonName() {
        return lessonName;
    }

    public double getTotalLessonCompletionRate() {
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        descriptiveStatistics.addValue(getQuizzesCompletionRate());
        descriptiveStatistics.addValue(getMicroLessonCompletionRate());
        double completion = descriptiveStatistics.getMean();
        return Precision.round(completion, 0);
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
                .append("microLessonsAttempted", microLessonsAttempted)
                .append("totalMicroLessons", totalMicroLessons)
                .append("uniqueQuizzesAttempted", uniqueQuizzesAttempted)
                .append("totalQuizzes", totalQuizzes)
                .append("lessonName", lessonName)
                .toString();
    }

    public static AdaptiveLessonProgressStatisticsRowMapper newRowMapper() {
        return new AdaptiveLessonProgressStatisticsRowMapper();
    }


    public static class AdaptiveLessonProgressStatisticsRowMapper implements RowMapper<AdaptiveLessonProgressStatistics> {
        @Override
        public AdaptiveLessonProgressStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
            int microLessonsAttempted = resultSet.getInt("UniqueMicroLessonsAttempted");
            int totalMicroLessons = resultSet.getInt("TotalNumberOfLessons");
            int uniqueQuizzesAttempted = resultSet.getInt("UniqueQuizzesAttempted");
            int totalQuizzes = resultSet.getInt("TotalNumberOfQuizzes");
            String lessonName = resultSet.getString("LessonName");
            return new AdaptiveLessonProgressStatistics(microLessonsAttempted, totalMicroLessons, uniqueQuizzesAttempted, totalQuizzes, lessonName);
        }
    }
}
