package com.wandico.wandico.service;

import com.wandico.wandico.entity.Role;
import com.wandico.wandico.entity.UserDetails;
import com.wandico.wandico.repo.LoginRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoginService implements UserDetailsService {
    private LoginRepo loginRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(LoginRepo loginRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.loginRepo = loginRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDetails getUserDetails(String email) {
        return loginRepo.findByUsername(email);

    }

    @Override
    @Transactional(readOnly = true)
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        UserDetails userDetails = loginRepo.findByUsername(username);
        if(null == userDetails) throw new UsernameNotFoundException(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
       /* for (Role role : userDetails.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }*/

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(), userDetails.getPassword(), grantedAuthorities);

    }

    public void saveUserDetails(UserDetails userDetails) {

        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        userDetails.setPasswordConfirm(bCryptPasswordEncoder.encode(userDetails.getPasswordConfirm()));
        loginRepo.save(userDetails);

    }

    public UserDetails findByUsername(String username) {
        return loginRepo.findByUsername(username);
    }

    public List<UserDetails> getUserDetailsList() {
        return loginRepo.findAll();

    }

}
