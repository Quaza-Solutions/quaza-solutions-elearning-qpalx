package com.quaza.solutions.qpalx.elearning.web.signup;

import org.springframework.util.Assert;

/**
 * Defines the available types of Sign-up types available on the QPalX platform.
 *
 * @author manyce400
 */
public enum SignUpTypeE {


    Student,
    Parent,
    Teacher
    ;

    public static SignUpTypeE getSignUpTypeE(String signUpType) {
        Assert.notNull(signUpType, "signUpType cannot be null");

        for(SignUpTypeE signUpTypeE : values()) {
            if(signUpTypeE.toString().equals(signUpType)) {
                return signUpTypeE;
            }
        }

        return null;
    }

}
