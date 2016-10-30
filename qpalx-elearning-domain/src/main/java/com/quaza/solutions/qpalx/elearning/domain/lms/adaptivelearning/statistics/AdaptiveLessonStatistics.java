package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Precision;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class AdaptiveLessonStatistics {



    private final Long LessonID;

    private final String lessonName;

    private final String lessonMediaFile;

    private final Integer microLessonsAttempted;

    private final Integer totalMicroLessons;

    private final Integer uniqueQuizzesAttempted;

    private final Integer totalQuizzes;


    public AdaptiveLessonStatistics(Long lessonID, String lessonName, String lessonMediaFile, Integer microLessonsAttempted, Integer totalMicroLessons, Integer uniqueQuizzesAttempted, Integer totalQuizzes) {
        LessonID = lessonID;
        this.lessonName = lessonName;
        this.lessonMediaFile = lessonMediaFile;
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
                .append("LessonID", LessonID)
                .append("lessonName", lessonName)
                .append("lessonMediaFile", lessonMediaFile)
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
            String lessonMediaFile = resultSet.getString("qpl.ELearningMediaFile");
            Integer microLessonsAttempted = resultSet.getInt("UniqueMicroLessonsAttempted");
            Integer totalMicroLessons = resultSet.getInt("TotalNumberOfMicroLessons");
            Integer uniqueQuizzesAttempted = resultSet.getInt("UniqueQuizzesAttempted");
            Integer totalQuizzes = resultSet.getInt("TotalNumberOfQuizzes");
            return new AdaptiveLessonStatistics(lessonID, lessonName, lessonMediaFile, microLessonsAttempted, totalMicroLessons, uniqueQuizzesAttempted, totalQuizzes);
        }
    }
}
