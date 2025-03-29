package com.Bookstore_Application.Bookstore_Application.Controller;


import com.Bookstore_Application.Bookstore_Application.Entity.Books;
import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Service.BookEntryService;
import com.Bookstore_Application.Bookstore_Application.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<Books>books=bookEntryService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/createBook/username/{username}")
    public ResponseEntity<?> createBook(@PathVariable String username, @RequestBody Books book) {
        try {
            bookEntryService.createBook(username, book);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(book, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateBook/user/{username}/bookId/{bookId}")
    public ResponseEntity<?> updateBook(@PathVariable String username, @PathVariable ObjectId bookId, @RequestBody Books newBook) {
        try {
            User user = userService.findByUsername(username);
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
                    bookEntryService.createBook(username, old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteBook/user/{username}/bookId/{bookId}")
    public ResponseEntity<?>deleteBook(@PathVariable String username,@PathVariable ObjectId bookId){
        try {
            boolean removed = bookEntryService.deleteBook(bookId, username);
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
