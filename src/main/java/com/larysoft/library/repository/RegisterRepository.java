package com.larysoft.library.repository;

import com.larysoft.library.models.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RegisterRepository extends JpaRepository<Register, Long> {

    //used to get user registration details by username
    Register getRegisterByUsername(@Param("username") String username);
}
