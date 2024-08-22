package com.users.dtoconversion;

import com.users.entities.User;
import com.users.indto.UserRequest;
import com.users.outdto.UserResponse;


    public class DtoConversion {
        public static User convertUserRequestToUser(UserRequest userRequest) {
            User user = new User();
            user.setUserName(userRequest.getUserName());
            user.setUserPassword(userRequest.getUserPassword());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setUserEmail(userRequest.getUserEmail());
            user.setUserRole(userRequest.getUserRole());
            return user;
        }

        public static UserResponse userToUserResponse(User user) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getUserId());
            userResponse.setUserEmail(user.getUserEmail());
            userResponse.setUserName(user.getUserName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            return userResponse;
        }
    }
