package com.timesheet.TSbackend.controller;


import com.timesheet.TSbackend.DTO.UserSigninEmail;
import com.timesheet.TSbackend.model.Users;
import com.timesheet.TSbackend.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserDataController {

 private final UserDataService userDataService;

    @Autowired
    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/create")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        // The JSON payload is automatically mapped to a User object by @RequestBody.
        Users createdUser = userDataService.createUser(user);
        // Return the created user along with a 201 CREATED HTTP status
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @PostMapping("/get-role")
    public ResponseEntity<Map<String,String>> verifyUser(@RequestBody UserSigninEmail userEmail)
    {
        String role = userDataService.getUserByEmail(userEmail.getEmail());
        Map<String,String> response = new HashMap<>();
        response.put("role",role);

        return ResponseEntity.ok(response);

    }

}
