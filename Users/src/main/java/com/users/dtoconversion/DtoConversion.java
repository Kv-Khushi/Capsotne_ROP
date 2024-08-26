package com.users.dtoconversion;

import com.users.entities.Address;
import com.users.entities.User;
import com.users.indto.AddressRequest;
import com.users.indto.UserRequest;
import com.users.outdto.AddressResponse;
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
            userResponse.setUserPassword(user.getUserPassword());
            userResponse.setWallet(user.getWallet());
            return userResponse;
        }

        public static Address convertAddressRequestToAddress(AddressRequest addressRequest) {
            Address address = new Address();
            address.setStreet(addressRequest.getStreet());
            address.setCity(addressRequest.getCity());
            address.setState(addressRequest.getState());
            address.setZipCode(addressRequest.getZipCode());
            address.setCountry(addressRequest.getCountry());
            address.setUserId(addressRequest.getUserId());
            return address;
        }

        public static AddressResponse addressToAddressResponse(Address address){
            AddressResponse addressResponse= new AddressResponse();
            addressResponse.setStreet(address.getStreet());
            addressResponse.setCity(address.getCity());
            addressResponse.setState(address.getState());
            addressResponse.setZipCode(address.getZipCode());
            addressResponse.setCountry(address.getCountry());
            addressResponse.setUserId(address.getUserId());
            return  addressResponse;
        }
    }
