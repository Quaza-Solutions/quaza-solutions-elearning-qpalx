package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.user.QPalXUser;
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
            "Select adaptiveLearningExperience "+
            "From   AdaptiveLearningExperience adaptiveLearningExperience "+
            "JOIN   FETCH adaptiveLearningExperience.adaptiveLearningProfile.qpalxUser "+
            "JOIN   FETCH adaptiveLearningExperience.eLearningCourseActivity.eLearningCourse.eLearningCurriculum "+
            "Where  adaptiveLearningExperience.adaptiveLearningProfile.qpalxUser = ?1 "+
            "And    adaptiveLearningExperience.eLearningCourseActivity.eLearningCourse.eLearningCurriculum =?2"
    )
    public List<AdaptiveLearningExperience> findAllQPalxUserCurriculumLearningExperiences(final QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);

}
