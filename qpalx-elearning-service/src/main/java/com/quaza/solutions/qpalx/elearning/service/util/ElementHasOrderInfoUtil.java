package com.quaza.solutions.qpalx.elearning.service.util;

import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IElementHasOrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service(ElementHasOrderInfoUtil.BEAN_NAME)
public class ElementHasOrderInfoUtil implements IElementHasOrderInfoUtil {



    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.util.ElementHasOrderInfoUtil";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ElementHasOrderInfoUtil.class);


    @Override
    public Optional<ElementOrderingResult> moveElementDown(IElementHasOrderInfo elementToMoveDown, Collection<IElementHasOrderInfo> iElementHasOrderInfos) {
        // This will signal that no Order Context ID is to be used
        Long orderContextID = Long.MIN_VALUE;
        return moveElementDown(orderContextID, elementToMoveDown, iElementHasOrderInfos);
    }

    @Override
    public Optional<ElementOrderingResult> moveElementDown(Long orderContextID, IElementHasOrderInfo elementToMoveDown, Collection<IElementHasOrderInfo> iElementHasOrderInfos) {
        Assert.notNull(orderContextID, "orderContextID cannot be null");
        Assert.notNull(elementToMoveDown, "elementToMoveDown cannot be null");
        Assert.notNull(iElementHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.isTrue(iElementHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
        Assert.isTrue(iElementHasOrderInfos.contains(elementToMoveDown), "iElementHasOrderInfos cannot be empty");

        LOGGER.debug("Executing move down operation on element: {} with iElementHasOrderInfos: {}", elementToMoveDown, iElementHasOrderInfos);

        Optional<Long> optionalOrderContextID = orderContextID == Long.MIN_VALUE ? Optional.empty() : Optional.of(orderContextID);
        Optional<IElementHasOrderInfo> elementDirectlyBelow = getIElementHasOrderInfoDirectlyBelow(optionalOrderContextID, elementToMoveDown, iElementHasOrderInfos);

        if(elementDirectlyBelow.isPresent()) {
            elementToMoveDown.setElementOrder(elementToMoveDown.getElementOrder() + 1);
            elementDirectlyBelow.get().setElementOrder(elementDirectlyBelow.get().getElementOrder() - 1);
            ElementOrderingResult elementOrderingResult = ElementOrderingResult.newInstance(elementToMoveDown, elementDirectlyBelow.get());
            return Optional.of(elementOrderingResult);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ElementOrderingResult> moveElementUp(IElementHasOrderInfo elementToMoveUp, Collection<IElementHasOrderInfo> iElementHasOrderInfos) {
        // This will signal that no Order Context ID is to be used
        Long orderContextID = Long.MIN_VALUE;
        return moveElementUp(orderContextID, elementToMoveUp, iElementHasOrderInfos);
    }

    @Override
    public Optional<ElementOrderingResult> moveElementUp(Long orderContextID, IElementHasOrderInfo elementToMoveUp, Collection<IElementHasOrderInfo> iElementHasOrderInfos) {
        Assert.notNull(orderContextID, "orderContextID cannot be null");
        Assert.notNull(elementToMoveUp, "elementToMoveUp cannot be null");
        Assert.notNull(iElementHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.isTrue(iElementHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
        Assert.isTrue(iElementHasOrderInfos.contains(elementToMoveUp), "iElementHasOrderInfos cannot be empty");

        LOGGER.debug("Executing move up operation on element: {} with iElementHasOrderInfos: {}", elementToMoveUp, iElementHasOrderInfos);

        Optional<Long> optionalOrderContextID = orderContextID == Long.MIN_VALUE ? Optional.empty() : Optional.of(orderContextID);
        Optional<IElementHasOrderInfo> elementDirectlyAbove = getIElementHasOrderInfoDirectlyAbove(optionalOrderContextID, elementToMoveUp, iElementHasOrderInfos);
        if(elementDirectlyAbove.isPresent()) {
            // move below the element directly below and move that element up
            elementToMoveUp.setElementOrder(elementToMoveUp.getElementOrder() - 1);
            elementDirectlyAbove.get().setElementOrder(elementDirectlyAbove.get().getElementOrder() + 1);
            ElementOrderingResult elementOrderingResult = ElementOrderingResult.newInstance(elementToMoveUp, elementDirectlyAbove.get());
            return Optional.of(elementOrderingResult);
        }

        return Optional.empty();
    }

    protected Optional<IElementHasOrderInfo> getIElementHasOrderInfoDirectlyBelow(Optional<Long> orderContextID, IElementHasOrderInfo elementToMove, Collection<IElementHasOrderInfo> iElementHasOrderInfos) {
        for(IElementHasOrderInfo loopElementHasOrderInfo : iElementHasOrderInfos) {
            if(orderContextID.isPresent() && orderContextID.get().equals(loopElementHasOrderInfo.getOrderContextID())) {
                boolean isBelow = loopElementHasOrderInfo.isBelow(elementToMove);
                if(isBelow) {
                    return Optional.of(loopElementHasOrderInfo);
                }
            } else {
                boolean isBelow = loopElementHasOrderInfo.isBelow(elementToMove);
                if(isBelow) {
                    return Optional.of(loopElementHasOrderInfo);
                }
            }
        }

        return Optional.empty();
    }

    protected Optional<IElementHasOrderInfo> getIElementHasOrderInfoDirectlyAbove(Optional<Long> orderContextID, IElementHasOrderInfo elementToMove, Collection<IElementHasOrderInfo> iElementHasOrderInfos) {
        for(IElementHasOrderInfo loopElementHasOrderInfo : iElementHasOrderInfos) {
            if(orderContextID.isPresent() && orderContextID.get().equals(loopElementHasOrderInfo.getOrderContextID())) {
                boolean isAbove = loopElementHasOrderInfo.isAbove(elementToMove);
                if(isAbove) {
                    return Optional.of(loopElementHasOrderInfo);
                }
            } else {
                boolean isAbove = loopElementHasOrderInfo.isAbove(elementToMove);
                if(isAbove) {
                    return Optional.of(loopElementHasOrderInfo);
                }
            }
        }

        return Optional.empty();
    }

}
