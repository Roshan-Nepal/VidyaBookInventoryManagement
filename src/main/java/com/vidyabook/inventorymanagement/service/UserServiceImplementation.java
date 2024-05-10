package com.vidyabook.inventorymanagement.service;

import com.vidyabook.inventorymanagement.dto.login.UserLoginRequestDto;
import com.vidyabook.inventorymanagement.dto.login.UserLoginResponseDto;
import com.vidyabook.inventorymanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserLoginResponseDto userLogin(String identifier, String password, UserLoginRequestDto userLoginRequestDto) {
        boolean loginSuccess = userRepository.userLogin(identifier, password).isPresent();
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        if (loginSuccess) {
            userLoginResponseDto.setUserName(userRepository.findByIdentifier(identifier));
            return userLoginResponseDto;
        }
        userLoginResponseDto.setUserName("");
        return userLoginResponseDto;
    }
}
