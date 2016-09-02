package com.quaza.solutions.qpalx.elearning.service.quizreporting;

import javax.xml.bind.JAXBException;

/**
 * Created by Trading_1 on 8/31/2016.
 */
public interface IQPalXQuizReporting {
    void storeResults(String fileName) throws JAXBException;
}
