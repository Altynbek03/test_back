package kaz.altynbek.pratica.test_task.service;

import kaz.altynbek.pratica.test_task.model.UserDetailsImpl;
import kaz.altynbek.pratica.test_task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                             .map(UserDetailsImpl::new)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
