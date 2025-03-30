package com.Bookstore_Application.Bookstore_Application.Controller;

import com.Bookstore_Application.Bookstore_Application.Entity.User;
import com.Bookstore_Application.Bookstore_Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PutMapping("/UpdateUser/username/{username}")
    public ResponseEntity<?>updateUser(@RequestBody User user, @PathVariable String username){
        try{
            User userInDb =userService.findByUsername(username);
            if (userInDb != null) {
                userInDb.setEmail(user.getEmail());
                userInDb.setPassword(user.getPassword());
                userInDb.setUsername(user.getUsername());
                userService.saveNewUser(userInDb);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/DeleteUser/username/{username}")
    public ResponseEntity<?>deleteUser(@PathVariable String username){
        try{
            User userInDb =userService.findByUsername(username);
            if (userInDb != null) {
                userService.deleteUser(userInDb);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
