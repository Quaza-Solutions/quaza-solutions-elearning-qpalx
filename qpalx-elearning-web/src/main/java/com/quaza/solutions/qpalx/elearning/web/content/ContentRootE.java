package com.quaza.solutions.qpalx.elearning.web.content;

/**
 * Defines all the root content directories for all QPalX web content pages.
 *
 * @author manyce400
 */
public enum ContentRootE {


    Home(""),

    Student_Home("qpalx-student/home/"),

    Student_Signup("qpalx-student/signup/"),

    Parent_Home("qpalx-parent/home/"),

    Parent_Signup("qpalx-parent/signup/"),

    Content_Admin_Home("qpalx-content-admin/home/");
    ;

    private final String rootDirectory;

    ContentRootE(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public String getContentRootPagePath(String page) {
        return  new StringBuffer(rootDirectory)
                .append(page)
                .toString();
    }

}
