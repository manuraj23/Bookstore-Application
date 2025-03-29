package com.Bookstore_Application.Bookstore_Application.Repository;

import com.Bookstore_Application.Bookstore_Application.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepsitory extends MongoRepository<Users, ObjectId> {
}
