package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
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
     * @param qPalXUser
     * @param eLearningCurriculum
     * @return List<AdaptiveLearningExperience>
     */
    @Query(
            "Select         adaptiveLearningExperience "+
            "From           AdaptiveLearningExperience adaptiveLearningExperience "+
            "INNER  JOIN    FETCH adaptiveLearningExperience.qpalxUser qpalxUser "+
            "INNER  JOIN    FETCH adaptiveLearningExperience.eLearningCourseActivity as eLearningCourseActivity "+
            "INNER  JOIN    FETCH eLearningCourseActivity.eLearningCourse as eLearningCourse "+
            "INNER  JOIN    FETCH eLearningCourse.eLearningCurriculum as eLearningCurriculum "+
            "Where          qpalxUser = ?1 "+
            "And            eLearningCurriculum =?2"
    )
    public List<AdaptiveLearningExperience> findAllQPalxUserCurriculumLearningExperiences(final QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);

}
