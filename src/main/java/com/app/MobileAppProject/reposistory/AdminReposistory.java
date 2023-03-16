package com.app.MobileAppProject.reposistory;

import com.app.MobileAppProject.model.admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminReposistory   extends JpaRepository<admin,String> {
}
