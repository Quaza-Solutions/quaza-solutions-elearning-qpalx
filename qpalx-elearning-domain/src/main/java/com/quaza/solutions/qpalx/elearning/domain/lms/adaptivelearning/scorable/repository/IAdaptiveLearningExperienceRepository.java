package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author manyce400
 */
public interface IAdaptiveLearningExperienceRepository extends CrudRepository<AdaptiveLearningExperience, Long> {


    /**
     * Find list of all AdaptiveLearningExperience for given QPalxUser and the specified ELearningCurriculum.
     *
     * @return List<AdaptiveLearningExperience>
     */
    @Query(
            value = "Select    alE.* "
                    + "From      AdaptiveLearningExperience alE "
                    + "Left      Outer Join  AdaptiveLearningQuiz alQ on alQ.ID = alE.ScorableActivityID "
                    + "Left      Outer Join  QPalXEMicroLesson qeML on qeML.ID = alQ.QPalXEMicroLessonID "
                    + "Left      Outer Join  QPalXELesson qpEL on qpEL.ID = qeML.QPalXELessonID "
                    + "Left      Outer Join  ELearningCourse elC on elC.ID = qpEL.ELearningCourseID "
                    + "Where     elC.ELearningCurriculumID = ?  "
                    + "And       alE.QPalxUserID = ?  "
                    + "Order    By LearningExperienceStartDate asc ",
            nativeQuery = true
    )
    public List<AdaptiveLearningExperience> findAllAccrossELearningCurriculum(Long elearningCurriculum, Long studentID);


    /**
     * Find list of all AdaptiveLearningExperience for given QPalxUser with a specific ScorableActivityID
     *
     * @return List<AdaptiveLearningExperience>
     */
    @Query(
            value = "Select    alE.* "
                    + "From    AdaptiveLearningExperience alE "
                    + "Where   ScorableActivityID = ? "
                    + "And     QPalxUserID  = ?  "
                    + "Order   By LearningExperienceStartDate asc ",
            nativeQuery = true
    )
    public List<AdaptiveLearningExperience> findAllWithScorableActivityID(Long scorableActivityID, Long studentID);

}
