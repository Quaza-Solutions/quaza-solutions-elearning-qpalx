package com.quaza.solutions.qpalx.elearning.service.quizreporting;

import com.quaza.solutions.qpalx.elearning.domain.quizresults.Course;
import com.quaza.solutions.qpalx.elearning.domain.quizresults.QuizResults;
import com.quaza.solutions.qpalx.elearning.domain.quizresults.repository.IQuizResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Trading_1 on 8/31/2016.
 */
@Service
public class DefaultQPalXQuizReporting implements IQPalXQuizReporting{
    @Autowired
    IQuizResultsRepository iQuizResultsRepository;
    @Override
    public void storeResults(String fileName) throws JAXBException{//in controller reqparam("Filename") String filename
        //get file name from php reqparam
        //xml root element - Course
        //getqpalxuser
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String file_path = "C:\\phpserver\\CaptivateResults\\QuazaSolutions\\Education\\BiologyQuizCH1\\" + fileName;
        File file = new File(file_path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Course.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Course course = (Course) unmarshaller.unmarshal(file);
        QuizResults quizResults = new QuizResults();
        quizResults.setLessonName(course.getCourseName().getValue());
        quizResults.setScore(course.getResult().getCoreData().getScore().getValue());
        quizResults.setQuizDuration(course.getResult().getCoreData().getSessionTime().getValue());
        quizResults.setqPalXUser(null);
        iQuizResultsRepository.save(quizResults);
        file.delete();
    }
}
