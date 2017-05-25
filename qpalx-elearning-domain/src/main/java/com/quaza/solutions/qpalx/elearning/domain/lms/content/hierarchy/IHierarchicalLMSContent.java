package com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy;

/**
 * @author manyce400
 */
public interface IHierarchicalLMSContent {

    public static final String CLASS_ATTRIBUTE_IDENTIFIER = "IHierarchicalLMSContent";

    public String getHierarchicalLMSContentName();

    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE();

    public IHierarchicalLMSContent getIHierarchicalLMSContentParent();
}
