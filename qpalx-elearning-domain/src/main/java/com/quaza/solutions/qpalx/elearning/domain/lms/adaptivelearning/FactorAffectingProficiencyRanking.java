package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="FactorAffectingProficiencyRanking")
public class FactorAffectingProficiencyRanking  {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="ProficiencyRankingFactorScoreModel", nullable=false, length=50)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScoreModelE proficiencyRankingScoreModelE;

    // Calculated score of scoring model
    @Column(name="ScoreModelPercent", nullable=false)
    private Double scoreModelPercent;

    @Column(name="ScoreModelAnalysis", nullable=true, length=256)
    private String scoreModelAnalysis;

    // Link back to AdaptiveProficiencyRanking not expensive to load eager
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdaptiveProficiencyRankingID", nullable = false)
    private AdaptiveProficiencyRanking adaptiveProficiencyRanking;

    // Load all recommendation's.  This will be a small collection to load and wont be expensive
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "factorAffectingProficiencyRanking")
    private Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProficiencyRankingScoreModelE getProficiencyRankingScoreModelE() {
        return proficiencyRankingScoreModelE;
    }

    public void setProficiencyRankingScoreModelE(ProficiencyRankingScoreModelE proficiencyRankingScoreModelE) {
        this.proficiencyRankingScoreModelE = proficiencyRankingScoreModelE;
    }

    public Double getScoreModelPercent() {
        return scoreModelPercent;
    }

    public void setScoreModelPercent(Double scoreModelPercent) {
        this.scoreModelPercent = scoreModelPercent;
    }

    public String getScoreModelAnalysis() {
        return scoreModelAnalysis;
    }

    public void setScoreModelAnalysis(String scoreModelAnalysis) {
        this.scoreModelAnalysis = scoreModelAnalysis;
    }

    public AdaptiveProficiencyRanking getAdaptiveProficiencyRanking() {
        return adaptiveProficiencyRanking;
    }

    public void setAdaptiveProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        this.adaptiveProficiencyRanking = adaptiveProficiencyRanking;
    }

    public Set<ProficiencyRankingScoreModelRecommendation> getProficiencyRankingScoreModelRecommendations() {
        return ImmutableSet.copyOf(proficiencyRankingScoreModelRecommendations);
    }

    public void addScoreModelAnalyticsRecommendation(ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation) {
        Assert.notNull(proficiencyRankingScoreModelRecommendation, "proficiencyRankingScoreModelRecommendation cannot be null");
        proficiencyRankingScoreModelRecommendations.add(proficiencyRankingScoreModelRecommendation);
    }

    public void addAllScoreModelAnalyticsRecommendation(Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations) {
        Assert.notNull(proficiencyRankingScoreModelRecommendations, "proficiencyRankingScoreModelRecommendations cannot be null");
        proficiencyRankingScoreModelRecommendations.addAll(proficiencyRankingScoreModelRecommendations);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FactorAffectingProficiencyRanking that = (FactorAffectingProficiencyRanking) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(proficiencyRankingScoreModelE, that.proficiencyRankingScoreModelE)
                .append(scoreModelPercent, that.scoreModelPercent)
                .append(scoreModelAnalysis, that.scoreModelAnalysis)
                .append(adaptiveProficiencyRanking, that.adaptiveProficiencyRanking)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(proficiencyRankingScoreModelE)
                .append(scoreModelPercent)
                .append(scoreModelAnalysis)
                .append(adaptiveProficiencyRanking)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("factorScoreModelStrategy", proficiencyRankingScoreModelE)
                .append("scoreModelPercent", scoreModelPercent)
                .append("scoreModelAnalysis", scoreModelAnalysis)
                .append("adaptiveProficiencyRanking", adaptiveProficiencyRanking)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = new FactorAffectingProficiencyRanking();

        public Builder factorScoreModelStrategy(ProficiencyRankingScoreModelE proficiencyRankingScoreModelE) {
            factorAffectingProficiencyRanking.proficiencyRankingScoreModelE = proficiencyRankingScoreModelE;
            return this;
        }

        public Builder scoreModelPercent(Double scoreModelPercent) {
            factorAffectingProficiencyRanking.scoreModelPercent = scoreModelPercent;
            return this;
        }

        public Builder scoreModelAnalysis(String scoreModelAnalysis) {
            factorAffectingProficiencyRanking.scoreModelAnalysis = scoreModelAnalysis;
            return this;
        }

        public Builder adaptiveProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
            factorAffectingProficiencyRanking.adaptiveProficiencyRanking = adaptiveProficiencyRanking;
            return this;
        }

        public Builder scoreModelAnalyticsRecommendation(ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation) {
            factorAffectingProficiencyRanking.addScoreModelAnalyticsRecommendation(proficiencyRankingScoreModelRecommendation);
            return this;
        }

        public Builder scoreModelAnalyticsRecommendations(Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations) {
            factorAffectingProficiencyRanking.addAllScoreModelAnalyticsRecommendation(proficiencyRankingScoreModelRecommendations);
            return this;
        }

        public FactorAffectingProficiencyRanking build() {
            return factorAffectingProficiencyRanking;
        }

    }

}
