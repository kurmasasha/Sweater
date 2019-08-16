package ru.kurma.sweater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kurma.sweater.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
