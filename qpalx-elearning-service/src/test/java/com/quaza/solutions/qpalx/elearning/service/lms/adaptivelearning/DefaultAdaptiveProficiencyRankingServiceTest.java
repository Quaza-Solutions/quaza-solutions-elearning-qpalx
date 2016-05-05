package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IAdaptiveProficiencyRankingRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultAdaptiveProficiencyRankingServiceTest {


    @Spy
    private QPalXUser qPalXUser;

    @Spy
    private ELearningCurriculum eLearningCurriculum;

    @Mock
    private IAdaptiveProficiencyRankingRepository iAdaptiveProficiencyRankingRepository;

    @Mock
    private IELearningCurriculumService ieLearningCurriculumService;

    @InjectMocks
    private DefaultAdaptiveProficiencyRankingService adaptiveProficiencyRankingService;


    @Test
    public void testcreateStudentAdaptiveProficiencyRankingWithProficiencyRanking() {
        qPalXUser.setEmail("mjones@gmail.com");
        eLearningCurriculum.setCurriculumName("Mathematics");

        // Set this up so there will be no current student adaptive profile ranking, meaning the service should create a brand new one
        when(adaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum)).thenReturn(null);

        // Capture the StudentEnrolmentRecord to be saved
        ArgumentCaptor<AdaptiveProficiencyRanking> argumentCaptor = ArgumentCaptor.forClass(AdaptiveProficiencyRanking.class);

        // Invoke the create method and capture the AdaptiveProficiencyRanking to verify it matches our expectations
        adaptiveProficiencyRankingService.createStudentAdaptiveProficiencyRanking(qPalXUser, ProficiencyRankingTriggerTypeE.ON_DEMAND, eLearningCurriculum, ProficiencyRankingScaleE.TEN);
        verify(iAdaptiveProficiencyRankingRepository, Mockito.times(1)).save(argumentCaptor.capture());
        AdaptiveProficiencyRanking  adaptiveProficiencyRanking = argumentCaptor.getValue();
        Assert.assertNotNull(adaptiveProficiencyRanking);
        Assert.assertNotNull(adaptiveProficiencyRanking.getProficiencyRankingEffectiveDateTime());
        Assert.assertEquals(qPalXUser, adaptiveProficiencyRanking.getQpalxUser());
        Assert.assertEquals(eLearningCurriculum, adaptiveProficiencyRanking.geteLearningCurriculum());
        Assert.assertEquals(ProficiencyRankingScaleE.TEN, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertEquals(new DateTime().toLocalDate(), adaptiveProficiencyRanking.getProficiencyRankingEffectiveDateTime().toLocalDate()); // dates without anytime should match as effective date should be today.
    }


}
