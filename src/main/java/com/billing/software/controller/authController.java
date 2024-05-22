package com.billing.software.controller;

import com.billing.software.dto.LoginRequestDTO;
import com.billing.software.dto.LoginResponseDTO;
import com.billing.software.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class authController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/api/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword());

            authenticationManager.authenticate(token);

            String jwt = jwtUtils.generateJwt(request.getEmail());

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setJwttoken(jwt);
            loginResponseDTO.setMessage("Signed IN Successfully.");

            return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
        }
        catch (Exception e){
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
            loginResponseDTO.setJwttoken("Not applicable");
            loginResponseDTO.setMessage("Error signing in ! Check your Email/Password.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponseDTO);
        }
    }
}
