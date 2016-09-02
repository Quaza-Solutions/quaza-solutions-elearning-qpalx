package com.quaza.solutions.qpalx.elearning.domain.quizresults;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Trading_1 on 8/25/2016.
 */
@XmlRootElement(name = "CoreData")
public class CoreData {
    Score score = new Score();
    SessionTime sessionTime = new SessionTime();
    @XmlElement(name = "Score")
    public void setScore(Score score){
        this.score = score;
    }
    public Score getScore(){return score;}
    @XmlElement(name = "SessionTime")
    public void setSessionTime(SessionTime sessionTime) {
        this.sessionTime = sessionTime;
    }
    public SessionTime getSessionTime() {return sessionTime;}

}
