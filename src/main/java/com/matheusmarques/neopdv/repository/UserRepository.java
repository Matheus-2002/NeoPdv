package com.matheusmarques.neopdv.repository;

import com.matheusmarques.neopdv.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends MongoRepository<User, String> {

    UserDetails findByEmail(String email);
}
