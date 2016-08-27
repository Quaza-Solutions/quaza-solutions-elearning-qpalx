package com.quaza.solutions.qpalx.elearning.config.file.management;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Contains configurations that determine the different directories used through the application for file uploads.
 *
 * @author manyce400
 */
@Component
@ConfigurationProperties(locations = "file-uploads-locations.properties", prefix = "elearning.course.activity")
public class FileUploadLocationConfiguration {


    private String videos;

    private String quizzes;

    public FileUploadLocationConfiguration() {
    }

    public String getVideos() {
        return videos;
    }

    public String getQuizzes() {
        return quizzes;
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("videos", videos)
                .append("quizzes", quizzes)
                .toString();
    }

}
