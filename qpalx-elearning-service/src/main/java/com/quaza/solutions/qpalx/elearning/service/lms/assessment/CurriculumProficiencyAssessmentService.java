package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CourseAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.ICourseAssessmentFocusAreaVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.repository.ICurriculumProficiencyAssessmentRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.DefaultELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author manyce400
 */
@Service(CurriculumProficiencyAssessmentService.SPRING_BEAN)
public class CurriculumProficiencyAssessmentService implements ICurriculumProficiencyAssessmentService {



    @Autowired
    private ICurriculumProficiencyAssessmentRepository iCurriculumProficiencyAssessmentRepository;

    @Autowired
    @Qualifier(DefaultELearningCourseService.BEAN_NAME)
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier(AdaptiveLearningQuizService.BEAN_NAME)
    private IAdaptiveLearningQuizService iAdaptiveLearningQuizService;

    @Autowired
    @Qualifier(CourseAssessmentFocusAreaService.BEAN_NAME)
    private ICourseAssessmentFocusAreaService iCourseAssessmentFocusAreaService;

    public static final String SPRING_BEAN = "com.quaza.solutions.qpalx.elearning.service.lms.assessment.CurriculumProficiencyAssessmentService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CurriculumProficiencyAssessmentService.class);


    @Override
    public CurriculumProficiencyAssessment findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding CurriculumProficiencyAssessment with id: {}", id);
        return iCurriculumProficiencyAssessmentRepository.findOne(id);
    }

    @Override
    public CurriculumProficiencyAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        LOGGER.info("Finding CurriculumProficiencyAssessment for eLearningCurriculum: {}", eLearningCurriculum);
        return iCurriculumProficiencyAssessmentRepository.findByELearningCurriculum(eLearningCurriculum);
    }

    @Transactional
    @Override
    public CurriculumProficiencyAssessment makeCurriculumProficiencyRankingAssessment(ELearningCurriculum eLearningCurriculum, Set<ICourseAssessmentFocusAreaVO> iCourseAssessmentFocusAreaVOS) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(iCourseAssessmentFocusAreaVOS, "iCourseAssessmentFocusAreaVOS cannot be null");

        LOGGER.info("Building and saving new CurriculumProficiencyAssessmentService with assessmentTitle: {} in eLearningCurriculum: {}", eLearningCurriculum);

        CurriculumProficiencyAssessment curriculumProficiencyAssessment = CurriculumProficiencyAssessment.builder()
                .eLearningCurriculum(eLearningCurriculum)
                .entryDate(new DateTime())
                .build();

        save(curriculumProficiencyAssessment);

        for(ICourseAssessmentFocusAreaVO iCourseAssessmentFocusAreaVO : iCourseAssessmentFocusAreaVOS) {
            // Lookup the ELearningCourse
            ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(iCourseAssessmentFocusAreaVO.getELearningCourseID());

            CourseAssessmentFocusArea courseAssessmentFocusArea = CourseAssessmentFocusArea.builder()
                    .entryDate(DateTime.now())
                    .curriculumProficiencyAssessment(curriculumProficiencyAssessment)
                    .eLearningCourse(eLearningCourse)
                    .build();
            iCourseAssessmentFocusAreaService.save(courseAssessmentFocusArea);

            curriculumProficiencyAssessment.addCourseAssessmentFocusArea(courseAssessmentFocusArea);
        }

        // Save again to update relations
        save(curriculumProficiencyAssessment);
        return curriculumProficiencyAssessment;
    }

    @Override
    public void save(CurriculumProficiencyAssessment curriculumProficiencyAssessment) {
        Assert.notNull(curriculumProficiencyAssessment, "curriculumProficiencyAssessment cannot be null");
        LOGGER.info("Saving CurriculumProficiencyAssessment: {}", curriculumProficiencyAssessment);
        iCurriculumProficiencyAssessmentRepository.save(curriculumProficiencyAssessment);
    }

}
