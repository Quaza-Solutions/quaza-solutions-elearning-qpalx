package com.quaza.solutions.qpalx.elearning.service.util;

import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IElementHasOrderInfo;

import java.util.Collection;
import java.util.Optional;

/**
 * Note that this Utility will execute a move operation by only modifying the ElementOrder of the IElementHasOrderInfo to move.
 * The ordering of the underlying collection will not be sorted by this Utility.
 *
 * Clients can sort themselves using {@link IElementHasOrderInfo#DEFAULT_COMPARATOR}
 *
 * @author manyce400
 */
public interface IElementHasOrderInfoUtil {

    /**
     * Moves the #elementToMoveDown to move instance down in the Set of IElementHasOrderInfo.
     * Note that this algorithm expects #elementToMoveDown instance to actually be in the iElementHasOrderInfos
     *
     * @param elementToMoveDown
     * @param iElementHasOrderInfos
     * @return Returns Optional.empty() IF move operation was not executed
     */
    public Optional<ElementOrderingResult> moveElementDown(IElementHasOrderInfo elementToMoveDown, Collection<IElementHasOrderInfo> iElementHasOrderInfos);

    /**
     * Moves the #elementToMoveDown to move instance down in the Set of IElementHasOrderInfo.
     * Note that this algorithm expects #elementToMoveDown instance to actually be in the iElementHasOrderInfos
     *
     * @param orderContextID
     * @param elementToMoveDown
     * @param iElementHasOrderInfos
     * @return Returns Optional.empty() IF move operation was not executed
     */
    public Optional<ElementOrderingResult> moveElementDown(Long orderContextID, IElementHasOrderInfo elementToMoveDown, Collection<IElementHasOrderInfo> iElementHasOrderInfos);

    /**
     * Moves the #elementToMoveUp to move instance up in the Set of IElementHasOrderInfo.
     * Note that this algorithm expects #elementToMoveUp instance to actually be in the iElementHasOrderInfos
     *
     * @param elementToMoveUp
     * @param iElementHasOrderInfos
     * @return Returns Optional.empty() IF move operation was not executed
     */
    public Optional<ElementOrderingResult> moveElementUp(IElementHasOrderInfo elementToMoveUp, Collection<IElementHasOrderInfo> iElementHasOrderInfos);

    /**
     * Moves the #elementToMoveUp to move instance up in the Set of IElementHasOrderInfo.
     * Note that this algorithm expects #elementToMoveUp instance to actually be in the iElementHasOrderInfos
     *
     * @param orderContextID
     * @param elementToMoveUp
     * @param iElementHasOrderInfos
     * @return Returns Optional.empty() IF move operation was not executed
     */
    public Optional<ElementOrderingResult> moveElementUp(Long orderContextID, IElementHasOrderInfo elementToMoveUp, Collection<IElementHasOrderInfo> iElementHasOrderInfos);

}
