package com.quaza.solutions.qpalx.elearning.web.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * Implements and provides File upload capabilities.
 *
 * @author manyce400
 */
public interface IFileUploadUtil {


    public String uploadAndScaleImageFile(MultipartFile imageFile);
}
