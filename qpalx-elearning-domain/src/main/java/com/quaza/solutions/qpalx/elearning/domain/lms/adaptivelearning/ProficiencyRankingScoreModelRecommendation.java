package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Each QPalX Score model implementation will be abe to analyze a Students adaptive proficiency rankings and score and make recommendations
 * on how each student can improve
 *
 * @author manyce400
 */
//@Entity
//@Table(name="ProficiencyRankingScoreModelRecommendation")
public class ProficiencyRankingScoreModelRecommendation {


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="ID", nullable=false)
    private Long id;

//    @Column(name="Recommendation", nullable=false, length=256)
    private String recommendation;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FactorAffectingProficiencyRankingID", nullable = false)
    private FactorAffectingProficiencyRanking factorAffectingProficiencyRanking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public FactorAffectingProficiencyRanking getFactorAffectingProficiencyRanking() {
        return factorAffectingProficiencyRanking;
    }

    public void setFactorAffectingProficiencyRanking(FactorAffectingProficiencyRanking factorAffectingProficiencyRanking) {
        this.factorAffectingProficiencyRanking = factorAffectingProficiencyRanking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProficiencyRankingScoreModelRecommendation that = (ProficiencyRankingScoreModelRecommendation) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(recommendation, that.recommendation)
                .append(factorAffectingProficiencyRanking, that.factorAffectingProficiencyRanking)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(recommendation)
                .append(factorAffectingProficiencyRanking)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("recommendation", recommendation)
                .append("factorAffectingProficiencyRanking", factorAffectingProficiencyRanking)
                .toString();
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = new ProficiencyRankingScoreModelRecommendation();

        public Builder recommendation(String recommendation) {
            proficiencyRankingScoreModelRecommendation.recommendation = recommendation;
            return this;
        }

        public Builder factorAffectingProficiencyRanking(FactorAffectingProficiencyRanking factorAffectingProficiencyRanking) {
            proficiencyRankingScoreModelRecommendation.factorAffectingProficiencyRanking = factorAffectingProficiencyRanking;
            return this;
        }

        public ProficiencyRankingScoreModelRecommendation build() {
            return proficiencyRankingScoreModelRecommendation;
        }

    }

}
