package com.quaza.solutions.qpalx.elearning.config.file.management;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Contains configurations that determine the different directories used through the application for file uploads.
 *
 * @author manyce400
 */
@Component
@PropertySource("classpath:file-uploads-locations.properties")
public class FileUploadLocationConfiguration {


    @Value("${elearning.content.resource.handler}")
    private String eLearningContentResourceHandler;

    @Value("${elearning.content.physical.file.resources.dir}")
    private String eLearningContentPhysicalFileResourcesDir;

    @Value("${elearning.content.resource.videos.dir}")
    private String eLearningContentVideosDir;

    @Value("${elearning.content.resource.quizzes.dir}")
    private String eLearningContentQuizzesDir;

    @Value("${elearning.content.resource.assesments.dir}")
    private String eLearningContentAssesmentsDir;

    @Value("${elearning.content.resource.assignments.dir}")
    private String eLearningContentAssignmentsDir;

    @Value("${elearning.content.resource.interactive-exercises.dir}")
    private String eLearningContentInteractiveExercisesDir;

    @Value("${application.temp.file.dir}")
    private String tempFileDirectory;


    public FileUploadLocationConfiguration() {

    }

    public String geteLearningContentResourceHandler() {
        return eLearningContentResourceHandler;
    }

    public String getELearningContentPhysicalFileResourcesDir() {
        return eLearningContentPhysicalFileResourcesDir;
    }

    public String geteLearningContentVideosDir() {
        return eLearningContentVideosDir;
    }

    public String geteLearningContentQuizzesDir() {
        return eLearningContentQuizzesDir;
    }

    public String geteLearningContentPhysicalFileResourcesDir() {
        return eLearningContentPhysicalFileResourcesDir;
    }

    public String geteLearningContentAssesmentsDir() {
        return eLearningContentAssesmentsDir;
    }

    public String geteLearningContentAssignmentsDir() {
        return eLearningContentAssignmentsDir;
    }

    public String geteLearningContentInteractiveExercisesDir() {
        return eLearningContentInteractiveExercisesDir;
    }

    public String getTempFileDirectory() {
        return tempFileDirectory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("eLearningContentResourceHandler", eLearningContentResourceHandler)
                .append("eLearningContentPhysicalFileResourcesDir", eLearningContentPhysicalFileResourcesDir)
                .append("eLearningContentVideosDir", eLearningContentVideosDir)
                .append("eLearningContentQuizzesDir", eLearningContentQuizzesDir)
                .append("eLearningContentAssesmentsDir", eLearningContentAssesmentsDir)
                .append("eLearningContentAssignmentsDir", eLearningContentAssignmentsDir)
                .append("eLearningContentInteractiveExercisesDir", eLearningContentInteractiveExercisesDir)
                .append("tempFileDirectory", tempFileDirectory)
                .toString();
    }

    @PostConstruct
    public void post() {
        System.out.println();
    }

}
