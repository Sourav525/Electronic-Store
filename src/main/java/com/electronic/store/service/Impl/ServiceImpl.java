package com.electronic.store.service.Impl;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entities.User;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.UserRepository;
import com.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {
        // Generate a random UUID as a String
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId); // Set the generated userId in the DTO

        // DTO to Entity
        User user = dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        // Entity to Dto
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }



    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        user.setName(userDto.getName());
        //email update
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }


    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("The given id is not found"));
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        //pageNumber default starts from 0
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);


        return response;
    }





    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with given id !!"));
        return entityToDto(user);
    }



    @Override
    public UserDto getUserByEmail(String email) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            return entityToDto(user);
//        } else {
//            // Return a default UserDto or an empty response
//            // If you have a default constructor in UserDto, you can use it to create an empty instance
//            // Otherwise, you can set the fields to their default values as needed
//            return new UserDto(); // This creates a new empty UserDto object
//            // Alternatively, you can return null or throw a custom exception based on your use case.
//        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with th eprovided email"));
        return entityToDto(user);
    }





    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .gender(savedUser.getGender())
//                .about(savedUser.getAbout())
//                .imageName(savedUser.getImageName()).build();

        return  mapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder().
//                userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .gender(userDto.getGender())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName()).build();
        return mapper.map(userDto, User.class);
    }




}
