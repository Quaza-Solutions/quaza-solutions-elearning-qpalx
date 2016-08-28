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



    @Value("${elearning.course.activity.videos.dir}")
    private String videosDirectory;

    @Value("${elearning.course.activity.quizzes.dir}")
    private String quizzesDirectory;

    @Value("${elearning.course.activity.symbolic.videos.dir}")
    private String videosSymbolicDirectory;

    @Value("${elearning.course.activity.symbolic.quizzes.dir}")
    private String quizzesSymbolicDirectory;

    public FileUploadLocationConfiguration() {

    }

    public String getVideosDirectory() {
        return videosDirectory;
    }

    public String getQuizzesDirectory() {
        return quizzesDirectory;
    }

    public String getVideosSymbolicDirectory() {
        return videosSymbolicDirectory;
    }

    public String getQuizzesSymbolicDirectory() {
        return quizzesSymbolicDirectory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("videosDirectory", videosDirectory)
                .append("quizzesDirectory", quizzesDirectory)
                .append("videosSymbolicDirectory", videosSymbolicDirectory)
                .append("quizzesSymbolicDirectory", quizzesSymbolicDirectory)
                .toString();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println();
    }
}
