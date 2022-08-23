package com.agoda.clone.agoda.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import com.agoda.clone.agoda.model.User;
import com.agoda.clone.agoda.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        User user = userOptional.orElseThrow(()-> new UsernameNotFoundException("No user"));
        return new org.springframework.security.core.userdetails
            .User(user.getEmail(), user.getPassword(), user.isVerification(), true, true,true, getAuthorities("USER"));
    }

    @Transactional
    public UserDetails loadUserById(int id) throws UsernameNotFoundException {
        User user = userRepository.getById(id);
        return new org.springframework.security.core.userdetails
            .User(user.getEmail(), user.getPassword(), user.isVerification(), true, true,true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities (String role){
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
