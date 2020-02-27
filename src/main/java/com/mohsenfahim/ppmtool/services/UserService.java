package com.mohsenfahim.ppmtool.services;

import com.mohsenfahim.ppmtool.domain.User;
import com.mohsenfahim.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.mohsenfahim.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){

        try{

            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique
            newUser.setUsername(newUser.getUsername());
            //Make sure that password and confirm password match
            //we don't persist or show the confirm password
            newUser.setConfirmPassword(""); //This will clear the confirmPassword filed so its not in clear text on the response JSON
            return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists!");
        }
    }


}
