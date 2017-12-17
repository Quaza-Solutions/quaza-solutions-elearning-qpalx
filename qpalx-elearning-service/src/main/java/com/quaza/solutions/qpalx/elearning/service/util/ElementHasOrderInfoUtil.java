package com.quaza.solutions.qpalx.elearning.service.util;

import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IEntityHasOrderInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author manyce400
 */
@Service(ElementHasOrderInfoUtil.BEAN_NAME)
public class ElementHasOrderInfoUtil implements IElementHasOrderInfoUtil {



    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.util.ElementHasOrderInfoUtil";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ElementHasOrderInfoUtil.class);


//    @Override
//    public <K extends IEntityHasOrderInfo> Optional<ElementOrderingResult> moveElementDown(K elementToMove, IEntityHasOrderInfoRepository<K> iEntityHasOrderInfoRepository) {
//        Assert.notNull(elementToMove, "elementToMoveDown cannot be null");
//        Assert.notNull(elementToMove.getOrderingDiscriminator(), "orderingDiscriminator cannot be null");
//        Assert.notNull(iEntityHasOrderInfoRepository, "iEntityHasOrderInfoRepository cannot be null");
//
//        // Find all the instances containing this elementToMoveDown
//        List<K> allInstancesContainingEntity =  iEntityHasOrderInfoRepository.findAllInstancesContainingEntity(elementToMove);
//        return executeMove(elementToMove, allInstancesContainingEntity, null);
//    }
//
//    protected <K extends IEntityHasOrderInfo> Optional<ElementOrderingResult> executeMove(K elementToMoveDown, List<K> iEntityHasOrderInfos, IEntityHasOrderInfoRepository<K> iEntityHasOrderInfoRepository) {
//        Assert.notNull(elementToMoveDown.getOrderingDiscriminator(), "orderingDiscriminator cannot be null");
//        Assert.notNull(elementToMoveDown, "elementToMoveDown cannot be null");
//        Assert.notNull(iEntityHasOrderInfos, "iElementHasOrderInfos cannot be null");
//        Assert.notNull(iEntityHasOrderInfoRepository, "crudRepository cannot be null");
//        Assert.isTrue(iEntityHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
//        Assert.isTrue(iEntityHasOrderInfos.contains(elementToMoveDown), "iElementHasOrderInfos cannot be empty");
//
//        LOGGER.debug("Executing move down operation on element: {} with iElementHasOrderInfos: {}", elementToMoveDown, iEntityHasOrderInfos);
//
//        // Get Element directly below that matches Ordering context so we can swap ElementOrder on the element to move and element found
//        Optional<K> elementDirectlyBelow = getElementHasOrderInfoDirectlyBelow(elementToMoveDown, iEntityHasOrderInfos);
//        if(elementDirectlyBelow.isPresent()) {
//            elementToMoveDown.setElementOrder(elementToMoveDown.getElementOrder() + 1);
//            elementDirectlyBelow.get().setElementOrder(elementDirectlyBelow.get().getElementOrder() - 1);
//            ElementOrderingResult elementOrderingResult = ElementOrderingResult.newInstance(elementToMoveDown, elementDirectlyBelow.get());
//            iEntityHasOrderInfoRepository.saveIEntityHasOrderInfo(elementToMoveDown);
//            iEntityHasOrderInfoRepository.saveIEntityHasOrderInfo(elementDirectlyBelow.get());
//            //saveElementOrderingResult(elementOrderingResult, crudRepository);
//            return Optional.of(elementOrderingResult);
//        }
//
//        return Optional.empty();
//    }
//
//    private <K extends IEntityHasOrderInfo> Optional<K> getElementHasOrderInfoDirectlyBelow(K elementToMove, List<K> iEntityHasOrderInfos) {
//        // Sort this collection using Element Order DESC compartor
//        Collections.sort(iEntityHasOrderInfos, IEntityHasOrderInfo.DEFAULT_ASC_COMPARATOR);
//
//        Optional<Long> orderingDiscriminator = elementToMove.getOrderingDiscriminator();
//
//        boolean foundElementToMoveInList = false;
//        K elementDirectlyBelow = null;
//        Iterator<K> iterator = iEntityHasOrderInfos.iterator();
//        while (iterator.hasNext()) {
//            K currentEntityHasOrderInfo = iterator.next();
//            System.out.println("previous.getElementOrder() = " + currentEntityHasOrderInfo.getElementOrder() + " discriminator = " + currentEntityHasOrderInfo.getOrderingDiscriminator());
//
//            // IF Element To Move in List has been found then break out and return current element
//            if(foundElementToMoveInList && currentEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
//                elementDirectlyBelow = currentEntityHasOrderInfo;
//                break;
//            }
//
//            // Comparing within the same context using the same Ordering Discriminator
//            if(currentEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
//                if(elementToMove.equals(currentEntityHasOrderInfo)) {
//                    foundElementToMoveInList = true;
//                }
//            }
//        }
//
//        return elementDirectlyBelow != null ? Optional.of(elementDirectlyBelow) : Optional.empty();
//    }

