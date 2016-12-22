package com.quaza.solutions.qpalx.elearning.web.curriculum.vo;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
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



    private String microLessonName;

    private String microLessonDescription;

    private Long qPalXELessonID;

    protected String staticQPalXTutorialContentType;

    private ELearningMediaContent staticELearningMediaContent;


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

    public String getStaticQPalXTutorialContentType() {
        return staticQPalXTutorialContentType;
    }

    public void setStaticQPalXTutorialContentType(String staticQPalXTutorialContentType) {
        this.staticQPalXTutorialContentType = staticQPalXTutorialContentType;
    }

    @Override
    public Set<MediaContentTypeE> getMediaContentTypes() {
        return ImmutableSet.of(MediaContentTypeE.mp4, MediaContentTypeE.swf);
    }

    @Override
    public Set<QPalXTutorialContentTypeE> getQPalXTutorialContentTypes() {
        return ImmutableSet.of(QPalXTutorialContentTypeE.Video);
    }

    @Override
    public Set<QPalXTutorialContentTypeE> getStaticQPalXTutorialContentTypes() {
        return ImmutableSet.of(QPalXTutorialContentTypeE.Static);
    }

    public QPalXTutorialContentTypeE getSelectedStaticQPalXTutorialContentTypeE() {
        return QPalXTutorialContentTypeE.valueOf(staticQPalXTutorialContentType);
    }

    @Override
    public ELearningMediaContent getStaticELearningMediaContent() {
        return staticELearningMediaContent;
    }

    public void setStaticELearningMediaContent(ELearningMediaContent staticELearningMediaContent) {
        this.staticELearningMediaContent = staticELearningMediaContent;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContent() {
        return null;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("microLessonName", microLessonName)
                .append("microLessonDescription", microLessonDescription)
                .append("qPalXELessonID", qPalXELessonID)
                .append("eLearningMediaContent", eLearningMediaContent)
                .append("staticELearningMediaContent", staticELearningMediaContent)
                .toString();
    }
}
