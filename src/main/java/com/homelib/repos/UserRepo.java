package com.homelib.repos;

import com.homelib.model.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Transient;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);


    User removeUserById(Long id);

    User readByUsername(String username);

    User getByUsername(String username);

    User readByActivationCode(String code);



    User getByActivationCode(String code);

    User findByActivationCode(String code);

    /*@Modifying(clearAutomatically = true)
    @Query("update usr u set u.activation_code =:activation_code where u.id =:id")
    public void setCode(@Param("id") Long id, @Param("activation_code") String code);*/


}
