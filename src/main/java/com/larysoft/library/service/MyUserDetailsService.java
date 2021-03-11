package com.larysoft.library.service;

import com.larysoft.library.models.Register;
import com.larysoft.library.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired//autowiring the login repository so as to get the method in the interface
    private RegisterRepository registerRepository;

    @Override//overriding the loadUserByUsername method
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Register register = registerRepository.getRegisterByUsername(username);

        if(register != null){
            //passing the register object to the constructor of the MyUserDetails class
            return new MyUserDetails(register);
        }else{
            throw new UsernameNotFoundException("Username not found");
        }

    }


}
