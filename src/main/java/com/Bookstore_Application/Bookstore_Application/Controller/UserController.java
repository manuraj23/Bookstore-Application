package com.Bookstore_Application.Bookstore_Application.Controller;

import com.Bookstore_Application.Bookstore_Application.Repository.UserRepository;
import com.Bookstore_Application.Bookstore_Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;




}
