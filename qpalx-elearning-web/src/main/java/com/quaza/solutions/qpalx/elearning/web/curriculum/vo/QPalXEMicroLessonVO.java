package com.quaza.solutions.qpalx.elearning.web.curriculum.vo;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.AbstractILMSMediaContentVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

/**
 * @author manyce400
 */
public class QPalXEMicroLessonVO extends AbstractILMSMediaContentVO implements IQPalXEMicroLessonVO {



    public String microLessonName;

    public String microLessonDescription;

    private Long qPalXELessonID;


    @Override
    public String getMicroLessonName() {
        return microLessonName;
    }

    public void setMicroLessonName(String microLessonName) {
        this.microLessonName = microLessonName;
    }

    @Override
    public String getMicroLessonDescription() {
        return microLessonDescription;
    }

    public void setMicroLessonDescription(String microLessonDescription) {
        this.microLessonDescription = microLessonDescription;
    }

    public Long getQPalXELessonID() {
        return qPalXELessonID;
    }

    public void setQPalXELessonID(Long qPalXELessonID) {
        this.qPalXELessonID = qPalXELessonID;
    }

    @Override
    public Set<MediaContentTypeE> getMediaContentTypes() {
        return ImmutableSet.of(MediaContentTypeE.mp4);
    }

    @Override
    public Set<QPalXTutorialContentTypeE> getQPalXTutorialContentTypes() {
        return ImmutableSet.of(QPalXTutorialContentTypeE.Video);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("microLessonName", microLessonName)
                .append("microLessonDescription", microLessonDescription)
                .append("qPalXELessonID", qPalXELessonID)
                .append("eLearningMediaContent", eLearningMediaContent)
                .toString();
    }
}
