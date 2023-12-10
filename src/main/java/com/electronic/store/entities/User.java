package com.electronic.store.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId; // Changed the type from Long to String

    @Column(name = "User_Name")
    private String name;

    @Column(name = "User_Email", unique = true)
    private String email;

    @Column(name = "User_Password")
    private String password;

    private String gender;

    @Column(name = "About", length = 1000)
    private String about;

    @Column(name = "User_Image_Name")
    private String imageName;
}
