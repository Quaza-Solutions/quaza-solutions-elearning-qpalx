package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by manyce400 on 11/17/17.
 */
public class ELearningMediaContentTest {


    private ELearningMediaContent eLearningMediaContent;

    @Before
    public void beforeTest() {
        eLearningMediaContent = new ELearningMediaContent(
                MediaContentTypeE.mp4.toString(), "/Users/manyce400/QuazaSolutions/elearning-content/Mathematics/Algebra-I/Introduction-to-Expressions/Second_cut_preview_1510939291840.mp4"
        );
    }

    @Test
    public void testGetActualFileName() {
        String fileName = eLearningMediaContent.getActualFileName();
        System.out.println("fileName = " + fileName);
        Assert.assertNotNull(fileName);
        Assert.assertEquals("Second_cut_preview_1510939291840.mp4", fileName);
    }

}
