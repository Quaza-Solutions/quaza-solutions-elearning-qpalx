package com.quaza.solutions.qpalx.elearning.domain.subscription;

import com.quaza.solutions.qpalx.elearning.domain.partner.PlatformPartnerTypeE;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author manyce400
 */
public class SubscriptionCodeBatchSessionStatistic {


    private Long subscriptionCodeBatchSessionID;

    private String platformPartnerName;

    private PlatformPartnerTypeE platformPartnerTypeE;

    private String subscriptionCodeBatchSessionUID;

    private DateTime batchGenerationDate;

    private DateTime batchRedemptionCompletionDate;

    private Integer totalNumberOfRedeemedPrepaidCodes;

    private Integer totalNumberOfOpenPrePaidCodes;

    private Integer totalNumberOfPrePaidCodes;

    private DateTimeFormatter dateTimeFormatterDisplay;

    public SubscriptionCodeBatchSessionStatistic() {

    }


    public Long getSubscriptionCodeBatchSessionID() {
        return subscriptionCodeBatchSessionID;
    }

    public void setSubscriptionCodeBatchSessionID(Long subscriptionCodeBatchSessionID) {
        this.subscriptionCodeBatchSessionID = subscriptionCodeBatchSessionID;
    }

    public String getPlatformPartnerName() {
        return platformPartnerName;
    }

    public void setPlatformPartnerName(String platformPartnerName) {
        this.platformPartnerName = platformPartnerName;
    }

    public PlatformPartnerTypeE getPlatformPartnerTypeE() {
        return platformPartnerTypeE;
    }

    public void setPlatformPartnerTypeE(PlatformPartnerTypeE platformPartnerTypeE) {
        this.platformPartnerTypeE = platformPartnerTypeE;
    }

    public String getSubscriptionCodeBatchSessionUID() {
        return subscriptionCodeBatchSessionUID;
    }

    public void setSubscriptionCodeBatchSessionUID(String subscriptionCodeBatchSessionUID) {
        this.subscriptionCodeBatchSessionUID = subscriptionCodeBatchSessionUID;
    }

    public DateTime getBatchGenerationDate() {
        return batchGenerationDate;
    }

    public String getBatchGenerationDateAsString() {
        return batchGenerationDate.toString(dateTimeFormatterDisplay);
    }

    public void setBatchGenerationDate(DateTime batchGenerationDate) {
        this.batchGenerationDate = batchGenerationDate;
    }

    public DateTime getBatchRedemptionCompletionDate() {
        return batchRedemptionCompletionDate;
    }

    public String getBatchRedemptionCompletionDateAsString() {
        if (batchRedemptionCompletionDate != null) {
            return batchRedemptionCompletionDate.toString(dateTimeFormatterDisplay);
        }

        return "";
    }

    public void setBatchRedemptionCompletionDate(DateTime batchRedemptionCompletionDate) {
        this.batchRedemptionCompletionDate = batchRedemptionCompletionDate;
    }

    public Integer getTotalNumberOfRedeemedPrepaidCodes() {
        return totalNumberOfRedeemedPrepaidCodes;
    }

    public void setTotalNumberOfRedeemedPrepaidCodes(Integer totalNumberOfRedeemedPrepaidCodes) {
        this.totalNumberOfRedeemedPrepaidCodes = totalNumberOfRedeemedPrepaidCodes;
    }

    public Integer getTotalNumberOfOpenPrePaidCodes() {
        return totalNumberOfOpenPrePaidCodes;
    }

    public void setTotalNumberOfOpenPrePaidCodes(Integer totalNumberOfOpenPrePaidCodes) {
        this.totalNumberOfOpenPrePaidCodes = totalNumberOfOpenPrePaidCodes;
    }

    public Integer getTotalNumberOfPrePaidCodes() {
        return totalNumberOfPrePaidCodes;
    }

    public void setTotalNumberOfPrePaidCodes(Integer totalNumberOfPrePaidCodes) {
        this.totalNumberOfPrePaidCodes = totalNumberOfPrePaidCodes;
    }

    public void setDateTimeFormatterDisplay(DateTimeFormatter dateTimeFormatterDisplay) {
        this.dateTimeFormatterDisplay = dateTimeFormatterDisplay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("subscriptionCodeBatchSessionID", subscriptionCodeBatchSessionID)
                .append("platformPartnerName", platformPartnerName)
                .append("platformPartnerTypeE", platformPartnerTypeE)
                .append("subscriptionCodeBatchSessionUID", subscriptionCodeBatchSessionUID)
                .append("batchGenerationDate", batchGenerationDate)
                .append("batchRedemptionCompletionDate", batchRedemptionCompletionDate)
                .append("totalNumberOfRedeemedPrepaidCodes", totalNumberOfRedeemedPrepaidCodes)
                .append("totalNumberOfOpenPrePaidCodes", totalNumberOfOpenPrePaidCodes)
                .append("totalNumberOfPrePaidCodes", totalNumberOfPrePaidCodes)
                .append("dateTimeFormatterDisplay", dateTimeFormatterDisplay)
                .toString();
    }

