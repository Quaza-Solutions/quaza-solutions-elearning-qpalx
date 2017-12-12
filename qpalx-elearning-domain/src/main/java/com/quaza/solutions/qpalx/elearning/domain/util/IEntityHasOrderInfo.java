package com.quaza.solutions.qpalx.elearning.domain.util;

import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IEntityHasOrderInfo {

    public void setElementOrder(Integer order);

    public Integer getElementOrder();

    public Optional<Long> getOrderingDiscriminator();

    public ElementOrderCompartor DEFAULT_COMPARATOR = new ElementOrderCompartor();

    public default boolean isBelow(IEntityHasOrderInfo iEntityHasOrderInfo) {
        Assert.notNull(iEntityHasOrderInfo, "iElementHasOrderInfo cannot be null");
        int thatElementOrder = iEntityHasOrderInfo.getElementOrder();
        return this.getElementOrder() > thatElementOrder;
    }

    public default boolean isAbove(IEntityHasOrderInfo iEntityHasOrderInfo) {
        Assert.notNull(iEntityHasOrderInfo, "iElementHasOrderInfo cannot be null");
        int thatElementOrder = iEntityHasOrderInfo.getElementOrder();
        return this.getElementOrder() < thatElementOrder;
    }

    // Comparator implementation to maintain any collection by EelementOrder
    public class ElementOrderCompartor implements Comparator<IEntityHasOrderInfo> {
        @Override
        public int compare(IEntityHasOrderInfo o1, IEntityHasOrderInfo o2) {
            return o1.getElementOrder().compareTo(o2.getElementOrder());
        }
    }

}
