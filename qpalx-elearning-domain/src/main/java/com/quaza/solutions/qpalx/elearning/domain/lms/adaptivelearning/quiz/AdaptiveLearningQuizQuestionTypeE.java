package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;

import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
public enum AdaptiveLearningQuizQuestionTypeE {


    Multiple_Choice(Optional.empty(), Optional.empty()),

    Check_Box(Optional.empty(), Optional.empty()),

    Video(Optional.of(ImmutableSet.of(MediaContentTypeE.mp4)), Optional.empty()),

    Image(Optional.of(ImmutableSet.of(MediaContentTypeE.jpeg, MediaContentTypeE.png)), Optional.empty()),

    True_False(Optional.empty(), Optional.empty())
    ;


    private final Optional<Set<MediaContentTypeE>> supportedQuestionMediaType;

    private final Optional<Set<MediaContentTypeE>> supportedQuestionAnswerMediaType;

    AdaptiveLearningQuizQuestionTypeE(Optional<Set<MediaContentTypeE>> supportedQuestionMediaType, Optional<Set<MediaContentTypeE>> supportedQuestionAnswerMediaType) {
        this.supportedQuestionMediaType = supportedQuestionMediaType;
        this.supportedQuestionAnswerMediaType = supportedQuestionAnswerMediaType;
    }

    public Optional<Set<MediaContentTypeE>> getSupportedQuestionMediaType() {
        return supportedQuestionMediaType;
    }

    public Optional<Set<MediaContentTypeE>> getSupportedQuestionAnswerMediaType() {
        return supportedQuestionAnswerMediaType;
    }

    public static AdaptiveLearningQuizQuestionTypeE getByName(String name) {
        for(AdaptiveLearningQuizQuestionTypeE adaptiveLearningQuizQuestionTypeE : values()) {
            if(adaptiveLearningQuizQuestionTypeE.toString().equals(name)) {
                return adaptiveLearningQuizQuestionTypeE;
            }
        }

        return null;
    }
}
