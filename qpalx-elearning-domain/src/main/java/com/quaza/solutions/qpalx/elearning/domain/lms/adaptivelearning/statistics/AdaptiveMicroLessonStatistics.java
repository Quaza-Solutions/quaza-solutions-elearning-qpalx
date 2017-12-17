package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.math3.util.Precision;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class AdaptiveMicroLessonStatistics {


    private final Long microLessonID;

    private final String microLessonName;

    private final Integer uniqueQuizzesAttempted;

    private final Integer totalQuizzes;

    private final String narrationMediaFile;

    private final String staticMediaFile;

    private String interactiveExerciseMediaFile = null;

    public static final String CLASS_ATTRIBUTE = "AdaptiveMicroLessonStatistics";

    public AdaptiveMicroLessonStatistics(Long microLessonID, String microLessonName, Integer uniqueQuizzesAttempted, Integer totalQuizzes, String narrationMediaFile, String staticMediaFile, String interactiveExerciseMediaFile) {
        this.microLessonID = microLessonID;
        this.microLessonName = microLessonName;
        this.uniqueQuizzesAttempted = uniqueQuizzesAttempted;
        this.totalQuizzes = totalQuizzes;
        this.narrationMediaFile = narrationMediaFile;
        this.staticMediaFile = staticMediaFile;

        // Interactive Exercise media file is optional so can be empty.  Dont set if empty String
        if (!StringUtils.isEmpty(interactiveExerciseMediaFile)) {
            this.interactiveExerciseMediaFile = interactiveExerciseMediaFile;
        }
    }

    public Long getMicroLessonID() {
        return microLessonID;
    }

    public String getMicroLessonName() {
        return microLessonName;
    }

    public Integer getUniqueQuizzesAttempted() {
        return uniqueQuizzesAttempted;
    }

    public Integer getTotalQuizzes() {
        return totalQuizzes;
    }

    public String getNarrationMediaFile() {
        return narrationMediaFile;
    }

    public String getStaticMediaFile() {
        return staticMediaFile;
    }

    public String getInteractiveExerciseMediaFile() {
        return interactiveExerciseMediaFile;
    }

    public double getQuizzesCompletionRate() {
        if (uniqueQuizzesAttempted.intValue() > 0 && totalQuizzes.intValue() > 0) {
            double completion = ((double)uniqueQuizzesAttempted.intValue() / (double) totalQuizzes.intValue()) * 100;
            return Precision.round(completion, 0);
        }

        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("microLessonID", microLessonID)
                .append("microLessonName", microLessonName)
                .append("uniqueQuizzesAttempted", uniqueQuizzesAttempted)
                .append("totalQuizzes", totalQuizzes)
                .append("narrationMediaFile", narrationMediaFile)
                .append("staticMediaFile", staticMediaFile)
                .append("interactiveExerciseMediaFile", interactiveExerciseMediaFile)
                .toString();
    }

    public static AdaptiveMicroLessonStatisticsRowMapper newRowMapper() {
        return new AdaptiveMicroLessonStatisticsRowMapper();
    }

    public static class AdaptiveMicroLessonStatisticsRowMapper implements RowMapper<AdaptiveMicroLessonStatistics> {

        @Override
        public AdaptiveMicroLessonStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
            long microLessonID = resultSet.getLong("MicroLessonID");
            String microLessonName = resultSet.getString("MicroLessonName");
            String narrationMediaFile = resultSet.getString("ELearningMediaFile");
            String staticMediaFile = resultSet.getString("StaticELearningMediaFile");
            String interactiveExerciseMediaFile = resultSet.getString("InteractiveExerciseELearningMediaFile");
            Integer uniqueQuizzesAttempted = resultSet.getInt("UniqueQuizzesAttempted");
            Integer totalQuizzes = resultSet.getInt("TotalNumberOfQuizzes");
            return new AdaptiveMicroLessonStatistics(microLessonID, microLessonName, uniqueQuizzesAttempted, totalQuizzes, narrationMediaFile, staticMediaFile, interactiveExerciseMediaFile);
        }
    }
}
