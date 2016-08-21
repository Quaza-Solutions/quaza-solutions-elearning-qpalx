package com.quaza.solutions.qpalx.elearning.web.service.enums;

import com.google.common.collect.ImmutableMap;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

/**
 * @author manyce400
 */
public enum AdminTutorialGradePanelE {

    AddCoursesEnabled,

    AddCourseActivitiesEnabled,

    CurriculumType,

    StudentTutorialGrade,

    ELearningCourseWebVO,

    ELearningCourseActivityWebVO;

    public static ImmutableMap<String, Object> buildAttributes(Boolean addCoursesEnabled, Boolean addCourseActivitiesEnabled, StudentTutorialGrade studentTutorialGrade, String curriculumType) {
        ImmutableMap<String, Object> attributes = ImmutableMap.of(
                AddCoursesEnabled.toString(), addCoursesEnabled.toString(),
                AddCourseActivitiesEnabled.toString(), addCourseActivitiesEnabled.toString(),
                StudentTutorialGrade.toString(), studentTutorialGrade,
                CurriculumType.toString(), curriculumType
        );

        return  attributes;
    }
}
