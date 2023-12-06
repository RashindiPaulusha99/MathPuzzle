package com.uob.mathpuzzle.service;


import com.uob.mathpuzzle.dto.PlayerDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    PlayerDTO getAdminDetails(String email);
    PlayerDTO savePlayer(PlayerDTO playerDTO);
    PlayerDTO getPlayer(String token);
    PlayerDTO saveProfileImage(String token, MultipartFile image);
}
