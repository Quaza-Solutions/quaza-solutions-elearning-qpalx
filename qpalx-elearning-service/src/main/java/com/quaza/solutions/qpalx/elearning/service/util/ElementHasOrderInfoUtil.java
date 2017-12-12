package com.quaza.solutions.qpalx.elearning.service.util;

import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IEntityHasOrderInfo;
import org.springframework.data.repository.CrudRepository;
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
    public Optional<ElementOrderingResult> moveElementDown(IEntityHasOrderInfo elementToMoveDown, Collection<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Long orderContextID = getNoOrderingDiscriminator();
        return moveElementDown(orderContextID, elementToMoveDown, iEntityHasOrderInfos, crudRepository);
    }

    @Override
    public Optional<ElementOrderingResult> moveElementDown(Long orderingDiscriminator, IEntityHasOrderInfo elementToMoveDown, Collection<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Assert.notNull(orderingDiscriminator, "orderingDiscriminator cannot be null");
        Assert.notNull(elementToMoveDown, "elementToMoveDown cannot be null");
        Assert.notNull(iEntityHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.notNull(crudRepository, "crudRepository cannot be null");
        Assert.isTrue(iEntityHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
        Assert.isTrue(iEntityHasOrderInfos.contains(elementToMoveDown), "iElementHasOrderInfos cannot be empty");

        LOGGER.debug("Executing move down operation on element: {} with iElementHasOrderInfos: {}", elementToMoveDown, iEntityHasOrderInfos);

        // Build discriminator value that will be used to enforce ordering and get the element directly below
        Optional<Long> optionalOrderDiscriminator = getOptionalOrderingDiscriminator(orderingDiscriminator);
        Optional<IEntityHasOrderInfo> elementDirectlyBelow = getIElementHasOrderInfoDirectlyBelow(optionalOrderDiscriminator, elementToMoveDown, iEntityHasOrderInfos);

        if(elementDirectlyBelow.isPresent()) {
            elementToMoveDown.setElementOrder(elementToMoveDown.getElementOrder() + 1);
            elementDirectlyBelow.get().setElementOrder(elementDirectlyBelow.get().getElementOrder() - 1);
            ElementOrderingResult elementOrderingResult = ElementOrderingResult.newInstance(elementToMoveDown, elementDirectlyBelow.get());
            saveElementOrderingResult(elementOrderingResult, crudRepository);
            return Optional.of(elementOrderingResult);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ElementOrderingResult> moveElementUp(IEntityHasOrderInfo elementToMoveUp, Collection<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Long orderContextID = getNoOrderingDiscriminator();
        return moveElementUp(orderContextID, elementToMoveUp, iEntityHasOrderInfos, crudRepository);
    }

    @Override
    public Optional<ElementOrderingResult> moveElementUp(Long orderingDiscriminator, IEntityHasOrderInfo elementToMoveUp, Collection<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Assert.notNull(orderingDiscriminator, "orderContextID cannot be null");
        Assert.notNull(elementToMoveUp, "elementToMoveUp cannot be null");
        Assert.notNull(iEntityHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.notNull(crudRepository, "crudRepository cannot be null");
        Assert.isTrue(iEntityHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
        Assert.isTrue(iEntityHasOrderInfos.contains(elementToMoveUp), "iElementHasOrderInfos cannot be empty");

        LOGGER.debug("Executing move up operation on element: {} with iElementHasOrderInfos: {}", elementToMoveUp, iEntityHasOrderInfos);

        // Build discriminator value that will be used to enforce ordering and get the element directly above
        Optional<Long> optionalOrderDiscriminator = getOptionalOrderingDiscriminator(orderingDiscriminator);
        Optional<IEntityHasOrderInfo> elementDirectlyAbove = getIElementHasOrderInfoDirectlyAbove(optionalOrderDiscriminator, elementToMoveUp, iEntityHasOrderInfos);

        if(elementDirectlyAbove.isPresent()) {
            // move below the element directly below and move that element up
            elementToMoveUp.setElementOrder(elementToMoveUp.getElementOrder() - 1);
            elementDirectlyAbove.get().setElementOrder(elementDirectlyAbove.get().getElementOrder() + 1);
            ElementOrderingResult elementOrderingResult = ElementOrderingResult.newInstance(elementToMoveUp, elementDirectlyAbove.get());
            saveElementOrderingResult(elementOrderingResult, crudRepository);
            return Optional.of(elementOrderingResult);
        }

        return Optional.empty();
    }

    protected Optional<IEntityHasOrderInfo> getIElementHasOrderInfoDirectlyBelow(Optional<Long> orderContextID, IEntityHasOrderInfo elementToMove, Collection<IEntityHasOrderInfo> iEntityHasOrderInfos) {
        for(IEntityHasOrderInfo loopElementHasOrderInfo : iEntityHasOrderInfos) {
            if(orderContextID.isPresent() && orderContextID.get().equals(loopElementHasOrderInfo.getOrderingDiscriminator())) {
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

    protected Optional<IEntityHasOrderInfo> getIElementHasOrderInfoDirectlyAbove(Optional<Long> orderContextID, IEntityHasOrderInfo elementToMove, Collection<IEntityHasOrderInfo> iEntityHasOrderInfos) {
        for(IEntityHasOrderInfo loopElementHasOrderInfo : iEntityHasOrderInfos) {
            if(orderContextID.isPresent() && orderContextID.get().equals(loopElementHasOrderInfo.getOrderingDiscriminator())) {
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

    protected Long getNoOrderingDiscriminator() {
        // This will signal that no Order Context ID is to be used
        return Long.MIN_VALUE;
    }

    protected Optional<Long> getOptionalOrderingDiscriminator(Long orderingDiscriminator) {
        Optional<Long> optionalOrderingDiscriminator = orderingDiscriminator == Long.MIN_VALUE ? Optional.empty() : Optional.of(orderingDiscriminator);
        return optionalOrderingDiscriminator;
    }

    private void saveElementOrderingResult(ElementOrderingResult elementOrderingResult, CrudRepository crudRepository) {
        LOGGER.debug("Saving results of move operation: {}", elementOrderingResult);
        IEntityHasOrderInfo elementToMove = elementOrderingResult.getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = elementOrderingResult.getElementImpactedByMove();
        crudRepository.save(elementToMove);
        crudRepository.save(elementImpactedByMove);
    }

}
