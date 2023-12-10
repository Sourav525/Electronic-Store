package com.electronic.store.controllers;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.ImageResponse;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.service.FileService;
import com.electronic.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return  new ResponseEntity<>(userDto1, HttpStatus.CREATED);

    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid
            @PathVariable("userId") String userId,
            @RequestBody UserDto userDto
    ){
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId){
        ApiResponseMessage message = ApiResponseMessage.builder().message("user is deleted successfully").success(true).status(HttpStatus.OK).build();
        userService.deleteUser(userId);
        return new ResponseEntity<>(message,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc", required = false) String sortDir
    ){
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable  String userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> getUserByNameContaining(@PathVariable String keywords){
        return  new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }

    //upload userimage
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable String userId,@RequestParam("userImage") MultipartFile image) throws IOException {

        String imageName = fileService.updloadFile(image, imageUploadPath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        userService.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);


    }
    //serve user image
    public void serve




}
