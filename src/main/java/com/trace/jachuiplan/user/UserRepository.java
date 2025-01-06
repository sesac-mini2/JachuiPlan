/// 김정은
package com.trace.jachuiplan.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByNickname(String nickname);
    void deleteByUsername(String username);
    Optional<Users> findByProviderAndProviderId(String provider, String providerId); // 소셜 로그인
}