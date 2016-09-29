package com.quaza.solutions.qpalx.elearning.domain.quizresults;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Trading_1 on 8/25/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseName {

    @XmlAttribute
    private String value;

    public void setValue(String value){this.value = value;}

    public String getValue(){return value;}

}
