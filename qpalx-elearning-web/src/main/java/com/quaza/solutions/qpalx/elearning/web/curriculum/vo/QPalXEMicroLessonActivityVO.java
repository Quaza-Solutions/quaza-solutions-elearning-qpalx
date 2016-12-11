package com.quaza.solutions.qpalx.elearning.web.curriculum.vo;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonActivityVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.MicroLessonActivityTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.AbstractILMSMediaContentVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;

import java.util.Set;

/**
 * @author manyce400
 */
public class QPalXEMicroLessonActivityVO extends AbstractILMSMediaContentVO implements IQPalXEMicroLessonActivityVO {


    public String microLessonActivityName;

    public String microLessonActivityDescription;

    private Long qPalXEMicroLessonID;

    private String microLessonActivityType;

    public ELearningMediaContent eLearningMediaContent;

    @Override
    public String getMicroLessonActivityName() {
        return microLessonActivityName;
    }

    public void setMicroLessonActivityName(String microLessonActivityName) {
        this.microLessonActivityName = microLessonActivityName;
    }

    @Override
    public String getMicroLessonActivityDescription() {
        return microLessonActivityDescription;
    }

    public void setMicroLessonActivityDescription(String microLessonActivityDescription) {
        this.microLessonActivityDescription = microLessonActivityDescription;
    }

    public Long getQPalXEMicroLessonID() {
        return qPalXEMicroLessonID;
    }

    public void setQPalXEMicroLessonID(Long qPalXEMicroLessonID) {
        this.qPalXEMicroLessonID = qPalXEMicroLessonID;
    }

    @Override
    public String getMicroLessonActivityType() {
        return microLessonActivityType;
    }

    public void setMicroLessonActivityType(String microLessonActivityType) {
        this.microLessonActivityType = microLessonActivityType;
    }

    public ELearningMediaContent getELearningMediaContent() {
        return eLearningMediaContent;
    }

    public void setELearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    @Override
    public MicroLessonActivityTypeE getMicroLessonActivityTypeE() {
        return MicroLessonActivityTypeE.getByStringValue(microLessonActivityType);
    }

    @Override
    public Set<MediaContentTypeE> getMediaContentTypes() {
        return ImmutableSet.of(MediaContentTypeE.mp4);
    }

    @Override
    public Set<QPalXTutorialContentTypeE> getQPalXTutorialContentTypes() {
        return ImmutableSet.of(QPalXTutorialContentTypeE.Video);
    }
}
