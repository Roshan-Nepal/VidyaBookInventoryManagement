package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.dto.login.UserLoginRequestDto;
import com.vidyabook.inventorymanagement.dto.login.UserLoginResponseDto;
import com.vidyabook.inventorymanagement.entity.User;

public interface UserService {
    UserLoginResponseDto userLogin(String identifier, String password, UserLoginRequestDto userLoginRequestDto);
    void saveUser(User user);

}
