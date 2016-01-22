package com.quaza.solutions.qpalx.elearning.domain.socialnetwork.repository;

import com.quaza.solutions.qpalx.elearning.domain.socialnetwork.SocialNetwork;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository object for SocialNetwork
 *
 * @author manyce400
 */
public interface ISocialNetworkRepository extends CrudRepository<SocialNetwork, Long> {


    @Query("Select sNetwork From SocialNetwork sNetwork Where sNetwork.socialNetworkName = ?1")
    public SocialNetwork findBySocialNetworkName(String socialNetworkName);
}
