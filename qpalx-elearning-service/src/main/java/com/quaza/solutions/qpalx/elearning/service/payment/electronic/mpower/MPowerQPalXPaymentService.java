package com.quaza.solutions.qpalx.elearning.service.payment.electronic.mpower;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower.*;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.service.payment.electronic.ITwoPhaseQPalXPaymentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * MPower implementation of ITwoPhaseQPalXPaymentService.  This will bill exclusively users with an MPower account Profile for their
 * QPalX Subscription.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.services.MPowerQPalXPaymentService")
public class MPowerQPalXPaymentService implements ITwoPhaseQPalXPaymentService<MPowerPaymentActionResponse, MPowerOnSitePaymentCharge> {


    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper jsonObjectMapper = new ObjectMapper();

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MPowerQPalXPaymentService.class);


    @Override
    public MPowerOnSitePaymentCharge executePaymentForSubscription(IQPalXUserVO iqPalXUserVO, QPalXSubscription qPalXSubscription)  {
        LOGGER.info("Executing MPower 2 phase payment for qPalXUser: {} with qPalXSubscription: {}", iqPalXUserVO.getEmail(), qPalXSubscription);

        String mPowerAccountAlias = iqPalXUserVO.getMPowerAccountAlias();
        if(mPowerAccountAlias != null) {
            MPowerInvoiceData mpowerInvoiceData = generateMPowerInvoice(qPalXSubscription);
            LOGGER.info("MPower Invoice data created:=> {}", mpowerInvoiceData);
            MPowerCustomerData customerData = generateMPowerCustomerData(mPowerAccountAlias);

            MPowerOnsitePaymentRequest paymentRequest = new MPowerOnsitePaymentRequest();
            paymentRequest.setInvoiceData(mpowerInvoiceData);
            paymentRequest.setCustomerData(customerData);

            MPowerPaymentActionResponse response = executePaymentTokenRequest(paymentRequest);

            // Use QPalX store token received from MPower to populate MPowerOnSitePaymentCharge
            MPowerOnSitePaymentCharge charge = new MPowerOnSitePaymentCharge();
            charge.setQpalXPaymentToken(response.getPaymentToken());
            return charge;
        }

        return null;
    }

    @Override
    public MPowerPaymentActionResponse completeSubscriptionPaymentPhaseII(MPowerOnSitePaymentCharge phaseIResponse)  {
        String chargeJSON = null;
        try {
            chargeJSON = jsonObjectMapper.writeValueAsString(phaseIResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-Master-Key", "83fbdb77-e18f-4bd4-bb8e-ebc2a2489fd7");
        headers.add("MP-Private-Key", "test_private_4jDhwdkgkPnZBLydG4G79aKRaMc");
        headers.add("MP-Token", "933b67e48e798f49fe92");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<String>(chargeJSON, headers);
        return restTemplate.postForObject("https://app.mpowerpayments.com/sandbox-api/v1/opr/charge", request, MPowerPaymentActionResponse.class);
    }


    private MPowerInvoiceData generateMPowerInvoice(QPalXSubscription qPalXSubscription) {
        String invoiceDescription = new StringBuffer(qPalXSubscription.getSubscriptionType().toString())
                .append(" - ")
                .append("Billing for QPalX E-Learning Platform.").append("\n")
                .append("Cost: ").append(qPalXSubscription.getSubscriptionCost())
                .toString();
        MPowerInvoice mpowerInvoice = new MPowerInvoice(
                qPalXSubscription.getSubscriptionCost(), invoiceDescription
        );

        MPowerInvoiceData invoiceData = new MPowerInvoiceData();
        invoiceData.setStore(MPowerIntegrationStore.QPALX);
        invoiceData.setInvoice(mpowerInvoice);
        return invoiceData;
    }

    private MPowerCustomerData generateMPowerCustomerData(String mPowerAccountAlias) {
        LOGGER.info("MPower account with userName: {} will be billed", mPowerAccountAlias);
        MPowerCustomerData customerData = new MPowerCustomerData();
        customerData.setUserName(mPowerAccountAlias);
        return customerData;
    }

    private MPowerPaymentActionResponse executePaymentTokenRequest(MPowerOnsitePaymentRequest paymentRequest)  {
        // Create JSON and dispatch request to MPower system
        String paymentRequestJSON = null;
        try {
            paymentRequestJSON = jsonObjectMapper.writeValueAsString(paymentRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("MP-Master-Key", "83fbdb77-e18f-4bd4-bb8e-ebc2a2489fd7");
        headers.add("MP-Private-Key", "test_private_4jDhwdkgkPnZBLydG4G79aKRaMc");
        headers.add("MP-Token", "933b67e48e798f49fe92");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<String>(paymentRequestJSON, headers);
        return restTemplate.postForObject("https://app.mpowerpayments.com/sandbox-api/v1/opr/create", request, MPowerPaymentActionResponse.class);
    }
}
