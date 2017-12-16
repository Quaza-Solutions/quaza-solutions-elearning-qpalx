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

    public ElementOrderCompartorAsc DEFAULT_ASC_COMPARATOR = new ElementOrderCompartorAsc();

    public ElementOrderCompartorDesc DEFAULT_DESC_COMPARATOR = new ElementOrderCompartorDesc();

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

    public default boolean matchesOrderingDiscriminator(IEntityHasOrderInfo iEntityHasOrderInfo) {
        Assert.notNull(iEntityHasOrderInfo, "iElementHasOrderInfo cannot be null");
        Optional<Long> otherDiscriminator = iEntityHasOrderInfo.getOrderingDiscriminator();
        return matchesOrderingDiscriminator(otherDiscriminator);
    }

    public default boolean matchesOrderingDiscriminator(Optional<Long> orderingDiscriminator) {
        Assert.notNull(orderingDiscriminator, "orderingDiscriminator cannot be null");
        Optional<Long> thisDiscriminator = getOrderingDiscriminator();

        if(!thisDiscriminator.isPresent() && !orderingDiscriminator.isPresent()) {
            // Both have no Ordering Discriminator
            return true;
        }

        if(thisDiscriminator.isPresent() && orderingDiscriminator.isPresent()) {
            // Both present so compare both
            return orderingDiscriminator.isPresent() ? thisDiscriminator.get().equals(orderingDiscriminator.get()) : false;
        }

        return false;
    }

    // Comparator implementation to maintain any collection by EelementOrder
    public class ElementOrderCompartorAsc implements Comparator<IEntityHasOrderInfo> {
        @Override
        public int compare(IEntityHasOrderInfo o1, IEntityHasOrderInfo o2) {
            return o1.getElementOrder().compareTo(o2.getElementOrder());
        }
    }

    public class ElementOrderCompartorDesc implements Comparator<IEntityHasOrderInfo> {
        @Override
        public int compare(IEntityHasOrderInfo o1, IEntityHasOrderInfo o2) {
            return o2.getElementOrder().compareTo(o1.getElementOrder());
        }
    }

}
