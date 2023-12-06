package com.uob.mathpuzzle.dto;

import com.uob.mathpuzzle.entity.Score;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PlayerDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String image;

}
