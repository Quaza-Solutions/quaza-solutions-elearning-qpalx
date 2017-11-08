package com.quaza.solutions.qpalx.elearning.domain.lms.media;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Defines all the currently supported Elearning media content type used across the application.
 *
 * @author manyce400
 */
public enum MediaContentTypeE {

    jpeg,

    jpg,

    png,

    gif,

    mp4,

    swf,

    html,

    text;


    public static Set<MediaContentTypeE> getImageMediaTypes() {
        return ImmutableSet.of(jpeg, jpg, png, gif);
    }

}
