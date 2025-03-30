package com.timesheet.TSbackend.service;

import com.timesheet.TSbackend.model.Users;
import com.timesheet.TSbackend.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService {

    private final UsersRepository userRepository;

    @Autowired
    public UserDataService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user) {
        return userRepository.save(user);
    }


    public String getUserByEmail(String email) {
        Users user = userRepository.findByEmail(email).orElse(null);

        if(user==null)
        {
            return "User Not Found";
        }

        return user.getRole().toString();

    }
}
