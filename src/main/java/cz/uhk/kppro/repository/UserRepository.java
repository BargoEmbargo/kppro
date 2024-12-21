package cz.uhk.kppro.repository;

import cz.uhk.kppro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

