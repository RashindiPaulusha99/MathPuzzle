package com.uob.mathpuzzle.controller;

import com.uob.mathpuzzle.dto.CommonResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.uob.mathpuzzle.constant.OAuth2Constant.HEADER_AUTH;

@RestController
@RequestMapping("/v1/admin")
@Log4j2
@RequiredArgsConstructor
public class UserController {

//    @Autowired
//    private AdminService userService;

    // reset password
    /*@PutMapping(path = "/password/reset",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPasswordOfAdmin(
            @RequestHeader(value = HEADER_AUTH, required = true) String token
            *//*@RequestBody PasswordDTO passwordDTO*//*
    ){

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonResponseDTO<>(false, "Bearer token is required", null));
        }
        return null;

       *//* log.info("REST request to reset password "+"password :"+passwordDTO);

        if (passwordDTO.getEmail() == null || passwordDTO.getNewPassword() == null || passwordDTO.getCurrentPassword() == null){
            return new ResponseEntity(new CommonResponseDTO<>(false, "Invalid Data", null), HttpStatus.BAD_REQUEST);
        }else {
            userService.resetPassword(token,passwordDTO);
            return ResponseEntity.ok(new CommonResponseDTO<>(true, "Password has been updated successfully..!"));
        }*//*
    }

    // save banner
    @PostMapping(path = "/banner/save",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveBanner(
            @RequestHeader(value = HEADER_AUTH, required = true) String token,
            @RequestPart("image") MultipartFile file
            *//*@ModelAttribute BannerDTO banner*//*
    ){

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonResponseDTO<>(false, "Bearer token is required", null));
        }

//        log.info("REST request to save new banner "+"banner :"+banner+"file : "+file);

        return null;
    }

    // update banner
    @PutMapping(path = "/banner/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateBanner(
            @RequestHeader(value = HEADER_AUTH, required = true) String token,
            @RequestPart("image") MultipartFile file
//            @ModelAttribute BannerDTO banner
    ){

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonResponseDTO<>(false, "Bearer token is required", null));
        }

//        log.info("REST request to update banner "+"banner :"+banner+"file : "+file);
        return  null;
    }

    // delete banner
    @DeleteMapping(path = "/banner/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteBanner(
            @RequestHeader(value = HEADER_AUTH, required = true) String token,
            @PathVariable("id") Long id,
            @RequestParam("type") String type
    ){

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonResponseDTO<>(false, "Bearer token is required", null));
        }

        log.info("REST request to delete banner "+"id :"+id);
        return null;
//        boolean result = bannerService.deleteBanner(token, id);
//        return new ResponseEntity(new CommonResponseDTO<>(true, "Banner has been deleted successfully",result), HttpStatus.OK);
    }
*/

}
