package com.Bookstore_Application.Bookstore_Application.Service;

import com.Bookstore_Application.Bookstore_Application.Entity.Books;
import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Repository.BooksRepsitory;
import com.Bookstore_Application.Bookstore_Application.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BooksRepsitory booksRepsitory;

    private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteUser(User userInDb) {
        List<ObjectId> bookIds = userInDb.getBooks().stream().map(Books::getId).toList();
        userRepository.delete(userInDb);
        bookIds.forEach(bookId -> booksRepsitory.deleteById(bookId));
//        System.out.println(bookIds);
    }
}