    @Override
    public Optional<ElementOrderingResult> moveElementDown(IEntityHasOrderInfo elementToMoveDown, List<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Assert.notNull(elementToMoveDown.getOrderingDiscriminator(), "orderingDiscriminator cannot be null");
        Assert.notNull(elementToMoveDown, "elementToMoveDown cannot be null");
        Assert.notNull(iEntityHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.notNull(crudRepository, "crudRepository cannot be null");
        Assert.isTrue(iEntityHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
        Assert.isTrue(iEntityHasOrderInfos.contains(elementToMoveDown), "iElementHasOrderInfos cannot be empty");

        LOGGER.debug("Executing move down operation on element: {} with iElementHasOrderInfos: {}", elementToMoveDown, iEntityHasOrderInfos);

        // Get Element directly below that matches Ordering context so we can swap ElementOrder on the element to move and element found
        Optional<IEntityHasOrderInfo> elementDirectlyBelow = getIElementHasOrderInfoDirectlyBelow(elementToMoveDown, iEntityHasOrderInfos);
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
    public Optional<ElementOrderingResult> moveElementUp(IEntityHasOrderInfo elementToMoveUp, List<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Assert.notNull(elementToMoveUp.getOrderingDiscriminator(), "orderingDiscriminator cannot be null");
        Assert.notNull(elementToMoveUp, "elementToMoveUp cannot be null");
        Assert.notNull(iEntityHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.notNull(crudRepository, "crudRepository cannot be null");
        Assert.isTrue(iEntityHasOrderInfos.size() > 0, "iElementHasOrderInfos cannot be empty");
        Assert.isTrue(iEntityHasOrderInfos.contains(elementToMoveUp), "iElementHasOrderInfos cannot be empty");


        LOGGER.debug("Executing move up operation on element: {} with iElementHasOrderInfos: {}", elementToMoveUp, iEntityHasOrderInfos);

        Optional<IEntityHasOrderInfo> elementDirectlyAbove = getIElementHasOrderInfoDirectlyAbove(elementToMoveUp, iEntityHasOrderInfos);
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


    @Override
    public void addNewEntityHasOrderInfoWithElementOrder(IEntityHasOrderInfo newEntityHasOrderInfo, List<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository) {
        Assert.notNull(newEntityHasOrderInfo.getOrderingDiscriminator(), "orderingDiscriminator cannot be null");
        Assert.notNull(newEntityHasOrderInfo, "newEntityHasOrderInfo cannot be null");
        Assert.notNull(iEntityHasOrderInfos, "iElementHasOrderInfos cannot be null");
        Assert.notNull(crudRepository, "crudRepository cannot be null");
        Assert.isTrue(!iEntityHasOrderInfos.contains(newEntityHasOrderInfo), "newEntityHasOrderInfo should not be in the list of iEntityHasOrderInfos");

        Optional<Long> orderingDiscriminator = newEntityHasOrderInfo.getOrderingDiscriminator();

        LOGGER.debug("Adding and setting new ElementOrder to newEntityHasOrderInfo: {} with orderingDiscriminator: {}", newEntityHasOrderInfo, orderingDiscriminator);

        int lastElementOrder = getLastElementOrder(orderingDiscriminator, iEntityHasOrderInfos);

        // With lastElementOrder increment by 1 and set the new ElementOrder.  Note this will save and update the instance to the DB.
        int incrementedElementOrder = lastElementOrder + 1;
        newEntityHasOrderInfo.setElementOrder(incrementedElementOrder);
        crudRepository.save(newEntityHasOrderInfo);
    }

    protected Optional<IEntityHasOrderInfo> getIElementHasOrderInfoDirectlyBelow(IEntityHasOrderInfo elementToMove, List<IEntityHasOrderInfo> iEntityHasOrderInfos) {
        // Sort this collection using Element Order DESC compartor
        Collections.sort(iEntityHasOrderInfos, IEntityHasOrderInfo.DEFAULT_ASC_COMPARATOR);

        Optional<Long> orderingDiscriminator = elementToMove.getOrderingDiscriminator();

        boolean foundElementToMoveInList = false;
        IEntityHasOrderInfo elementDirectlyBelow = null;
        Iterator<IEntityHasOrderInfo> iterator = iEntityHasOrderInfos.iterator();
        while (iterator.hasNext()) {
            IEntityHasOrderInfo currentEntityHasOrderInfo = iterator.next();
            System.out.println("previous.getElementOrder() = " + currentEntityHasOrderInfo.getElementOrder() + " discriminator = " + currentEntityHasOrderInfo.getOrderingDiscriminator());

            // IF Element To Move in List has been found then break out and return current element
            if(foundElementToMoveInList && currentEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
                elementDirectlyBelow = currentEntityHasOrderInfo;
                break;
            }

            // Comparing within the same context using the same Ordering Discriminator
            if(currentEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
                if(elementToMove.equals(currentEntityHasOrderInfo)) {
                    foundElementToMoveInList = true;
                }
            }
        }

        return elementDirectlyBelow != null ? Optional.of(elementDirectlyBelow) : Optional.empty();
    }

    protected Optional<IEntityHasOrderInfo> getIElementHasOrderInfoDirectlyAbove(IEntityHasOrderInfo elementToMove, List<IEntityHasOrderInfo> iEntityHasOrderInfos) {
        // Sort this List using Element Order ASC compartor.  This allows us to quickly find the element in order
        Collections.sort(iEntityHasOrderInfos, IEntityHasOrderInfo.DEFAULT_ASC_COMPARATOR);

        Optional<Long> orderingDiscriminator = elementToMove.getOrderingDiscriminator();

        boolean foundElementToMoveInList = false;
        IEntityHasOrderInfo elementDirectlyAbove = null;
        ListIterator<IEntityHasOrderInfo> iterator = iEntityHasOrderInfos.listIterator(iEntityHasOrderInfos.size());
        while (iterator.hasPrevious()) {
            IEntityHasOrderInfo currentEntityHasOrderInfo = iterator.previous();
            //System.out.println("previous.getElementOrder() = " + currentEntityHasOrderInfo.getElementOrder() + " discriminator = " + currentEntityHasOrderInfo.getOrderingDiscriminator());

            // IF Element To Move in List has been found then break out and return current element
            if(foundElementToMoveInList && currentEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
                elementDirectlyAbove = currentEntityHasOrderInfo;
                break;
            }

            // Comparing within the same context using the same Ordering Discriminator
            if(currentEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
                if(elementToMove.equals(currentEntityHasOrderInfo)) {
                    foundElementToMoveInList = true;
                }
            }
        }

        return elementDirectlyAbove != null ? Optional.of(elementDirectlyAbove) : Optional.empty();
    }


    protected int getLastElementOrder(Optional<Long> orderingDiscriminator, List<IEntityHasOrderInfo> iEntityHasOrderInfos) {
        LOGGER.debug("Retrieving last element order using orderContextID: {} from iEntityHasOrderInfos: {}", orderingDiscriminator, iEntityHasOrderInfos);
        Collections.sort(iEntityHasOrderInfos, IEntityHasOrderInfo.DEFAULT_ASC_COMPARATOR);

        int lastElementOrder = 0;
        for(IEntityHasOrderInfo iEntityHasOrderInfo : iEntityHasOrderInfos) {
            if(iEntityHasOrderInfo.matchesOrderingDiscriminator(orderingDiscriminator)) {
                lastElementOrder = iEntityHasOrderInfo.getElementOrder();
            }
        }

        LOGGER.debug("Returning value of lastElementOrder: {}", lastElementOrder);
        return lastElementOrder;
    }

    private void saveElementOrderingResult(ElementOrderingResult elementOrderingResult, CrudRepository crudRepository) {
        LOGGER.debug("Saving results of move operation: {}", elementOrderingResult);
        IEntityHasOrderInfo elementToMove = elementOrderingResult.getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = elementOrderingResult.getElementImpactedByMove();
        crudRepository.save(elementToMove);
        crudRepository.save(elementImpactedByMove);
    }

}
