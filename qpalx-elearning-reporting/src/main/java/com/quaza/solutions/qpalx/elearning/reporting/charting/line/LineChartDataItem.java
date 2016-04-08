package com.quaza.solutions.qpalx.elearning.reporting.charting.line;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author manyce400
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineChartDataItem {



    @JsonProperty(value="c")
    private List<LineDataElement> lineDataElements = new ArrayList<>();

    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private final LineChartDataItem lineChartDataItem = new LineChartDataItem();

        public Builder lineDataElement(String columnValue, Double rowValue) {
            lineChartDataItem.lineDataElements.add(new LineColumnElement(columnValue));
            lineChartDataItem.lineDataElements.add(new LineRowElement(rowValue));
            return this;
        }

        public LineChartDataItem build() {
            return lineChartDataItem;
        }

    }


    public abstract static class LineDataElement {

    }

    private static class LineColumnElement extends LineDataElement {

        @JsonProperty(value="v")
        private String lineColumnElement;

        public LineColumnElement(String lineColumnElement) {
            this.lineColumnElement = lineColumnElement;
        }

        public String getLineColumnElement() {
            return lineColumnElement;
        }

        public void setLineColumnElement(String lineColumnElement) {
            this.lineColumnElement = lineColumnElement;
        }
    }

    private static class LineRowElement extends LineDataElement {

        @JsonProperty(value="v")
        private Number lineRowElement;

        public LineRowElement(Number lineRowElement) {
            this.lineRowElement = lineRowElement;
        }

        public Number getLineRowElement() {
            return lineRowElement;
        }

        public void setLineRowElement(Number lineRowElement) {
            this.lineRowElement = lineRowElement;
        }
    }


}
