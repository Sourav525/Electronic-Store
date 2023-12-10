package com.electronic.store.dtos;


import com.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;

    @Size(min=3,max=25, message="Invalid Name!!")
    private String name;


    //@Email( message="Invalid email!!")
    @Pattern(regexp = "^[a-z8-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="Invalid email!!")
    @NotBlank(message = "email is required")
    private String email;


    @NotBlank(message = "password is required!!!")
    private String password;

    @Size(min=3,max=9, message = "Invalid gender")
    private String gender;


    @NotBlank(message = "Write something about yourself")
    private String about;

    @ImageNameValid
    private String imageName;
}
