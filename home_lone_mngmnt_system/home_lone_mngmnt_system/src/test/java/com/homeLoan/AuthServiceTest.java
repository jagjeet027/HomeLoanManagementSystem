package com.homeLoan;

import com.homeLoan.exceptions.InvalidRequestException;
import com.homeLoan.model.Users;
import com.homeLoan.repository.UserRepository;
import com.homeLoan.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegister() {
        Users user = new Users();
        user.setUsername("jaggie");
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        user.setPassword("123");

        when(userRepository.save(any())).thenReturn(user);
        Users saved = authService.register(user);
        assertNotNull(saved);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testLoginSuccess() {
        Users user = new Users();
        user.setUsername("admin");
        user.setPassword("encoded");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123", "encoded"))
                .thenReturn(true);
        Users result = authService.login("admin", "123");
        assertEquals("admin", result.getUsername());
    }

    @Test
    void testLoginInvalidPassword() {
        Users user = new Users();
        user.setPassword("encoded");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrong", "encoded"))
                .thenReturn(false);

        assertThrows(InvalidRequestException.class, () -> {
            authService.login("admin", "wrong");
        });
    }
}