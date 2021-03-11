package com.larysoft.library.controllers;

import com.larysoft.library.JWT.JsonWebToken;
import com.larysoft.library.models.Books;
import com.larysoft.library.models.Lend;
import com.larysoft.library.models.Register;
import com.larysoft.library.repository.LendRepository;
import com.larysoft.library.repository.RegisterRepository;
import com.larysoft.library.service.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lend")
public class LendController {

    @Autowired
    ResponseHandler responseHandler;

    @Autowired
    LendRepository lendRepository;

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    JsonWebToken jsonWebToken;

    @PostMapping
    public ResponseEntity<?> lendBook(@RequestParam("bookId")Long bookId, @RequestHeader("Authorization") String JWT){
        //extracting username from JWT
        String username = jsonWebToken.extractUsername(JWT);

        Long userId = registerRepository.getRegisterByUsername(username).getUserId();

        try{
            //passing data through the constructor
            Lend lend = new Lend(new Books(bookId), new Register(userId));

            Lend lendResponse = lendRepository.saveAndFlush(lend);

            return responseHandler.generateResponse(HttpStatus.OK, true, "success.", lendRepository.getOne(lendResponse.getLendId()));

        }catch (Exception e){
            return responseHandler.generateResponse(HttpStatus.FORBIDDEN, false, "Error in lending book.", null);
        }
    }
}
