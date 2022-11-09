package com.example.backendapp.Repositories;

import com.example.backendapp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);

    @Query(value = "SELECT ID FROM USERS WHERE LOGIN = :login", nativeQuery = true)
    Long GetUserIdByLogin(@Param("login") String login);

    @Query(value = "SELECT ID FROM USERS WHERE LOGIN = :login AND PASSWORD = ENCRYPT_PASSWORD(:password)", nativeQuery = true)
    Long GetUserIdByLoginAndPassword(String login, String password);

    @Procedure(procedureName = "REGISTER_USER")
    void saveUser(@Param("user_login") String login, @Param("user_password") String password, @Param("user_email") String email, @Param("user_role_id") Long id);
}
