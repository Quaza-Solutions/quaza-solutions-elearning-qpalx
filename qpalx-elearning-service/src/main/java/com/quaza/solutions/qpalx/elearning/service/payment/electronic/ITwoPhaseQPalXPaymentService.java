package com.quaza.solutions.qpalx.elearning.service.payment.electronic;

/**
 * Extension of IQPalXPaymentService which involves a 2 phase process to complete payments.
 *
 * @param <S>
 * @param <R>
 * @author manyce400
 */
public interface ITwoPhaseQPalXPaymentService<S, R> extends IQPalXPaymentService<R> {


    /**
     * Execute the final phase II step to complet subscription.
     * @param phaseIResponse
     * @return
     */
    public S completeSubscriptionPaymentPhaseII(final R phaseIResponse);

}

