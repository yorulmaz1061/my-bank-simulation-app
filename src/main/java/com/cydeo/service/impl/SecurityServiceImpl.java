package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {
    //Normally we shouldn't inject repository but it is exceptional usage.
    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // We need to get our own user from dataBase
        //Return some exception if user doesn't exist.
        // Return user information as a UserDetails
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("This user does not exist"));
    /*    if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }*/

        return new UserPrincipal(user);
    }
}
