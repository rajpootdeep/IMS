package com.deep.ims.service;

import com.deep.ims.entity.User;
import com.deep.ims.exception.ResourceNotFoundException;
import com.deep.ims.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.REPEATABLE_READ, rollbackFor = {Exception.class})
   public ResponseEntity<String> saveUser(User user){
        user.setRole("User");
        String bcryptPass=passwordEncoder.encode(user.getPassword());
        user.setPassword(bcryptPass);
        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User"+user.getFirstName() +" is created with ID : "+user.getId());
    }

    public ResponseEntity<User> getUserById(Long id) throws ResourceNotFoundException {
        log.info("getUserById method invoked");
       Optional<User> user= userRepo.findById(id);
       if(user.isPresent()) {
           log.info("getUserById method completed");
           return new ResponseEntity<>(user.get(),HttpStatus.OK);
       }
       throw new ResourceNotFoundException("Resource not found with user id : "+id);

    }




}
