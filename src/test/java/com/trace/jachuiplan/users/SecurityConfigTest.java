package com.trace.jachuiplan.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void adminPage_accessibleByAdmin() throws Exception {
//        mockMvc.perform(get("/users/admin"))
//                .andExpect(status().isOk()); // ADMIN은 접근 가능
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void adminPage_forbiddenForUser() throws Exception {
//        mockMvc.perform(get("/users/admin"))
//                .andExpect(status().isForbidden()); // USER는 접근 불가
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void userPage_forbiddenForAdmin() throws Exception {
//        mockMvc.perform(get("/users/myPage"))
//                .andExpect(status().isForbidden()); // admin 접근 불가능
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void userPage_accessibleByUser() throws Exception {
//        mockMvc.perform(get("/users/myPage"))
//                .andExpect(status().isOk()); // user 접근 가능
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
//    void adminPage_accessibleByAdmin() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void adminPage_forbiddenForUser() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(username = "user", roles = {"USER"})
//    void userPage_accessibleByUser() throws Exception {
//        mockMvc.perform(get("/user"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void adminPage_unauthenticated() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().isUnauthorized());
//    }
}
