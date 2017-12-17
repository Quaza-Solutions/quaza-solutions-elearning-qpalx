package com.quaza.solutions.qpalx.elearning.domain.util.repository;

import com.quaza.solutions.qpalx.elearning.domain.util.IEntityHasOrderInfo;

import java.util.List;

/**
 * @author manyce400
 */
public interface IEntityHasOrderInfoRepository<K extends IEntityHasOrderInfo> {

    public void saveIEntityHasOrderInfo(K iEntityHasOrderInfo);

    public List<K> findAllInstancesContainingEntity(K iEntityHasOrderInfo);

}
