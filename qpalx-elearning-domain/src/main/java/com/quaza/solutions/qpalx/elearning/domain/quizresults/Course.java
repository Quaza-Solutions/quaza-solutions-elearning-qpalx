package com.quaza.solutions.qpalx.elearning.domain.quizresults;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Trading_1 on 8/25/2016.
 */
@XmlRootElement(name="Course")
public class Course {
    Result result = new Result();
    CourseName courseName = new CourseName();
    @XmlElement(name = "Result")
    public void setResult(Result result) { this.result = result;}
    public Result getResult() {return result;}
    @XmlElement(name = "CourseName")
    public void setCourseName(CourseName courseName){
        this.courseName = courseName;
    }
    public CourseName getCourseName(){
        return courseName;
    }

}
