package com.uob.mathpuzzle.service;


import com.uob.mathpuzzle.dto.UserDTO;

public interface UserService {
    UserDTO getAdminDetails(String email);
    UserDTO saveUser(UserDTO userDTO);
}
