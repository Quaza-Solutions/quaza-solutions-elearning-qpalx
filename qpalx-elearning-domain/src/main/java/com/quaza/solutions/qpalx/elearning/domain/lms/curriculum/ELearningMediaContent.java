package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Elearning media content that can be associated with any ELearning course or Activity.
 *
 * @author manyce400
 */
@Embeddable
public class ELearningMediaContent {



    @Column(name="ELearningMediaType", nullable=true, length=255)
    private String eLearningMediaType;


    @Column(name="QPalXTutorialContentType", nullable=true, length=10)
    @Enumerated(EnumType.STRING)
    private QPalXTutorialContentTypeE qPalXTutorialContentTypeE;


    // Full path and file name of the media content
    @Column(name="ELearningMediaFile", nullable=true, length=255)
    private String eLearningMediaFile;


    public static final ELearningMediaContent NOT_SUPPORTED_MEDIA_CONTENT = new ELearningMediaContent(null, null);

    public ELearningMediaContent() {
    }

    public ELearningMediaContent(String eLearningMediaType, String eLearningMediaFile) {
        this.eLearningMediaType = eLearningMediaType;
        this.eLearningMediaFile = eLearningMediaFile;
    }


    public String getELearningMediaType() {
        return eLearningMediaType;
    }

    public void setELearningMediaType(String eLearningMediaType) {
        this.eLearningMediaType = eLearningMediaType;
    }

    public QPalXTutorialContentTypeE getQPalXTutorialContentTypeE() {
        return qPalXTutorialContentTypeE;
    }

    public void setQPalXTutorialContentTypeE(QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
        this.qPalXTutorialContentTypeE = qPalXTutorialContentTypeE;
    }

    public String getELearningMediaFile() {
        return eLearningMediaFile;
    }

    public void setELearningMediaFile(String eLearningMediaFile) {
        this.eLearningMediaFile = eLearningMediaFile;
    }

    public boolean isMediaTypeSupported() {
        MediaContentTypeE[] mediaContentTypeEs = MediaContentTypeE.values();

        for (MediaContentTypeE mType : mediaContentTypeEs) {
            if(mType.toString().equals(eLearningMediaType)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ELearningMediaContent that = (ELearningMediaContent) o;

        return new EqualsBuilder()
                .append(eLearningMediaType, that.eLearningMediaType)
                .append(qPalXTutorialContentTypeE, that.qPalXTutorialContentTypeE)
                .append(eLearningMediaFile, that.eLearningMediaFile)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(eLearningMediaType)
                .append(qPalXTutorialContentTypeE)
                .append(eLearningMediaFile)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("eLearningMediaType", eLearningMediaType)
                .append("qPalXTutorialContentTypeE", qPalXTutorialContentTypeE)
                .append("eLearningMediaFile", eLearningMediaFile)
                .toString();
    }

    public static final class Builder {

        private final ELearningMediaContent eLearningMediaContent = new ELearningMediaContent();

        public Builder eLearningMediaType(String eLearningMediaType) {
            eLearningMediaContent.eLearningMediaType = eLearningMediaType;
            return this;
        }

        public Builder qPalXTutorialContentTypeE(QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
            eLearningMediaContent.qPalXTutorialContentTypeE = qPalXTutorialContentTypeE;
            return this;
        }

        public Builder eLearningMediaFile(String eLearningMediaFile) {
            eLearningMediaContent.eLearningMediaFile = eLearningMediaFile;
            return this;
        }

        public ELearningMediaContent build() {
            return eLearningMediaContent;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
