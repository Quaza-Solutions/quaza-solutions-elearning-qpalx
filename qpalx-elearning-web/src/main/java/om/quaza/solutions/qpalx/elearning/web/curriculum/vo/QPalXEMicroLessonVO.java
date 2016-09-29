package om.quaza.solutions.qpalx.elearning.web.curriculum.vo;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class QPalXEMicroLessonVO implements IQPalXEMicroLessonVO {



    public String microLessonName;

    public String microLessonDescription;

    private Long qPalXELessonID;

    public ELearningMediaContent eLearningMediaContent;


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

    public ELearningMediaContent getELearningMediaContent() {
        return eLearningMediaContent;
    }

    public void setELearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
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
