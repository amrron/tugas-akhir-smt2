package com.core.perabot.model.repository;

import com.core.perabot.model.models.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<UserAdmin, String> {
    @Query("SELECT a FROM UserAdmin a WHERE a.email = :email")
    UserAdmin findByEmail(String email);
}
