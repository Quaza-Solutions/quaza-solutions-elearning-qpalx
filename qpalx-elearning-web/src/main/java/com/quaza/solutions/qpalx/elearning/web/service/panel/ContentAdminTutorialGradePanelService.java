package com.quaza.solutions.qpalx.elearning.web.service.panel;

import com.google.common.collect.ImmutableMap;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.service.enums.AdminTutorialGradePanelE;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import com.quaza.solutions.qpalx.elearning.web.utils.IWebAttributesUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Implementation of IContentAdminTutorialGradePanelService which creates attributes for Admin tutorial panel.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.ContentAdminTutorialGradePanelService")
public class ContentAdminTutorialGradePanelService implements IContentAdminTutorialGradePanelService {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.WebAttributesUtil")
    private IWebAttributesUtil iWebAttributesUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ContentAdminTutorialGradePanelService.class);




    @Override
    public void addDisplayPanelAttributes(Model model, Boolean addCoursesEnabled, Boolean addCourseActivitiesEnabled, String studentTutorialGradeID, String curriculumType) {
        Assert.notNull(model, "model cannot be null");
        Assert.notNull(addCoursesEnabled, "addCoursesEnabled cannot be null");
        Assert.notNull(addCourseActivitiesEnabled, "addCourseActivitiesEnabled cannot be null");
        Assert.notNull(studentTutorialGradeID, "studentTutorialGradeID cannot be null");
        Assert.notNull(curriculumType, "curriculumType cannot be null");

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        if(optionalUser.isPresent()) {
            LOGGER.debug("Adding all attributes required for admin tutorial grade panel for user:> {}", optionalUser.get().getEmail());
            CurriculumType curriculumTypeE = CurriculumType.valueOf(curriculumType);
            StudentTutorialGrade studentTutorialGrade = iqPalXTutorialService.findTutorialGradeByID(NumberUtils.toLong(studentTutorialGradeID));
            ImmutableMap<String, Object> attributes = AdminTutorialGradePanelE.buildAttributes(addCoursesEnabled, addCourseActivitiesEnabled, studentTutorialGrade, curriculumType.toString());
            iWebAttributesUtil.addWebAttributes(model, attributes);
        }

    }

}
