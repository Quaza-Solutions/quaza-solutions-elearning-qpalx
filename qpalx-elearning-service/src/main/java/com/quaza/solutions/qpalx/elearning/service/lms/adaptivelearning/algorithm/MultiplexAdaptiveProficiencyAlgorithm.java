package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm.ProficiencyAlgorithmExecutionInfo;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * @author manyce400
 */
@Service(MultiplexAdaptiveProficiencyAlgorithm.BEAN_NAME)
public class MultiplexAdaptiveProficiencyAlgorithm implements IMultiplexAdaptiveProficiencyAlgorithm {




    // Inject all IAdaptiveProficiencyAlgorithm implementations
    @Autowired
    private List<IAdaptiveProficiencyAlgorithm> adaptiveProficiencyAlgorithmList; //= new LinkedList<>();


    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm.MultiplexAdaptiveProficiencyAlgorithm";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MultiplexAdaptiveProficiencyAlgorithm.class);



    @Override
    public List<ProficiencyAlgorithmExecutionInfo> calculateAllAlgorithmScore(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(currentAdaptiveProficiencyRanking, "currentAdaptiveProficiencyRanking cannot be null");

        List<ProficiencyAlgorithmExecutionInfo> algorithmResults = new LinkedList<>();

        for(IAdaptiveProficiencyAlgorithm iAdaptiveProficiencyAlgorithm : adaptiveProficiencyAlgorithmList) {
            LOGGER.info("Invoking and execution iAdaptiveProficiencyAlgorithm: {} for student: {} ", iAdaptiveProficiencyAlgorithm, qPalXUser.getEmail());
            ProficiencyAlgorithmExecutionInfo proficiencyAlgorithmExecutionInfo = iAdaptiveProficiencyAlgorithm.executeAlgorithm(qPalXUser, eLearningCurriculum, currentAdaptiveProficiencyRanking);
            algorithmResults.add(proficiencyAlgorithmExecutionInfo);
        }

        return algorithmResults;
    }

    @Override
    public ProficiencyAlgorithmExecutionInfo executeAlgorithm(QPalXUser qPalXUser, ELearningCourse eLearningCourse, TutorialLevelCalendar selectedTutorialLevelCalendar) {
        return null;
    }

    @Override
    public ProficiencyAlgorithmExecutionInfo executeAlgorithm(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking) {
        return null;
    }

    @Override
    public void addIAdaptiveProficiencyAlgorithm(IAdaptiveProficiencyAlgorithm iAdaptiveProficiencyAlgorithm) {
        Assert.notNull(iAdaptiveProficiencyAlgorithm, "iAdaptiveProficiencyAlgorithm cannot be null");
        LOGGER.debug("Adding iAdaptiveProficiencyAlgorithm: {} to internal list", iAdaptiveProficiencyAlgorithm);
        adaptiveProficiencyAlgorithmList.add(iAdaptiveProficiencyAlgorithm);
    }

    @Override
    public void removeIAdaptiveProficiencyAlgorithm(IAdaptiveProficiencyAlgorithm iAdaptiveProficiencyAlgorithm) {
        Assert.notNull(iAdaptiveProficiencyAlgorithm, "iAdaptiveProficiencyAlgorithm cannot be null");
        LOGGER.debug("Removing iAdaptiveProficiencyAlgorithm: {} from internal list", iAdaptiveProficiencyAlgorithm);
        adaptiveProficiencyAlgorithmList.remove(iAdaptiveProficiencyAlgorithm);
    }

    @PostConstruct
    public void displayAllProficiencyRan() {
        System.out.println("adaptiveProficiencyAlgorithmList = " + adaptiveProficiencyAlgorithmList);
    }

}
