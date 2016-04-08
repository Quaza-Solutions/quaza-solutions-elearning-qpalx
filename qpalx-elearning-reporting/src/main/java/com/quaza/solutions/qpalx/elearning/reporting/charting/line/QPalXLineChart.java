package com.quaza.solutions.qpalx.elearning.reporting.charting.line;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Models a QPalX line chart based off Google Chart API.  Objective is to convert to JSON for easily charting on
 * the QPalX application.
 *
 * @author manyce400
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QPalXLineChart {



    @JsonProperty(value="cols")
    private final LineChartAttribute [] lineChartAttributes = new LineChartAttribute[] {
            new LineChartColAttribute(), new LineChartRowAttribute()
    };

    @JsonProperty(value="row")
    private List<LineChartDataItem> lineChartDataItems;



    public QPalXLineChart() {

    }

    public QPalXLineChart(List<LineChartDataItem> lineChartDataItems) {
        this.lineChartDataItems = lineChartDataItems;
    }

    public List<LineChartDataItem> getLineChartDataItems() {
        return lineChartDataItems;
    }

    public void setLineChartDataItems(List<LineChartDataItem> lineChartDataItems) {
        this.lineChartDataItems = lineChartDataItems;
    }

    public LineChartAttribute[] getLineChartAttributes() {
        return lineChartAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("lineChartDataItems", lineChartDataItems)
                .append("lineChartAttributes", lineChartAttributes)
                .toString();
    }

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        QPalXLineChart qPalXLineChart = new QPalXLineChart();

        // build line chart data items
        LineChartDataItem lineChartDataItem1 = LineChartDataItem.builder()
                .lineDataElement("20-01-13", 22d)
                .build();

        LineChartDataItem lineChartDataItem2 = LineChartDataItem.builder()
                .lineDataElement("20-01-13", 24d)
                .build();

        List<LineChartDataItem> lineChartDataItems =  ImmutableList.of(lineChartDataItem1, lineChartDataItem2);
        qPalXLineChart.setLineChartDataItems(lineChartDataItems);

        try {
            String json = objectMapper.writeValueAsString(qPalXLineChart);
            System.out.println("json = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
