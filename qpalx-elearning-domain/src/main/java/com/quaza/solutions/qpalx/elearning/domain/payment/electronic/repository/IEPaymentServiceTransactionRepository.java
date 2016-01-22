package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.repository;

import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.EPaymentTransaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring JPA object for managing persistence of EPaymentTransaction domain object.
 *
 * @author manyce400
 */
public interface IEPaymentServiceTransactionRepository extends CrudRepository<EPaymentTransaction, Long> {


}
