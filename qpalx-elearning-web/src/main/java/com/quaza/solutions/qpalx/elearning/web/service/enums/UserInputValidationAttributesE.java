package com.quaza.solutions.qpalx.elearning.web.service.enums;

import com.google.common.collect.ImmutableMap;
import org.springframework.ui.Model;

/**
 * @author manyce400
 */
public enum UserInputValidationAttributesE {


    Invalid_FORM_Submission,

    ;

    public static ImmutableMap<String, String> buildAttributes(String formSubmissionErrorMessage) {
        return ImmutableMap.of(
               Invalid_FORM_Submission.toString(), formSubmissionErrorMessage
        );
    }

    public static void addInvalidFormSubmissionMessage(String formSubmissionErrorMessage, Model model) {
        model.addAttribute(Invalid_FORM_Submission.toString(), formSubmissionErrorMessage);
    }

}
