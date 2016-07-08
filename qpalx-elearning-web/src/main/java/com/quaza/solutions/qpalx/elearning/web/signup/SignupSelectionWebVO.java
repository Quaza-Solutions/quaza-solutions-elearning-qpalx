package com.quaza.solutions.qpalx.elearning.web.signup;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class SignUpSelectionWebVO {


    // Selected type of user sign-up.
    private String signUpType;

    public SignUpSelectionWebVO() {

    }

    public String getSignUpType() {
        return signUpType;
    }

    public SignUpTypeE getAsSignUpTypeE() {
        return SignUpTypeE.getSignUpTypeE(signUpType);
    }

    public void setSignUpType(String signUpType) {
        this.signUpType = signUpType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("signUpType", signUpType)
                .toString();
    }
}