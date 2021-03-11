package com.larysoft.library.controllers;

import com.larysoft.library.JWT.JsonWebToken;
import com.larysoft.library.custom.AuthenticationRequest;
import com.larysoft.library.models.Register;
import com.larysoft.library.repository.RegisterRepository;
import com.larysoft.library.service.MyUserDetailsService;
import com.larysoft.library.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController {

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    JsonWebToken jsonWebToken;

    @Autowired
    RegisterRepository registerRepository;

    /**
     * method used for login
     */
    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)//@PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        //checking if username or password is null
        if (!authenticationRequest.getUsername().isEmpty() || !authenticationRequest.getPassword().isEmpty()) {
            try {

                //using method in the login repository to get other users credentials
                Register register = registerRepository.getRegisterByUsername(authenticationRequest.getUsername().toLowerCase());

                //loading user credentials by users inputted username
                final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername().toLowerCase());

                //instantiating the BCryptPasswordEncoder class
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

                //comparing the user inputted password and the hashed password in the db
                boolean isMatches = passwordEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword());

                if (isMatches && userDetails.getUsername().equals(authenticationRequest.getUsername().toLowerCase())) {

                    //using the userDetails to generate the JSon Web Token
                    final String JWT = jsonWebToken.generateToken(userDetails);

                    Map<String, Object> response = new HashMap<>();
                    response.put("userId", register.getUserId());
                    response.put("JWT", JWT);
                    response.put("username", register.getUsername());
                    response.put("emailAddress", register.getEmailAddress());


                    //building custom success response
                    return responseHandler.generateResponse(HttpStatus.OK, true, "Login Successfully", response);
                } else {
                    //building custom error response
                    return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Invalid Username or Password", new HashMap<>());
                }
            } catch (Exception e) {
                //building custom error response
                return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Username doesn't exist.", new HashMap<>());
            }
        } else {
            //building custom error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Username or password cannot be null.", new HashMap<>());
        }
    }

    /**
     * method used for creating a user account
     */
    @RequestMapping(value = "/api/v1/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody Register register) {

        try {

            //instantiating the BCryptPasswordEncoder class
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            //encoding user's password
            register.setPassword(passwordEncoder.encode(register.getPassword()));

            Register registerResponse = registerRepository.saveAndFlush(register);

            //building custom error response
            return responseHandler.generateResponse(HttpStatus.OK, true, "User's data saved successfully.", registerResponse);

        } catch (Exception e) {
            //building custom error response
            return responseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, "Unable to save user's data.", null);
        }

    }
}
