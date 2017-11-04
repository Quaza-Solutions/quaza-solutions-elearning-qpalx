package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm;

import com.google.common.collect.ImmutableList;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Domain object that captures all the results of a Proficiency Algorithm execution run.
 *
 * @author manyce400
 */
public class ProficiencyAlgorithmExecutionInfo {



    private String algorithm;

    private List<ProficiencyAlgorithmResult>  positiveProgressItems = new LinkedList<>();

    private List<ProficiencyAlgorithmResult>  negativeProgressItems = new LinkedList<>();

    public static final String CLASS_ATTRIBUTE = "ProficiencyAlgorithmExecutionInfo";



    public ProficiencyAlgorithmExecutionInfo(String algorithm) {
        Assert.notNull(algorithm, "algorithm cannot be null");
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public List<ProficiencyAlgorithmResult> getPositiveProgressItems() {
        return ImmutableList.copyOf(positiveProgressItems);
    }

    public void addPositiveProgressItem(ProficiencyAlgorithmResult proficiencyAlgorithmResult) {
        Assert.notNull(proficiencyAlgorithmResult, "proficiencyAlgorithmResult");
        positiveProgressItems.add(proficiencyAlgorithmResult);
    }

    public List<ProficiencyAlgorithmResult> getNegativeProgressItems() {
        return ImmutableList.copyOf(negativeProgressItems);
    }

    public void addNegativeProgressItem(ProficiencyAlgorithmResult proficiencyAlgorithmResult) {
        Assert.notNull(proficiencyAlgorithmResult, "proficiencyAlgorithmResult");
        negativeProgressItems.add(proficiencyAlgorithmResult);
    }

    public static ProficiencyAlgorithmExecutionInfo newInstance(String algorithm) {
        return new ProficiencyAlgorithmExecutionInfo(algorithm);
    }

}
