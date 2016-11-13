package com.quaza.solutions.qpalx.elearning.web.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class FileUploadUtilTest {


    @InjectMocks
    private FileUploadUtil fileUploadUtil;


    @Test
    public void testGetUniqueSafeFileName() {
        String originalFileName = "BASIC HTML TAGS - v1.mp4";
        String newFileName = fileUploadUtil.getUniqueSafeFileName(originalFileName);
        Assert.assertNotNull(newFileName);
        Assert.assertNotEquals(originalFileName, newFileName);
        System.out.println("newFileName = " + newFileName);
    }

}
