package com.Bookstore_Application.Bookstore_Application.Controller;

import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

//    @GetMapping("getAllUsers")
//    public ResponseEntity<?> getAllUser(){
//        List<User> all=userService.getAll();
//        if(all!=null && !all.isEmpty()){
//            return new ResponseEntity<>(all, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("getAllBooks")
    public ResponseEntity<?>getAllBoooks(){
        return null;
    }

}
