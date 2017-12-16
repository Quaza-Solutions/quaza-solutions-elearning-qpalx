package com.quaza.solutions.qpalx.elearning.service.util;

import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IEntityHasOrderInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Note that this Utility will execute a move operation by only modifying the ElementOrder of the IElementHasOrderInfo to move.
 * The ordering of the underlying collection will not be sorted by this Utility.
 *
 * Clients can sort themselves using both {@link IEntityHasOrderInfo#DEFAULT_ASC_COMPARATOR} and {@link IEntityHasOrderInfo#DEFAULT_DESC_COMPARATOR}
 *
 * @author manyce400
 */
public interface IElementHasOrderInfoUtil {

    /**
     * Moves the #elementToMoveDown to move instance down in the Set of IElementHasOrderInfo.
     * Note that this algorithm expects #elementToMoveDown instance to actually be in the iElementHasOrderInfos
     *
     * @param elementToMoveDown
     * @param iEntityHasOrderInfos
     * @return Returns Optional.empty() IF move operation was not executed
     */
    public Optional<ElementOrderingResult> moveElementDown(IEntityHasOrderInfo elementToMoveDown, List<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository);


    /**
     * Moves the #elementToMoveUp to move instance up in the Set of IElementHasOrderInfo.
     * Note that this algorithm expects #elementToMoveUp instance to actually be in the iElementHasOrderInfos
     *
     * @param elementToMoveUp
     * @param iEntityHasOrderInfos
     * @return Returns Optional.empty() IF move operation was not executed
     */
    public Optional<ElementOrderingResult> moveElementUp(IEntityHasOrderInfo elementToMoveUp, List<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository);


    /**
     * Add and save a new instance of IEntityHasOrderInfo and set element order based on the Ordering Information of the iEntityHasOrderInfos.
     *
     *
     * @param orderingDiscriminator
     * @param newEntityHasOrderInfo
     * @param iEntityHasOrderInfos
     */
    public void addNewEntityHasOrderInfoWithElementOrder(Optional<Long> orderingDiscriminator, IEntityHasOrderInfo newEntityHasOrderInfo, List<IEntityHasOrderInfo> iEntityHasOrderInfos, CrudRepository crudRepository);

}
