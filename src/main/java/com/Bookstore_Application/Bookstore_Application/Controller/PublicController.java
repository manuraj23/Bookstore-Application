package com.Bookstore_Application.Bookstore_Application.Controller;


import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Service.UserDetailServiceImpl;
import com.Bookstore_Application.Bookstore_Application.Service.UserService;
import com.Bookstore_Application.Bookstore_Application.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public void signUp(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> logIn(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
            System.out.println(user.getEmail());
            String token = jwtUtils.generateToken(userDetails.getUsername());
            System.out.println(token);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }


    }
    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "I am alive! from public";
    }
}

