package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.CurriculumCompletionThresholdE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm.IMultiplexAdaptiveProficiencyAlgorithm;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.IStudentCurriculumProgressService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class GlobalAdaptiveLearningServiceTest {



    @Mock
    private IMultiplexAdaptiveProficiencyAlgorithm iMultiplexAdaptiveProficiencyAlgorithm;

    @Mock
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    @Mock
    private IStudentCurriculumProgressService iStudentCurriculumProgressService;

    @Mock
    private ListeningExecutorService listeningExecutorService;

    @InjectMocks
    private GlobalAdaptiveLearningService globalAdaptiveLearningService;



    @Test
    public void testHasMetOrCrossedCurriculumCompletionThresholdE() {
        boolean hasMetOrCrossedCompletionTriggerThreshold = globalAdaptiveLearningService.hasMetOrCrossedCurriculumCompletionThresholdE(10, CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT);
        Assert.assertFalse(hasMetOrCrossedCompletionTriggerThreshold);

        hasMetOrCrossedCompletionTriggerThreshold = globalAdaptiveLearningService.hasMetOrCrossedCurriculumCompletionThresholdE(15, CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT);
        Assert.assertFalse(hasMetOrCrossedCompletionTriggerThreshold);

        hasMetOrCrossedCompletionTriggerThreshold = globalAdaptiveLearningService.hasMetOrCrossedCurriculumCompletionThresholdE(20, CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT);
        Assert.assertFalse(hasMetOrCrossedCompletionTriggerThreshold);

        hasMetOrCrossedCompletionTriggerThreshold = globalAdaptiveLearningService.hasMetOrCrossedCurriculumCompletionThresholdE(24, CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT);
        Assert.assertFalse(hasMetOrCrossedCompletionTriggerThreshold);

        hasMetOrCrossedCompletionTriggerThreshold = globalAdaptiveLearningService.hasMetOrCrossedCurriculumCompletionThresholdE(25, CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT);
        Assert.assertTrue(hasMetOrCrossedCompletionTriggerThreshold);

        hasMetOrCrossedCompletionTriggerThreshold = globalAdaptiveLearningService.hasMetOrCrossedCurriculumCompletionThresholdE(28, CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT);
        Assert.assertTrue(hasMetOrCrossedCompletionTriggerThreshold);
    }

    @Test
    public void testHasStudentMetCompletionThresholdRequirementsWith0Initial() {
        // Simulate an initial AdaptiveProficiencyRanking.  Represents the first time student signed up with 0% completion
        AdaptiveProficiencyRanking currentProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .curriculumCompletionPercentage(0d)
                .build();

        // Student has only completed 10% of curriculum, they are not in the 25% completion range yet
        boolean isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(10, currentProficiencyRanking);
        Assert.assertFalse(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 20% of curriculum, they are not in the 25% completion range yet
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(20, currentProficiencyRanking);
        Assert.assertFalse(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 24% of curriculum, they are not in the 25% completion range yet
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(24, currentProficiencyRanking);
        Assert.assertFalse(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 25% of curriculum, they are in the 25% completion range
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(25, currentProficiencyRanking);
        Assert.assertTrue(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 26% of curriculum, they have crossed the 25% completion range
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(26, currentProficiencyRanking);
        Assert.assertTrue(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 30% of curriculum, they have crossed the 25% completion range
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(30, currentProficiencyRanking);
        Assert.assertTrue(isStudentInRangeOfCompletionThresholdRequirements);

    }


    @Test
    public void testHasStudentMetCompletionThresholdRequirementsWith26Updated() {
        // Simulate an updated current AdaptiveProficiencyRanking.  Represents student has cross 25% completion threshold with 26% when checked.  Their
        AdaptiveProficiencyRanking currentProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .curriculumCompletionPercentage(26d)
                .build();

        // Student has only completed 28% of curriculum, they are not in the 50% completion threshold yet
        boolean isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(28, currentProficiencyRanking);
        Assert.assertFalse(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 38% of curriculum, they are not in the 50% completion threshold yet
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(38, currentProficiencyRanking);
        Assert.assertFalse(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 45% of curriculum, they are not in the 50% completion threshold yet
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(45, currentProficiencyRanking);
        Assert.assertFalse(isStudentInRangeOfCompletionThresholdRequirements);

        // Student has only completed 50% of curriculum, student has cross target 50%
        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(50, currentProficiencyRanking);
        Assert.assertTrue(isStudentInRangeOfCompletionThresholdRequirements);

        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(60, currentProficiencyRanking);
        Assert.assertTrue(isStudentInRangeOfCompletionThresholdRequirements);

        isStudentInRangeOfCompletionThresholdRequirements = globalAdaptiveLearningService.hasStudentMetCompletionThresholdRequirements(74, currentProficiencyRanking);
        Assert.assertTrue(isStudentInRangeOfCompletionThresholdRequirements);

    }

}
