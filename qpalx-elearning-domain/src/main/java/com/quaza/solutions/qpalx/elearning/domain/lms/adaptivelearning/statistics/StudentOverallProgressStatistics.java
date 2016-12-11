package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class StudentOverallProgressStatistics {



    private final Long studentID;

    private final Long curriculumID;

    private final String curriculumName;

    private final String curriculumDescription;

    private final String curriculumIcon;

    private final String curriculumType;

    private final Integer totalNumberOfLessons;

    private final Integer totalNumberOfMicroLessons;

    private final Integer totalNumberOfQuizzes;

    private final Integer totalNumberOfQuestionBankItems;

    private final Integer uniqueMicroLessonsAttempted;

    private final Integer uniqueQuizzesAttempted;

    private final Integer uniqueQuestionBankItemsAttempted;


    public StudentOverallProgressStatistics(Long studentID, Long curriculumID, String curriculumName, String curriculumDescription, String curriculumIcon, String curriculumType, Integer totalNumberOfLessons, Integer totalNumberOfMicroLessons, Integer totalNumberOfQuizzes, Integer totalNumberOfQuestionBankItems, Integer uniqueMicroLessonsAttempted, Integer uniqueQuizzesAttempted, Integer uniqueQuestionBankItemsAttempted) {
        this.studentID = studentID;
        this.curriculumID = curriculumID;
        this.curriculumName = curriculumName;
        this.curriculumDescription = curriculumDescription;
        this.curriculumIcon = curriculumIcon;
        this.curriculumType = curriculumType;
        this.totalNumberOfLessons = totalNumberOfLessons;
        this.totalNumberOfMicroLessons = totalNumberOfMicroLessons;
        this.totalNumberOfQuizzes = totalNumberOfQuizzes;
        this.totalNumberOfQuestionBankItems = totalNumberOfQuestionBankItems;
        this.uniqueMicroLessonsAttempted = uniqueMicroLessonsAttempted;
        this.uniqueQuizzesAttempted = uniqueQuizzesAttempted;
        this.uniqueQuestionBankItemsAttempted = uniqueQuestionBankItemsAttempted;
    }

    public Long getStudentID() {
        return studentID;
    }

    public Long getCurriculumID() {
        return curriculumID;
    }

    public String getCurriculumName() {
        return curriculumName;
    }

    public String getCurriculumDescription() {
        return curriculumDescription;
    }

    public String getCurriculumIcon() {
        return curriculumIcon;
    }

    public String getCurriculumType() {
        return curriculumType;
    }

    public Integer getTotalNumberOfLessons() {
        return totalNumberOfLessons;
    }

    public Integer getTotalNumberOfMicroLessons() {
        return totalNumberOfMicroLessons;
    }

    public Integer getTotalNumberOfQuizzes() {
        return totalNumberOfQuizzes;
    }

    public Integer getTotalNumberOfQuestionBankItems() {
        return totalNumberOfQuestionBankItems;
    }

    public Integer getUniqueMicroLessonsAttempted() {
        return uniqueMicroLessonsAttempted;
    }

    public Integer getUniqueQuizzesAttempted() {
        return uniqueQuizzesAttempted;
    }

    public Integer getUniqueQuestionBankItemsAttempted() {
        return uniqueQuestionBankItemsAttempted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("studentID", studentID)
                .append("curriculumID", curriculumID)
                .append("curriculumName", curriculumName)
                .append("curriculumDescription", curriculumDescription)
                .append("curriculumIcon", curriculumIcon)
                .append("curriculumType", curriculumType)
                .append("totalNumberOfLessons", totalNumberOfLessons)
                .append("totalNumberOfMicroLessons", totalNumberOfMicroLessons)
                .append("totalNumberOfQuizzes", totalNumberOfQuizzes)
                .append("totalNumberOfQuestionBankItems", totalNumberOfQuestionBankItems)
                .append("uniqueMicroLessonsAttempted", uniqueMicroLessonsAttempted)
                .append("uniqueQuizzesAttempted", uniqueQuizzesAttempted)
                .append("uniqueQuestionBankItemsAttempted", uniqueQuestionBankItemsAttempted)
                .toString();
    }

    public static StudentOverallProgressStatisticsRowMapper newRowMapper() {
        return new StudentOverallProgressStatisticsRowMapper();
    }

    public static class StudentOverallProgressStatisticsRowMapper implements RowMapper<StudentOverallProgressStatistics> {

        @Override
        public StudentOverallProgressStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
            Long studentID = resultSet.getLong("StudentID");
            Long curriculumID = resultSet.getLong("CurriculumID");
            String curriculumName = resultSet.getString("CurriculumName");
            String curriculumDescription = resultSet.getString("CurriculumDescription");
            String curriculumIcon = resultSet.getString("CurriculumIcon");
            String curriculumType = resultSet.getString("CurriculumType");
            Integer totalNumberOfLessons = resultSet.getInt("TotalNumberOfLessons");
            Integer totalNumberOfMicroLessons = resultSet.getInt("TotalNumberOfMicroLessons");
            Integer totalNumberOfQuizzes = resultSet.getInt("TotalNumberOfQuizzes");
            Integer totalNumberOfQuestionBankItems = resultSet.getInt("TotalNumberOfQuestionBankItems");
            Integer uniqueMicroLessonsAttempted = resultSet.getInt("UniqueMicroLessonsAttempted");
            Integer uniqueQuizzesAttempted = resultSet.getInt("UniqueQuizzesAttempted");
            Integer uniqueQuestionBankItemsAttempted = resultSet.getInt("UniqueQuestionBankItemsAttempted");
            return new StudentOverallProgressStatistics(studentID, curriculumID, curriculumName, curriculumDescription, curriculumIcon, curriculumType, totalNumberOfLessons, totalNumberOfMicroLessons, totalNumberOfQuizzes, totalNumberOfQuestionBankItems, uniqueMicroLessonsAttempted, uniqueQuizzesAttempted, uniqueQuestionBankItemsAttempted);
        }
    }
}
