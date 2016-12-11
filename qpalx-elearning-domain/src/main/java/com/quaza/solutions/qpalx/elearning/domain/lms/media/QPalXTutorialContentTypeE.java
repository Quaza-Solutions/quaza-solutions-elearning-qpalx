package com.quaza.solutions.qpalx.elearning.domain.lms.media;

/**
 * Represents all the different types of tutorial content that is currently available in QPalX application
 *
 * @author manyce400
 */
public enum QPalXTutorialContentTypeE {

    Quiz,

    Video,

    Assesment,

    Assignment,

    Interactive_Excersise
    ;

    public static QPalXTutorialContentTypeE getByStringValue(String value) {
        for(QPalXTutorialContentTypeE QPalXTutorialContentTypeE : values()) {
            if(QPalXTutorialContentTypeE.toString().equals(value)) {
                return QPalXTutorialContentTypeE;
            }
        }

        return null;
    }

}
