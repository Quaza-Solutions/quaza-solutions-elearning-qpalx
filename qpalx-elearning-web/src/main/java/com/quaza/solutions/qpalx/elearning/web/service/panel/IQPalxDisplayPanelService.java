package com.quaza.solutions.qpalx.elearning.web.service.panel;

import org.springframework.ui.Model;

/**
 * Defines an API for enriching and adding attribute details to a model specifically used by a QPalX web panel display.
 *
 * @param <O> Display panel custom arguments
 * @author manyce400
 */
public interface IQPalxDisplayPanelService<O> {


    /**
     * @param model Web model
     */
    public void addDisplayPanelAttributes(Model model);

    /**
     * @param model Web model
     * @param panelArgs arguments required to build all information required by display panel.
     */
    public void addDisplayPanelAttributes(Model model, O panelArgs);

}
