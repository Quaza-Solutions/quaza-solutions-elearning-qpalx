package com.quaza.solutions.qpalx.elearning.domain.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author manyce400
 */
public class BaseElementHasOrderInfo implements IElementHasOrderInfo {



    private Integer elementOrder;

    public BaseElementHasOrderInfo() {

    }

    public BaseElementHasOrderInfo(Integer elementOrder) {
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
    public Optional<Long> getOrderContextID() {
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(elementOrder)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BaseElementHasOrderInfo that = (BaseElementHasOrderInfo) o;

        return new EqualsBuilder()
                .append(elementOrder, that.elementOrder)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("elementOrder", elementOrder)
                .toString();
    }

    public static BaseElementHasOrderInfo newInstance(Integer elementOrder) {
        return new BaseElementHasOrderInfo(elementOrder);
    }

}
