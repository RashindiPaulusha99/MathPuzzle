package com.uob.mathpuzzle.service;


import com.uob.mathpuzzle.dto.PlayerDTO;

public interface UserService {
    PlayerDTO getAdminDetails(String email);
    PlayerDTO savePlayer(PlayerDTO playerDTO);
}
