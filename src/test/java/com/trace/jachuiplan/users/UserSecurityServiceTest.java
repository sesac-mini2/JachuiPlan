package com.trace.jachuiplan.users;

import com.trace.jachuiplan.user.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserSecurityServiceTest {

    @Autowired
    private UserSecurityService userSecurityService;

    @Test
    void loadUserByUsername_admin() {
        // Given
        String adminUsername = "admin";

        // When
        UserDetails userDetails = userSecurityService.loadUserByUsername(adminUsername);

        // Then
        assertThat(userDetails.getUsername()).isEqualTo(adminUsername);
        assertThat(userDetails.getAuthorities().toString()).contains("ROLE_ADMIN");
    }

    @Test
    void loadUserByUsername_user() {
        // Given
        String userUsername = "user";

        // When
        UserDetails userDetails = userSecurityService.loadUserByUsername(userUsername);

        // Then
        assertThat(userDetails.getUsername()).isEqualTo(userUsername);
        assertThat(userDetails.getAuthorities().toString()).contains("ROLE_USER");
    }

    @Test
    void loadUserByUsername_notFound() {
        // Given
        String invalidUsername = "unknown";

        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> {
            userSecurityService.loadUserByUsername(invalidUsername);
        });
    }
}
