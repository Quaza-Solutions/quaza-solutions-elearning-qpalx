package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CourseAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.repository.ICourseAssessmentFocusAreaRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(CourseAssessmentFocusAreaService.BEAN_NAME)
public class CourseAssessmentFocusAreaService implements ICourseAssessmentFocusAreaService {




    @Autowired
    private ICourseAssessmentFocusAreaRepository iCourseAssessmentFocusAreaRepository;

    @Autowired
    @Qualifier(AdaptiveLearningQuizService.BEAN_NAME)
    private IAdaptiveLearningQuizService iAdaptiveLearningQuizService;

    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.assessment.CourseAssessmentFocusAreaService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CourseAssessmentFocusAreaService.class);


    @Override
    public CourseAssessmentFocusArea findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding CourseAssessmentFocusArea with id: {}", id);
        return iCourseAssessmentFocusAreaRepository.findOne(id);
    }

    @Override
    public List<CourseAssessmentFocusArea> findCourseAssessmentFocusAreas(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "id cannot be null");
        LOGGER.debug("Finding CourseAssessmentFocusArea for ELearningCourse: {}", eLearningCourse.getCourseName());
        return iCourseAssessmentFocusAreaRepository.findAllByELearningCourse(eLearningCourse);
    }

    @Transactional
    @Override
    public void save(CourseAssessmentFocusArea courseAssessmentFocusArea) {
        Assert.notNull(courseAssessmentFocusArea, "courseAssessmentFocusArea cannot be null");
        LOGGER.debug("Saving CourseAssessmentFocusArea: {}", courseAssessmentFocusArea);
        iCourseAssessmentFocusAreaRepository.save(courseAssessmentFocusArea);
    }

    @Transactional
    @Override
    public void delete(CourseAssessmentFocusArea courseAssessmentFocusArea) {
        Assert.notNull(courseAssessmentFocusArea, "courseAssessmentFocusArea cannot be null");
        LOGGER.debug("Deleting CourseAssessmentFocusArea: {}", courseAssessmentFocusArea);
        iCourseAssessmentFocusAreaRepository.delete(courseAssessmentFocusArea);
    }

    @Transactional
    @Override
    public void makeCourseAssessmentFocusArea(CurriculumProficiencyAssessment curriculumProficiencyAssessment, ELearningCourse eLearningCourse) {
        Assert.notNull(curriculumProficiencyAssessment, "curriculumProficiencyAssessment cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.debug("Building new CourseAssessmentFocusArea with curriculumProficiencyAssessment: {} eLearningCourse: {}", curriculumProficiencyAssessment, eLearningCourse);

        CourseAssessmentFocusArea courseAssessmentFocusArea = CourseAssessmentFocusArea.builder()
                .eLearningCourse(eLearningCourse)
                .curriculumProficiencyAssessment(curriculumProficiencyAssessment)
                .entryDate(DateTime.now())
                .build();
        iCourseAssessmentFocusAreaRepository.save(courseAssessmentFocusArea);
    }

    @Transactional
    @Override
    public void modifyCourseAssessmentFocusAreaWithQuiz(CourseAssessmentFocusArea courseAssessmentFocusArea, Long adaptiveLearningQuizID) {
        Assert.notNull(courseAssessmentFocusArea, "courseAssessmentFocusArea cannot be null");
        Assert.notNull(adaptiveLearningQuizID, "adaptiveLearningQuizID cannot be null");

        LOGGER.debug("Modifying CourseAssessmentFocusArea with Quiz ID: {}", adaptiveLearningQuizID);
        AdaptiveLearningQuiz adaptiveLearningQuiz = iAdaptiveLearningQuizService.findByID(adaptiveLearningQuizID);
        modifyCourseAssessmentFocusAreaWithQuiz(courseAssessmentFocusArea, adaptiveLearningQuiz);
    }

    @Transactional
    @Override
    public void modifyCourseAssessmentFocusAreaWithQuiz(CourseAssessmentFocusArea courseAssessmentFocusArea, AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(courseAssessmentFocusArea, "courseAssessmentFocusArea cannot be null");
        Assert.notNull(courseAssessmentFocusArea, "courseAssessmentFocusArea cannot be null");
        Assert.notNull(courseAssessmentFocusArea.getId(), "courseAssessmentFocusArea does not have a persisted ID");
        Assert.notNull(adaptiveLearningQuiz.getId(), "adaptiveLearningQuiz does not have a persisted ID");

        LOGGER.debug("Modifying courseAssessmentFocusArea: {} adding adaptiveLearningQuiz: {}", courseAssessmentFocusArea, adaptiveLearningQuiz);

        courseAssessmentFocusArea.setAdaptiveLearningQuiz(adaptiveLearningQuiz);
        courseAssessmentFocusArea.setLastModifyDate(DateTime.now());
        iCourseAssessmentFocusAreaRepository.save(courseAssessmentFocusArea);
    }


}
