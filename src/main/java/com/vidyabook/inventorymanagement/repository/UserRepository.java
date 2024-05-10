package com.vidyabook.inventorymanagement.repository;

import com.vidyabook.inventorymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from admin_user u where (u.username = :identifier or u.email = :identifier) and u.password = :password")
    Optional<User> userLogin(@Param("identifier") String identifier,@Param("password") String password);

    @Query("select u.username from admin_user u where u.email = :identifier or u.username = :identifier ")
    String findByIdentifier(@Param("identifier") String identifier);
}
