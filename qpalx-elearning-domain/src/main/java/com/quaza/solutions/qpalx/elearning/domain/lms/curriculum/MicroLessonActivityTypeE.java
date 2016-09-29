package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

/**
 * @author manyce400
 */
public enum MicroLessonActivityTypeE {


    Quiz,

    Video,

    Assesment,

    Assignment,

    Interactive_Excersise,

    Question_Bank

    ;

    public static MicroLessonActivityTypeE getByStringValue(String value) {
        for(MicroLessonActivityTypeE microLessonActivityTypeE : values()) {
            if(microLessonActivityTypeE.toString().equals(value)) {
                return microLessonActivityTypeE;
            }
        }

        return null;
    }
}
