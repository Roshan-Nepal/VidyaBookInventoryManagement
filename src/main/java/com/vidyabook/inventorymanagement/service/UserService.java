package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.dto.login.UserLoginRequestDto;
import com.vidyabook.inventorymanagement.dto.login.UserLoginResponseDto;

public interface UserService {
    UserLoginResponseDto userLogin(String identifier, String password, UserLoginRequestDto userLoginRequestDto);

}
