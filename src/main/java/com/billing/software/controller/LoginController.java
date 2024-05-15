package com.billing.software.controller;


import com.billing.software.Dao.CustomerDao;
import com.billing.software.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private CustomerDao customerdao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            savedCustomer = customerdao.save(customer);
            if (savedCustomer.getId() != null) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/api/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        System.out.println(authentication);
        List<Customer> customers = customerdao.findByEmail(authentication.getName());
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            return null;
        }

    }

    @GetMapping(path = "/api/details")
    public Authentication getDetails(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
