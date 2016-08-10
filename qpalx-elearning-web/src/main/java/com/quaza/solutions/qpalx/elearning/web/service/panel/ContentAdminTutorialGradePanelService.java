package com.quaza.solutions.qpalx.elearning.web.service.panel;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.service.enums.AdminTutorialGradePanelE;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Implementation of IQPalxDisplayPanelService which creates attributes for Admin tutorial panel.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.ContentAdminTutorialGradePanelService")
public class ContentAdminTutorialGradePanelService implements IQPalxDisplayPanelService<ContentAdminTutorialGradePanelService.PanelDisplayAttributes> {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ContentAdminTutorialGradePanelService.class);

    @Override
    public void addDisplayPanelAttributes(Model model) {
        throw new UnsupportedOperationException("Content admin tutorial panel requires display attributes");
    }

    @Override
    public void addDisplayPanelAttributes(Model model, PanelDisplayAttributes panelDisplayAttributes) {
        Assert.notNull(model, "model cannot be null");
        Assert.notNull(panelDisplayAttributes, "panelDisplayAttributes cannot be null");
        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        if(optionalUser.isPresent()) {
            LOGGER.info("Adding ContentAdmin tutorial grade panel attributes for user:> {} with panelDisplayAttributes: {}", optionalUser.get().getEmail(), panelDisplayAttributes);
            String addCoursesEnabled = panelDisplayAttributes.isAddCoursesEnabled().toString();
            CurriculumType curriculumTypeE = CurriculumType.valueOf(panelDisplayAttributes.getCurriculumType());
            StudentTutorialGrade studentTutorialGrade = iqPalXTutorialService.findTutorialGradeByID(NumberUtils.toLong(panelDisplayAttributes.getTutorialGradeID()));
            model.addAttribute(AdminTutorialGradePanelE.AddCoursesEnabled.toString(), addCoursesEnabled);
            model.addAttribute(AdminTutorialGradePanelE.CurriculumType.toString(), curriculumTypeE.toString());
            model.addAttribute(AdminTutorialGradePanelE.StudentTutorialGrade.toString(), studentTutorialGrade.getTutorialGrade());
        }
    }

    public static class PanelDisplayAttributes {

        private Boolean addCoursesEnabled = false;
        private final String tutorialGradeID;
        private final String curriculumType;

        public PanelDisplayAttributes(boolean addCoursesEnabled, String tutorialGradeID, String curriculumType) {
            this.addCoursesEnabled = addCoursesEnabled;
            this.tutorialGradeID = tutorialGradeID;
            this.curriculumType = curriculumType;
        }

        public Boolean isAddCoursesEnabled() {
            return addCoursesEnabled;
        }

        public String getTutorialGradeID() {
            return tutorialGradeID;
        }

        public String getCurriculumType() {
            return curriculumType;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("addCoursesEnabled", addCoursesEnabled)
                    .append("tutorialGradeID", tutorialGradeID)
                    .append("curriculumType", curriculumType)
                    .toString();
        }
    }


    public PanelDisplayAttributes getPanelDisplayAttributes(Boolean addCoursesEnabled, String tutorialGradeID, String curriculumType) {
        Assert.notNull(addCoursesEnabled, "addCoursesEnabled cannot be null");
        Assert.notNull(tutorialGradeID, "tutorialGradeID cannot be null");
        Assert.notNull(curriculumType, "curriculumType  cannot be null");
        return new PanelDisplayAttributes(addCoursesEnabled, tutorialGradeID, curriculumType);
    }
}
