package com.quaza.solutions.qpalx.elearning.domain.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author manyce400
 */
public class DefaultEntityHasOrderInfo implements IEntityHasOrderInfo {



    private Integer elementOrder;

    private Long orderingDiscriminator;

    private String entityName;

    public DefaultEntityHasOrderInfo() {

    }

    public DefaultEntityHasOrderInfo(Integer elementOrder) {
        Assert.notNull(elementOrder, "elementOrder cannot be null");
        this.elementOrder = elementOrder;
    }

    @Override
    public void setElementOrder(Integer elementOrder) {
        this.elementOrder = elementOrder;
    }

    @Override
    public Integer getElementOrder() {
        return this.elementOrder;
    }

    @Override
    public Optional<Long> getOrderingDiscriminator() {
        return orderingDiscriminator == null ? Optional.empty() : Optional.of(orderingDiscriminator);
    }

    public void setOrderingDiscriminator(Long orderingDiscriminator) {
        this.orderingDiscriminator = orderingDiscriminator;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DefaultEntityHasOrderInfo that = (DefaultEntityHasOrderInfo) o;

        return new EqualsBuilder()
                .append(elementOrder, that.elementOrder)
                .append(orderingDiscriminator, that.orderingDiscriminator)
                .append(entityName, that.entityName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(elementOrder)
                .append(orderingDiscriminator)
                .append(entityName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("elementOrder", elementOrder)
                .append("orderingDiscriminator", orderingDiscriminator)
                .append("entityName", entityName)
                .toString();
    }

    public static DefaultEntityHasOrderInfo newInstance(Integer elementOrder) {
        return new DefaultEntityHasOrderInfo(elementOrder);
    }

    public static DefaultEntityHasOrderInfo newInstance(String entityName, Integer elementOrder, Long orderingDiscriminator) {
        DefaultEntityHasOrderInfo defaultEntityHasOrderInfo = new DefaultEntityHasOrderInfo(elementOrder);
        defaultEntityHasOrderInfo.setEntityName(entityName);
        defaultEntityHasOrderInfo.setOrderingDiscriminator(orderingDiscriminator);
        return defaultEntityHasOrderInfo;
    }

}
