package com.elm.task.user;

import com.elm.task.privilege.Privilege;
import com.elm.task.privilege.PrivilegeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private PrivilegeRepository privilegeRepository;

    private PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;

    }


    public Optional<User> basicLogin(String username, String password) {
        LOGGER.info("New user attempting to sign in");

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                return user;

            } catch (AuthenticationException e){
                LOGGER.info("Log in failed for user {}", username);

            }
        }
        return Optional.empty();
    }

    /**
     * Create a new user in the database.
     *
     * @param username username
     * @param password password

     * @return Optional of user, empty if the user already exists.
     */
    public Optional<User> create(String username, String password, String email, String phone) {
        LOGGER.info("New user attempting to sign in");
        Optional<User> user = Optional.empty();
        if (!userRepository.findByUsername(username).isPresent()) {
            Optional<Privilege> privilege = privilegeRepository.findByPrivilege("USERS");
            user = Optional.of(userRepository.saveAndFlush(User.builder().username(username)
                    .password(passwordEncoder.encode(password))
                    .privileges(privilege.isPresent()? List.of(privilege.get()): Collections.emptyList())
                    .phone(phone).email(email).build()))
            ;
        }
        return user;
    }



    public Optional<User> currentUser(){
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser!=null) {
            Optional<User> user =   userRepository.findByUsername(currentUser.getUsername());
            return user;

        }else
            return  Optional.empty();
    }
}
