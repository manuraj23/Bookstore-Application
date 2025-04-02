package com.Bookstore_Application.Bookstore_Application.Service;

import com.Bookstore_Application.Bookstore_Application.Entity.Books;
import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Repository.BooksRepsitory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookEntryService {
    @Autowired
    public BooksRepsitory booksRepsitory;

    @Autowired
    public UserService userService;

    public Page<Books> getAllBooks(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return booksRepsitory.findAll(pageable);
    }

    public List<Books> getAllBooks1() {
        return booksRepsitory.findAll();
    }

    public List<Books> searchBooksByAuthor(String author) {
        List<Books> allBooks = getAllBooks1();
        return allBooks.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
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


    public List<Books> searchBooksByTitle(String title) {
        List<Books> allBooks = getAllBooks1();
        return allBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Books> searchBooksByCategory(String category) {
        List<Books> allBooks = getAllBooks1();
        return allBooks.stream()
                .filter(book -> book.getCategory() != null && book.getCategory().stream()
                        .anyMatch(cat -> cat.equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }

    public List<Books> searchBooksByRating(double rating, String condition) {
        List<Books> allBooks = getAllBooks1();
        return allBooks.stream()
                .filter(book -> {
                    if ("greater".equalsIgnoreCase(condition)) {
                        return book.getRating() > rating;
                    } else if ("equal".equalsIgnoreCase(condition)) {
                        return book.getRating() == rating;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
