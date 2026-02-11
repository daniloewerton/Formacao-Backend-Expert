package br.com.ewerton.authserviceapi.services;

import br.com.ewerton.authserviceapi.models.User;
import br.com.ewerton.authserviceapi.repositories.UserRepository;
import br.com.ewerton.authserviceapi.security.dtos.UserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User entity = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
        return UserDetailsDTO.builder()
                .id(entity.getId())
                .username(entity.getName())
                .name(entity.getName())
                .password(entity.getPassword())
                .authorities(entity.getProfiles().stream()
                        .map(x -> new SimpleGrantedAuthority(x.getDescription()))
                        .collect(Collectors.toSet()))
                .build();
    }
}
