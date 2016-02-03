package com.quaza.solutions.qpalx.elearning.web.student.subscription;

import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by manyce400 on 2/1/16.
 */
public class QPalXStudentSubscriptionController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXStudentSubscriptionController.class);



}
