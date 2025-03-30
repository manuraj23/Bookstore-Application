package com.Bookstore_Application.Bookstore_Application.Service;

import com.Bookstore_Application.Bookstore_Application.Entity.Books;
import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Repository.BooksRepsitory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BookEntryService {
    @Autowired
    public BooksRepsitory booksRepsitory;

    @Autowired
    public UserService userService;

    public List<Books> getAllBooks() {
        return booksRepsitory.findAll();
    }

    @Transactional
    public void createBook(String email, Books book) {
        try {
            User user= userService.findByEmail(email);
//            User user = userService.findByUsername(username);
            Books saved = booksRepsitory.save(book);
            user.getBooks().add(saved);
            userService.saveUser(user);
        }catch (Exception e) {
                throw new RuntimeException("Error saving journal entry", e);
            }
    }

    public Optional<Books> getBookById(ObjectId bookId) {
        return booksRepsitory.findById(bookId);
    }

    @Transactional
    public boolean deleteBook(ObjectId bookId, String email) {
        boolean removed = false;
        try {
            User user = userService.findByEmail(email);
            removed = user.getBooks().removeIf(x -> x.getId().equals(bookId));
            if (removed) {
                userService.saveUser(user);
                booksRepsitory.deleteById(bookId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting book", e);
        }
        return removed;
    }


}
