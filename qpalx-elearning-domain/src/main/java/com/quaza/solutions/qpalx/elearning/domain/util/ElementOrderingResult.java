package com.quaza.solutions.qpalx.elearning.domain.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
public class ElementOrderingResult {


    // Original element for which ordering operation was executed for
    private IElementHasOrderInfo elementToMove;

    // Element impacted as a result of the move operation
    private IElementHasOrderInfo elementImpactedByMove;


    public ElementOrderingResult(IElementHasOrderInfo elementToMove, IElementHasOrderInfo elementImpactedByMove) {
        Assert.notNull(elementToMove, "elementToMove cannot be null");
        Assert.notNull(elementImpactedByMove, "elementImpactedByMove cannot be null");
        this.elementToMove = elementToMove;
        this.elementImpactedByMove = elementImpactedByMove;
    }

    public IElementHasOrderInfo getElementToMove() {
        return elementToMove;
    }

    public IElementHasOrderInfo getElementImpactedByMove() {
        return elementImpactedByMove;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("elementToMove", elementToMove)
                .append("elementImpactedByMove", elementImpactedByMove)
                .toString();
    }

    public static final ElementOrderingResult newInstance(IElementHasOrderInfo elementToMove, IElementHasOrderInfo elementImpactedByMove) {
        return new ElementOrderingResult(elementToMove, elementImpactedByMove);
    }
}
