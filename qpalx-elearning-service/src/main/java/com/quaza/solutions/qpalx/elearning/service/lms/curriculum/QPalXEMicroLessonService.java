package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXEMicroLessonRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
public class QPalXEMicroLessonService implements IQPalXEMicroLessonService {



    private String quizzesActiveSQL;

    @Value("classpath:/sql/micro-lesson-active-items-count.sql")
    private Resource sqlResource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IQPalXEMicroLessonRepository iqPalXEMicroLessonRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXEMicroLessonService.class);


    @Override
    public QPalXEMicroLesson findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding QPalxEMicroLesson by id: {}", id);
        return iqPalXEMicroLessonRepository.findOne(id);
    }

    @Override
    public List<QPalXEMicroLesson> findQPalXEMicroLessons(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.debug("Finding all QPalxEMicroLesson's for qPalXELesson: {}", qPalXELesson);
        return iqPalXEMicroLessonRepository.findAllQPalxMicroLessons(qPalXELesson);
    }

    @Override
    public void createAndSaveQPalXEMicroLesson(IQPalXEMicroLessonVO iqPalXEMicroLessonVO) {
        Assert.notNull(iqPalXEMicroLessonVO, "iqPalXEMicroLessonVO cannot be null");
        Assert.notNull(iqPalXEMicroLessonVO.getQPalXELessonID(), "Valid non null QPalXELessonID required");
        Assert.notNull(iqPalXEMicroLessonVO.getELearningMediaContent(), "Valid non null ELearningMediaContent required");
        LOGGER.debug("Creating and saving new iqPalXEMicroLessonVO: {}", iqPalXEMicroLessonVO);

        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(iqPalXEMicroLessonVO.getQPalXELessonID());

        QPalXEMicroLesson qPalXEMicroLesson = QPalXEMicroLesson.builder()
                .microLessonName(iqPalXEMicroLessonVO.getMicroLessonName())
                .microLessonDescription(iqPalXEMicroLessonVO.getMicroLessonDescription())
                .eLearningMediaContent(iqPalXEMicroLessonVO.getELearningMediaContent())
                .qPalXELesson(qPalXELesson)
                .microLessonActive(iqPalXEMicroLessonVO.isActive())
                .entryDate(new DateTime())
                .build();

        iqPalXEMicroLessonRepository.save(qPalXEMicroLesson);
    }

    @Override
    public boolean isMicroLessonDeletable(QPalXEMicroLesson qPalXEMicroLesson) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        LOGGER.info("Checking to see if MicroLesson: {} can be deleted...", qPalXEMicroLesson.getMicroLessonName());
        Long [] queryParams = new Long[]{qPalXEMicroLesson.getId()};
        Integer activeItemsCount = jdbcTemplate.queryForObject(quizzesActiveSQL, Integer.class, queryParams);
        return activeItemsCount != null ? activeItemsCount.intValue() == 0 : false;
    }

    @Transactional
    @Override
    public void delete(QPalXEMicroLesson qPalXEMicroLesson) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        LOGGER.info("Deleting MicroLesson with ID: {}", qPalXEMicroLesson.getId());

        // Delete micro lessons progress that has been recorded for this lesson.
        String deleteProgressSQL = "Delete  From QPalXEMicroLessonProgress Where MicroLessonID=? ";
        jdbcTemplate.update(deleteProgressSQL, qPalXEMicroLesson.getId());

        // Now we can delete the MicroLesson
        iqPalXEMicroLessonRepository.delete(qPalXEMicroLesson);
    }

    @PostConstruct
    private void constructSQL() throws IOException {
        LOGGER.info("Loading sql from resource: {}", sqlResource);
        quizzesActiveSQL  = Resources.toString(sqlResource.getURL(), Charsets.UTF_8);
    }

}
