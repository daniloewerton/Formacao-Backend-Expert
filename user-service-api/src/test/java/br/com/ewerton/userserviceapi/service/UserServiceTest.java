package br.com.ewerton.userserviceapi.service;

import br.com.ewerton.userserviceapi.entity.User;
import br.com.ewerton.userserviceapi.mapper.UserMapper;
import br.com.ewerton.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.ewerton.userserviceapi.creator.CreatorUtils.generateMock;
import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse() {

        when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(generateMock(UserResponse.class));

        final var response = service.findById("1");

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());

        verify(repository).findById(anyString());
        verify(mapper).fromEntity(any(User.class));
    }

    @Test
    void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.findById("1");
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found 1, Type: UserResponse", e.getMessage());
        }

        verify(repository).findById(anyString());
    }

    @Test
    void whenCallFindAllThenReturnListOfUserResponse() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));

        final var response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.getFirst().getClass());

        verify(repository).findAll();
        verify(mapper, times(2)).fromEntity(any(User.class));
    }

    @Test
    void whenCallSaveThenSuccess() {
        final var request = generateMock(CreateUserRequest.class);

        when(mapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any(User.class))).thenReturn(new User());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        service.save(request);

        verify(mapper).fromRequest(request);
        verify(encoder).encode(request.password());
        verify(repository).save(any(User.class));
        verify(repository).findByEmail(request.email());
    }

    @Test
    void whenCallSaveWithInvalidEmailThenThrowDataIntegrityViolationException() {
        final var request = generateMock(CreateUserRequest.class);
        final var entity = generateMock(User.class);

        when(repository.findByEmail(anyString())).thenReturn((Optional.of(entity)));

        try {
            service.save(request);
        } catch (Exception e) {
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Email [ " + request.email() + " ] already exists", e.getMessage());
        }

        verify(repository).findByEmail(request.email());
        verify(mapper, times(0)).fromRequest(request);
    }
}