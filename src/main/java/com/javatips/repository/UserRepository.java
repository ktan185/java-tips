package com.javatips.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javatips.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
}
