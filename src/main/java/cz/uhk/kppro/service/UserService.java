package cz.uhk.kppro.service;

import cz.uhk.kppro.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    void save(User user);
}