    public static SubscriptionCodeBatchSessionStatisticRowMapper newRowMapper(DateTimeFormatter dateTimeFormatterDB, DateTimeFormatter dateTimeFormatterDisplay) {
        return new SubscriptionCodeBatchSessionStatisticRowMapper(dateTimeFormatterDB, dateTimeFormatterDisplay);
    }

    private static class SubscriptionCodeBatchSessionStatisticRowMapper implements RowMapper<SubscriptionCodeBatchSessionStatistic> {

        private final DateTimeFormatter dateTimeFormatterDB;

        private final DateTimeFormatter dateTimeFormatterDisplay;

        public SubscriptionCodeBatchSessionStatisticRowMapper(DateTimeFormatter dateTimeFormatterDB, DateTimeFormatter dateTimeFormatterDisplay) {
            this.dateTimeFormatterDB = dateTimeFormatterDB;
            this.dateTimeFormatterDisplay = dateTimeFormatterDisplay;
        }

        @Override
        public SubscriptionCodeBatchSessionStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
            SubscriptionCodeBatchSessionStatistic subscriptionCodeBatchSessionStatistic = new SubscriptionCodeBatchSessionStatistic();
            subscriptionCodeBatchSessionStatistic.setDateTimeFormatterDisplay(dateTimeFormatterDisplay);

            Long subscriptionCodeBatchSessionID = resultSet.getLong("ID");
            String platformPartnerName = resultSet.getString("PlatformPartnerName");
            String platformPartnerTypeE = resultSet.getString("PlatformPartnerType");
            String subscriptionCodeBatchSessionUID = resultSet.getString("SubscriptionCodeBatchSessionUID");
            String batchGenerationDate = getCorrectDBDateTime(resultSet.getString("BatchGenerationDate"));
            String batchRedemptionCompletionDate = getCorrectDBDateTime(resultSet.getString("BatchRedemptionCompletionDate"));
            Integer totalNumberOfRedeemedPrepaidCodes = resultSet.getInt("TotalNumberOfRedeemedPrepaidCodes");
            Integer totalNumberOfOpenPrePaidCodes = resultSet.getInt("TotalNumberOfOpenPrePaidCodes");
            Integer totalNumberOfPrePaidCodes = resultSet.getInt("TotalNumberofPrePaidCodes");

            subscriptionCodeBatchSessionStatistic.setSubscriptionCodeBatchSessionID(subscriptionCodeBatchSessionID);
            subscriptionCodeBatchSessionStatistic.setPlatformPartnerName(platformPartnerName);
            subscriptionCodeBatchSessionStatistic.setPlatformPartnerTypeE(PlatformPartnerTypeE.valueOf(platformPartnerTypeE));
            subscriptionCodeBatchSessionStatistic.setSubscriptionCodeBatchSessionUID(subscriptionCodeBatchSessionUID);

            if (batchGenerationDate != null) {
                subscriptionCodeBatchSessionStatistic.setBatchGenerationDate(DateTime.parse(batchGenerationDate, dateTimeFormatterDB));
            }

            if (batchRedemptionCompletionDate != null) {
                subscriptionCodeBatchSessionStatistic.setBatchRedemptionCompletionDate(DateTime.parse(batchRedemptionCompletionDate, dateTimeFormatterDB));
            }

            subscriptionCodeBatchSessionStatistic.setTotalNumberOfRedeemedPrepaidCodes(totalNumberOfRedeemedPrepaidCodes);
            subscriptionCodeBatchSessionStatistic.setTotalNumberOfOpenPrePaidCodes(totalNumberOfOpenPrePaidCodes);
            subscriptionCodeBatchSessionStatistic.setTotalNumberOfPrePaidCodes(totalNumberOfPrePaidCodes);

            return subscriptionCodeBatchSessionStatistic;
        }

        public String getCorrectDBDateTime(String dateTime) {
            if (dateTime != null) {
                int idx = dateTime.indexOf(".0");

                if(idx > 0) {
                    return dateTime.substring(0, idx);
                }

                return dateTime;
            }

            return null;
        }

    }

}
