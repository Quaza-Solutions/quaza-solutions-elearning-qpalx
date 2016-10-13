package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents all QPalX E-Learning activities that a score can be generated/created out of.
 *
 * @author manyce400
 */
@Embeddable
public class EmbedabbleScorableELearningActivity {



    @Column(name="ScorableActivityID", nullable=false)
    private Long scorableActivityID;

    @Column(name="QPalXTutorialContentType", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private QPalXTutorialContentTypeE qPalXTutorialContentTypeE;

    public EmbedabbleScorableELearningActivity() {

    }

    public Long getScorableActivityID() {
        return scorableActivityID;
    }

    public void setScorableActivityID(Long scorableActivityID) {
        this.scorableActivityID = scorableActivityID;
    }

    public QPalXTutorialContentTypeE getQPalXTutorialContentTypeE() {
        return qPalXTutorialContentTypeE;
    }

    public void setQPalXTutorialContentTypeE(QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
        this.qPalXTutorialContentTypeE = qPalXTutorialContentTypeE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmbedabbleScorableELearningActivity that = (EmbedabbleScorableELearningActivity) o;

        return new EqualsBuilder()
                .append(scorableActivityID, that.scorableActivityID)
                .append(qPalXTutorialContentTypeE, that.qPalXTutorialContentTypeE)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(scorableActivityID)
                .append(qPalXTutorialContentTypeE)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("scorableActivityID", scorableActivityID)
                .append("qPalXTutorialContentTypeE", qPalXTutorialContentTypeE)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        public EmbedabbleScorableELearningActivity embedabbleScorableELearningActivity = new EmbedabbleScorableELearningActivity();

        public Builder scorableActivityID(Long scorableActivityID) {
            embedabbleScorableELearningActivity.scorableActivityID = scorableActivityID;
            return this;
        }

        public Builder qPalXTutorialContentTypeE(QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
            embedabbleScorableELearningActivity.qPalXTutorialContentTypeE = qPalXTutorialContentTypeE;
            return this;
        }

        public EmbedabbleScorableELearningActivity build() {
            return embedabbleScorableELearningActivity;
        }

    }
}
