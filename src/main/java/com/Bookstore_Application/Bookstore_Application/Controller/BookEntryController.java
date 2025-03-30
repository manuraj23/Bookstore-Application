package com.Bookstore_Application.Bookstore_Application.Controller;


import com.Bookstore_Application.Bookstore_Application.Entity.Books;
import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Service.BookEntryService;
import com.Bookstore_Application.Bookstore_Application.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookEntryController {

    @Autowired
    private BookEntryService bookEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/getAllBooks")
    public ResponseEntity<?>getAllBooks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        List<Books>books=bookEntryService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/createBook")
    public ResponseEntity<?> createBook(@RequestBody Books book) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            bookEntryService.createBook(email, book);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateBook/bookId/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable ObjectId bookId, @RequestBody Books newBook) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
//            User user = userService.findByUsername(username);
            User user = userService.findByEmail(email);
            List<Books> books = user.getBooks().stream().filter(x -> x.getId().equals(bookId)).toList();
            if (!books.isEmpty()) {
                Optional<Books> oldBook = bookEntryService.getBookById(bookId);
                if (oldBook.isPresent()) {
                    Books old = oldBook.get();
                    old.setTitle(!newBook.getTitle().isEmpty() ? newBook.getTitle() : old.getTitle());
                    old.setAuthor(!newBook.getAuthor().isEmpty() ? newBook.getAuthor() : old.getAuthor());
                    old.setCategory(newBook.getCategory() != null ? newBook.getCategory() : old.getCategory());
                    old.setPrice(newBook.getPrice() != 0.0 ? newBook.getPrice() : old.getPrice());
                    old.setRating(newBook.getRating() != 0.0 ? newBook.getRating() : old.getRating());
                    old.setPublishedDate(newBook.getPublishedDate() != null && newBook.getPublishedDate().equals(new Date(0)) ? newBook.getPublishedDate() : old.getPublishedDate());
                    bookEntryService.createBook(email, old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteBook/bookId/{bookId}")
    public ResponseEntity<?>deleteBook(@PathVariable ObjectId bookId){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            boolean removed = bookEntryService.deleteBook(bookId, email);
            if (removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("healthCheck")
    public String healthCheck() {
        return "I am alive! from booksEntryController";
    }

}
