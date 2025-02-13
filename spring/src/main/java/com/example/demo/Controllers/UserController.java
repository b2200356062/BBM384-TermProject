package com.example.demo.Controllers;

import com.example.demo.DTO.PasswordChangeDTO;
import com.example.demo.DTO.RegisterReq;
import com.example.demo.Entity.Users;
import com.example.demo.Service.AuthService;
import com.example.demo.Service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", maxAge = 3600)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Users> getUserProfile(@RequestHeader("Authorization") String token){
        Users user = userService.getCurrentUser(token.substring(7)); // authorization - bearer
        System.out.println("controller: "+user );
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/update")
    public ResponseEntity<Users> updateUserProfile(@RequestHeader("Authorization") String token, @RequestBody Users user){
        Users updated_user = userService.updateUserProfile(token, user);
        return new ResponseEntity<>(updated_user, HttpStatus.OK);
    }

    @PutMapping("/user/interestedCategory")
    public ResponseEntity<Long> addInterestedCategory(@RequestHeader("Authorization") String token, @RequestBody Long categoryId) {
        userService.addInterestedCategory(token, categoryId);
        return new ResponseEntity<Long>(HttpStatus.OK);
    }

    @PutMapping("/forgotpassword")
    public ResponseEntity<?> checkUser(@RequestBody PasswordChangeDTO values){
        ResponseEntity<?> user = userService.updatePassword(values);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // transactional ekle calısmazsa
    @Transactional
    @DeleteMapping("/deleteuser")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token){
        userService.deleteUser(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
