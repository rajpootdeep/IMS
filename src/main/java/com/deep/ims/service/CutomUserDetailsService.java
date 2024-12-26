package com.deep.ims.service;

import com.deep.ims.entity.User;
import com.deep.ims.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CutomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public CutomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findById(Long.parseLong(username)).orElseThrow(() -> new UsernameNotFoundException("User details not found for the user Id : "+username));
        List<GrantedAuthority> authorities=List.of(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()),user.getPassword(),authorities);
    }
}

