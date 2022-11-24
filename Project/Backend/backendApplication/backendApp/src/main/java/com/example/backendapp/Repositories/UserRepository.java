package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Procedure(name = "GET_USER_BY_ID")
    User findUserById(@Param("user_id_") Long id);

    @Procedure(procedureName = "GET_USER_ID_BY_LOGIN", outputParameterName = "user_id_")
    Long GetUserIdByLogin(@Param("login_") String login);

    @Procedure(procedureName = "REGISTER_USER")
    void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id);

    User findByLogin(String username);
}
