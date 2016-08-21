package com.quaza.solutions.qpalx.elearning.web.signup;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class RegistrationSelectionWebVO {


    // Selected type of user sign-up.
    private String signUpType;

    public RegistrationSelectionWebVO() {

    }

    public String getSignUpType() {
        return signUpType;
    }

    public RegistrationTypeE getAsSignUpTypeE() {
        return RegistrationTypeE.getSignUpTypeE(signUpType);
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
