package br.com.ewerton.userserviceapi.controller.impl;

import br.com.ewerton.userserviceapi.entity.User;
import br.com.ewerton.userserviceapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.ewerton.userserviceapi.creator.CreatorUtils.generateMock;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    public static final String BASE_URI = "/api/users";
    public static final String VALID_EMAIL = "1235445asder@mail.com";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByIdWithSuccess() throws Exception {
        final var entity = generateMock(User.class);
        final var userId = userRepository.save(entity).getId();

        mockMvc.perform(get(BASE_URI + "/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        userRepository.deleteById(userId);
    }

    @Test
    void testFindByIdWithNotFoundException() throws Exception {
        mockMvc.perform(get(BASE_URI + "/{id}", "123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found 123, Type: UserResponse"))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value("/api/users/123"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testFindAllWithSuccess() throws Exception {
        final var entity1 = generateMock(User.class);
        final var entity2 = generateMock(User.class);

        userRepository.saveAll(List.of(entity1, entity2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty())
                .andExpect(jsonPath("$[1].profiles").isArray());

        userRepository.deleteAll(List.of(entity1, entity2));
    }

    @Test
    void testSaveUserWithSuccess() throws Exception {
        final var validEmail = VALID_EMAIL;
        final var request = generateMock(CreateUserRequest.class).withEmail(validEmail);

        mockMvc.perform(
                post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isCreated());

        userRepository.deleteByEmail(validEmail);
    }

    @Test
    void testSaveUserWithConflict() throws Exception {
        final var validEmail = VALID_EMAIL;
        final var request = generateMock(CreateUserRequest.class).withEmail(validEmail);
        final var entity = generateMock(User.class).withEmail(validEmail);

        userRepository.save(entity);

        mockMvc.perform(
                post(BASE_URI)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(request))
        ).andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value("Email [ " + validEmail + " ] already exists"))
        .andExpect(jsonPath("$.error").value(CONFLICT.getReasonPhrase()))
        .andExpect(jsonPath("$.path").value(BASE_URI))
        .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
        .andExpect(jsonPath("$.timestamp").isNotEmpty());

        userRepository.deleteById(entity.getId());
    }

    private String toJson(final Object object) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (final Exception e) {
            throw new Exception("Error to convert object to json", e);
        }
    }
}