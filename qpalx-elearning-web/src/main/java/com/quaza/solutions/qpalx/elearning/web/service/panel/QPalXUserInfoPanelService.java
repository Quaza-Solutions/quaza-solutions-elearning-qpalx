package com.quaza.solutions.qpalx.elearning.web.service.panel;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.web.content.UserInfoPanelAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
public class QPalXUserInfoPanelService implements IQPalXUserInfoPanelService {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXUserInfoPanelService.class);

    @Override
    public void addUserInfoAttributes(Model model) {
        Assert.notNull(model, "model cannot be null");
        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        if(optionalUser.isPresent()) {
            QPalXUser qPalXUser = optionalUser.get();
            String userType = qPalXUser.getUserType().toString();
            LOGGER.info("Adding all all QPalx User information panel attributes for user:> {} type:> {}", qPalXUser.getEmail(), userType);
            model.addAttribute(UserInfoPanelAttributesE.LoggedInQPalXUser.toString(), qPalXUser);
            model.addAttribute(UserInfoPanelAttributesE.QPalXUserType.toString(), userType);
        }
    }

}
