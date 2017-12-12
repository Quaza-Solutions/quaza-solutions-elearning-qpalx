package com.quaza.solutions.qpalx.elearning.domain.util;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Base default class that all Entities that contain Entity Order Information can extend from.
 *
 * @author manyce400
 */
@MappedSuperclass
public abstract class BaseEntityHasOrderInfo implements IEntityHasOrderInfo {


    @Column(name="ElementOrder", nullable=false)
    protected Integer elementOrder;


    @Override
    public void setElementOrder(Integer elementOrder) {
        this.elementOrder = elementOrder;
    }

    @Override
    public Integer getElementOrder() {
        return elementOrder;
    }


}
