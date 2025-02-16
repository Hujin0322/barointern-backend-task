package com.barointern.yujin.domain.repository;

import com.barointern.yujin.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);
}
