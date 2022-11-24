package com.example.habitstracker.services;

import java.util.Optional;

import com.example.habitstracker.models.UserPassword;
import com.example.habitstracker.repository.UserPasswordRepository;
import com.example.openapi.dto.ChangePasswordDTO;
import com.example.openapi.dto.UserWithoutPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.habitstracker.exceptions.UserExistException;
import com.example.habitstracker.exceptions.UserNotFoundException;
import com.example.habitstracker.models.HabitList;
import com.example.habitstracker.models.UserEntity;
import com.example.habitstracker.repository.UserRepository;
import com.example.openapi.dto.UserDTO;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;
    private final HabitListService habitListService;
    private final MapperService mapperService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserPasswordRepository userPasswordRepository, HabitListService habitListService, MapperService mapperService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userPasswordRepository = userPasswordRepository;
        this.habitListService = habitListService;
        this.mapperService = mapperService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity addUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new UserExistException(userDTO.getUsername());

        UserEntity user = new UserEntity();
        mapperService.transform(userDTO, user);
        habitListService.addHabitList(user.getHabitList());        
        userRepository.save(user);
        return user;
    }

    public UserEntity getByUsername(String username){
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(username);
        return userOpt.get();
    }

    public UserEntity getById(long id){
        Optional<UserEntity> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty())
            throw new UserNotFoundException(id);
        return userOpt.get();
    }

    public void deleteByUsername(String username){
        UserEntity user = getByUsername(username);
        userRepository.delete(user);
    }

    @Deprecated
    public void updateUserByUsername(String username, UserDTO userDTO) {
        UserEntity user = getByUsername(username);
        var userDTOName = userDTO.getUsername();
        var userDTOPassword = userDTO.getPassword();

        if (userDTOName != null && !username.equals(userDTOName)) {
            Optional<UserEntity> userOpt = userRepository.findByUsername(userDTOName);
            if (userOpt.isPresent())
                throw new UserExistException(userDTO.getUsername());
            user.setUsername(userDTOName);
        }

        if (userDTOPassword != null)
            user.setPassword(userDTOPassword);

        userRepository.save(user);
    }

    public void updateUserById(long id, UserWithoutPasswordDTO newUser) {
        UserEntity user = getById(id);
        user.setUsername(newUser.getUsername());
        user.getHabitList().setName(newUser.getHabitListName());

        userRepository.save(user);
    }

    public void updatePassword(long id, ChangePasswordDTO userDto) {
        UserEntity user = getById(id);

//        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

//        var isEquals = user.getPassword().equals(encodedPassword);

        var isEquals = passwordEncoder.matches(userDto.getOldPassword(), user.getPassword());

        if(isEquals) {
            UserPassword userPassword = new UserPassword(user.getUserId(), userDto.getNewPassword());
            userPasswordRepository.save(userPassword);
//            userRepository.save(user);
        }
    }

    public HabitList getUserHabitList(UserDTO userDTO){
        UserEntity user = getByUsername(userDTO.getUsername());
        return user.getHabitList();
    }
}
