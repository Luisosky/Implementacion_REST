package org.uniquindio.prr23.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.uniquindio.prr23.dto.UserRegistrationRequest;
import org.uniquindio.prr23.model.Rol;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        UserRegistrationRequest user = new UserRegistrationRequest(
                null,
                "test@example.com",
                "Password1",
                "Test User",
                LocalDate.of(1990, 1, 1),
                Rol.USER
        );

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
        // Crea un usuario para obtener su id
        UserRegistrationRequest user = new UserRegistrationRequest(
                null,
                "getuser@example.com",
                "Password1",
                "Get User",
                LocalDate.of(1990, 1, 1),
                Rol.USER
        );
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();
        UserRegistrationRequest createdUser = objectMapper.readValue(response, UserRegistrationRequest.class);

        mockMvc.perform(get("/api/users/" + createdUser.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllUsersPaginated() throws Exception {
        mockMvc.perform(get("/api/users/paginated")
                        .param("page", "0")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Crea un usuario primero para asegurarse de que exista un id
        UserRegistrationRequest user = new UserRegistrationRequest(
                null,
                "updateuser@example.com",
                "Password1",
                "Update User",
                LocalDate.of(1990, 1, 1),
                Rol.USER
        );
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();
        UserRegistrationRequest createdUser = objectMapper.readValue(response, UserRegistrationRequest.class);

        UserRegistrationRequest updatedUser = new UserRegistrationRequest(
                createdUser.id(),
                "updated@example.com",
                "NewPassword1",
                "Updated User",
                LocalDate.of(1990, 1, 1),
                Rol.ADMIN
        );

        mockMvc.perform(put("/api/users/" + createdUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Crea un usuario para asegurarse de que exista un id y asi borrarlo
        UserRegistrationRequest user = new UserRegistrationRequest(
                null,
                "deleteuser@example.com",
                "Password1",
                "Delete User",
                LocalDate.of(1990, 1, 1),
                Rol.USER
        );
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andReturn().getResponse().getContentAsString();
        UserRegistrationRequest createdUser = objectMapper.readValue(response, UserRegistrationRequest.class);

        mockMvc.perform(delete("/api/users/" + createdUser.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}