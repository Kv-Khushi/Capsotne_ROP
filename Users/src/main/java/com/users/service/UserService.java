package com.users.service;


import com.users.dtoconversion.DtoConversion;
import com.users.entities.User;
import com.users.exception.AlreadyExists;
import com.users.exception.NotFoundException;
import com.users.indto.LoginRequest;
import com.users.indto.UserRequest;
import com.users.outdto.UserResponse;
import com.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.users.passwordencryption.PasswordEncodingAndDecoding;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    PasswordEncodingAndDecoding passwordEncodingAndDecoding;

    public List<User> getAllUserList(){
        List<User> list=new ArrayList<>();
        list=userRepository.findAll();
        return list;
    }

    public UserResponse addUser(UserRequest userRequest)  {

        User user = DtoConversion.convertUserRequestToUser(userRequest);
        Optional<User> optionalUser=userRepository.findByUserEmail(userRequest.getUserEmail());
        if(optionalUser.isPresent()){
            throw  new AlreadyExists();
        }
        passwordEncodingAndDecoding =new PasswordEncodingAndDecoding();
        user.setUserPassword(passwordEncodingAndDecoding.encodePassword(user.getUserPassword()));
        User savedUser =userRepository.save(user);
        UserResponse userResponse = DtoConversion.userToUserResponse(savedUser);
        return userResponse;
    }

    public boolean deleteUser(Long userId){
        userRepository.deleteById(userId);
        return true;
    }

    public UserResponse authenticateUser(LoginRequest loginRequest) {

        User user = userRepository.findByUserEmail(loginRequest.getUserEmail()).orElseThrow(() -> new NotFoundException(loginRequest.getUserEmail()));
        passwordEncodingAndDecoding =new PasswordEncodingAndDecoding();

        UserResponse userResponse = DtoConversion.userToUserResponse(user);
        return userResponse;
    }


}
