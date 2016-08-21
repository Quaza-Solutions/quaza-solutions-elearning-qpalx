package com.quaza.solutions.qpalx.elearning.web.service.panel;

import org.springframework.ui.Model;

/**
 * Defines an API for enriching and adding attribute details to a model specifically used by a QPalX web panel display.
 *
 * @author manyce400
 */
public interface IContentAdminTutorialGradePanelService {


    /**
     * Add all attributes required to display the Admin display panel
     *
     * @param model
     * @param addCoursesEnabled
     * @param addCourseActivitiesEnabled
     * @param studentTutorialGradeID
     * @param curriculumType
     */
    public void addDisplayPanelAttributes(Model model, Boolean addCoursesEnabled, Boolean addCourseActivitiesEnabled, String studentTutorialGradeID, String curriculumType);

}
