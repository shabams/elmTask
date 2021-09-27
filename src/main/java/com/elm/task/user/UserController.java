package com.elm.task.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;

@RestController
@RequestMapping
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;



    @GetMapping(path="/login",params = {"username","password"})
    public void basiclogin(@RequestParam String username, @RequestParam String password) {
         userService.basicLogin(username, password).orElseThrow(()->
                new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Login Failed"));
    }


    @PostMapping("/user")
    public User signup(@RequestBody @Valid UserDto loginDto){
        User user = userService.create(loginDto.getUsername(), loginDto.getPassword(), loginDto.getEmail(),
                loginDto.getPhone()).orElseThrow(() -> new HttpServerErrorException(HttpStatus.BAD_REQUEST,"User already exists"));
        return user;
    }



//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<User> getAllUsers() {
//        return userService.getAll();
//    }

}