package com.quaza.solutions.qpalx.elearning.domain.util;

import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IElementHasOrderInfo {

    public void setElementOrder(Integer order);

    public Integer getElementOrder();

    public Optional<Long> getOrderContextID();

    public ElementOrderCompartor DEFAULT_COMPARATOR = new ElementOrderCompartor();

    public default boolean isBelow(IElementHasOrderInfo iElementHasOrderInfo) {
        Assert.notNull(iElementHasOrderInfo, "iElementHasOrderInfo cannot be null");
        int thatElementOrder = iElementHasOrderInfo.getElementOrder();
        return this.getElementOrder() > thatElementOrder;
    }

    public default boolean isAbove(IElementHasOrderInfo iElementHasOrderInfo) {
        Assert.notNull(iElementHasOrderInfo, "iElementHasOrderInfo cannot be null");
        int thatElementOrder = iElementHasOrderInfo.getElementOrder();
        return this.getElementOrder() < thatElementOrder;
    }

    // Comparator implementation to maintain any collection by EelementOrder
    public class ElementOrderCompartor implements Comparator<IElementHasOrderInfo> {
        @Override
        public int compare(IElementHasOrderInfo o1, IElementHasOrderInfo o2) {
            return o1.getElementOrder().compareTo(o2.getElementOrder());
        }
    }

}
