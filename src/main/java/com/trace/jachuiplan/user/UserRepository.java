package com.trace.jachuiplan.user;

import com.trace.jachuiplan.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByNickname(String nickname);
    void deleteByUsername(String username);
}