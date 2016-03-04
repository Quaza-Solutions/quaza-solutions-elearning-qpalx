package com.quaza.solutions.qpalx.elearning.reporting.charting.line;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Defines QPalX Line chart row and column properties
 *
 * @author manyce400
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class LineChartAttribute {

    @JsonProperty(value="type")
    protected String name;


    public LineChartAttribute() {
    }

    public LineChartAttribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LineChartAttribute that = (LineChartAttribute) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .toString();
    }

    public static void main(String [] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        LineChartAttribute studentPerformanceSnapshot = new LineChartAttribute();
        studentPerformanceSnapshot.setName("string");

        try {
            String json = objectMapper.writeValueAsString(studentPerformanceSnapshot);
            System.out.println("json = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
