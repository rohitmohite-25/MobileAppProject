package com.app.MobileAppProject.reposistory;

import com.app.MobileAppProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReposistory extends JpaRepository<User,Integer> {

    Optional<User> findUserByEmail(String email);
}
