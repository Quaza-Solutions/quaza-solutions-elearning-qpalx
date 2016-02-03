package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXTutorialService {

    public QPalXTutorialLevel findQPalXTutorialLevelByID(Long id);

    public List<QPalXTutorialLevel> findAllQPalXTutorialLevels();

}
