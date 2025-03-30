package com.Bookstore_Application.Bookstore_Application.Repository;

import com.Bookstore_Application.Bookstore_Application.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
    User findByEmail(String email);
}
