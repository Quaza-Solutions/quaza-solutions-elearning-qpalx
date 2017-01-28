package com.quaza.solutions.qpalx.elearning.domain.subscription;

import com.quaza.solutions.qpalx.elearning.domain.api.rowmapper.AbstractDomainRowMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author manyce400
 */
public class PrepaidSubscriptionStatistic {



    private String uniqueID;

    private String subscriptionName;

    private Double subscriptionCost;

    private String municipalityName;

    private boolean alreadyUsed;

    private DateTime dateCreated;

    private DateTime dateRedeemed;

    private String redemptionUserEmail;


    public PrepaidSubscriptionStatistic() {

    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public Double getSubscriptionCost() {
        return subscriptionCost;
    }

    public void setSubscriptionCost(Double subscriptionCost) {
        this.subscriptionCost = subscriptionCost;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }

    public void setMunicipalityName(String municipalityName) {
        this.municipalityName = municipalityName;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(boolean alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DateTime getDateRedeemed() {
        return dateRedeemed;
    }

    public void setDateRedeemed(DateTime dateRedeemed) {
        this.dateRedeemed = dateRedeemed;
    }

    public String getRedemptionUserEmail() {
        return redemptionUserEmail;
    }

    public void setRedemptionUserEmail(String redemptionUserEmail) {
        this.redemptionUserEmail = redemptionUserEmail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uniqueID", uniqueID)
                .append("subscriptionName", subscriptionName)
                .append("subscriptionCost", subscriptionCost)
                .append("municipalityName", municipalityName)
                .append("alreadyUsed", alreadyUsed)
                .append("dateCreated", dateCreated)
                .append("dateRedeemed", dateRedeemed)
                .append("redemptionUserEmail", redemptionUserEmail)
                .toString();
    }

    public static PrepaidSubscriptionStatisticRowMapper newRowMapper(DateTimeFormatter dateTimeFormatterDB, DateTimeFormatter dateTimeFormatterDisplay) {
        return new PrepaidSubscriptionStatisticRowMapper(dateTimeFormatterDB, dateTimeFormatterDisplay);
    }

    private static class PrepaidSubscriptionStatisticRowMapper extends AbstractDomainRowMapper<PrepaidSubscriptionStatistic> {

        public PrepaidSubscriptionStatisticRowMapper(DateTimeFormatter dateTimeFormatterDB, DateTimeFormatter dateTimeFormatterDisplay) {
            super(dateTimeFormatterDB, dateTimeFormatterDisplay);
        }

        @Override
        public PrepaidSubscriptionStatistic mapRow(ResultSet resultSet, int i) throws SQLException {
            PrepaidSubscriptionStatistic prepaidSubscriptionStatistic = new PrepaidSubscriptionStatistic();

            String uniqueID = resultSet.getString("UniqueID");
            String subscriptionName = resultSet.getString("SubscriptionName");
            Double subscriptionCost = resultSet.getDouble("SubscriptionCost");
            String municipalityName = resultSet.getString("MunicipalityName");
            boolean alreadyUsed = resultSet.getBoolean("AlreadyUsed");
            Optional<DateTime> dateCreated = getResultSetDateTime(resultSet, "DateCreated");
            Optional<DateTime> dateRedeemed = getResultSetDateTime(resultSet, "RedemptionDate");
            String redemptionUserEmail = resultSet.getString("RedemptionUserEmail");

            prepaidSubscriptionStatistic.setUniqueID(uniqueID);
            prepaidSubscriptionStatistic.setSubscriptionName(subscriptionName);
            prepaidSubscriptionStatistic.setSubscriptionCost(subscriptionCost);
            prepaidSubscriptionStatistic.setMunicipalityName(municipalityName);
            prepaidSubscriptionStatistic.setAlreadyUsed(alreadyUsed);
            prepaidSubscriptionStatistic.setDateCreated(dateCreated.get());

            if (dateRedeemed.isPresent()) {
                prepaidSubscriptionStatistic.setDateRedeemed(dateRedeemed.get());
            }

            prepaidSubscriptionStatistic.setRedemptionUserEmail(redemptionUserEmail);
            return prepaidSubscriptionStatistic;
        }
    }
}
