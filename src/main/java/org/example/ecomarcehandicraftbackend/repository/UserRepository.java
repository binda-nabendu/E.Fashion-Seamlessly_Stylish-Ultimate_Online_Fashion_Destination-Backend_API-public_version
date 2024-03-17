package org.example.ecomarcehandicraftbackend.repository;

import org.example.ecomarcehandicraftbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
