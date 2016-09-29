package com.quaza.solutions.qpalx.elearning.domain.quizresults;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Trading_1 on 8/25/2016.
 */
@XmlRootElement(name = "Result")
public class Result {
    CoreData coreData = new CoreData();
    @XmlElement(name = "CoreData")
    public void setCoreData(CoreData coreData){
        this.coreData = coreData;
    }
    public CoreData getCoreData(){
        return coreData;
    }
}
