package com.example.Employee.Detail.Service;

import com.example.Employee.Detail.Model.Employee;
import com.example.Employee.Detail.Model.User;
import com.example.Employee.Detail.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User createnewuser(User user) {

        return userRepository.save(user);
    }

    public User finduserbyid(String id){
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

}
