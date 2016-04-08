package com.quaza.solutions.qpalx.elearning.reporting.charting.line;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 *
 *@author manyce400
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineChartProperties {


    @JsonProperty(value="cols")
    private LineChartAttribute [] lineChartAttributes = new LineChartAttribute[2];

    public LineChartProperties() {
        lineChartAttributes[0] = new LineChartColAttribute();
        lineChartAttributes[1] = new LineChartRowAttribute();
    }

    public LineChartAttribute[] getLineChartAttributes() {
        return lineChartAttributes;
    }

    public void setLineChartAttributes(LineChartAttribute[] lineChartAttributes) {
        this.lineChartAttributes = lineChartAttributes;
    }
}
