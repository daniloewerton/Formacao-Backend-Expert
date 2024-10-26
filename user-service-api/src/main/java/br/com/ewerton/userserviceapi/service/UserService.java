package br.com.ewerton.userserviceapi.service;

import br.com.ewerton.userserviceapi.mapper.UserMapper;
import br.com.ewerton.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity
                (userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                        "Object not found " + id + ", Type: " + UserResponse.class.getSimpleName()
                )));
    }

    public void save(final CreateUserRequest createUserRequest) {
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }
}
