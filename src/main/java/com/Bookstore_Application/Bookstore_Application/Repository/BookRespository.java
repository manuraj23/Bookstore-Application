package com.Bookstore_Application.Bookstore_Application.Repository;

import com.Bookstore_Application.Bookstore_Application.Entity.Books;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRespository extends MongoRepository<Books, ObjectId> {
}
