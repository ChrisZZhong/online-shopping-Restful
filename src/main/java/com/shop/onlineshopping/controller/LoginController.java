package com.shop.onlineshopping.controller;

import com.shop.onlineshopping.domain.User;
import com.shop.onlineshopping.dto.request.LoginRequest;
import com.shop.onlineshopping.dto.request.SignUpRequest;
import com.shop.onlineshopping.dto.response.LoginResponse;
import com.shop.onlineshopping.security.AuthUserDetail;
import com.shop.onlineshopping.security.JwtProvider;
import com.shop.onlineshopping.service.AuthenticationService;
import com.shop.onlineshopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {



    private JwtProvider jwtProvider;

    private UserService userService;

    private AuthenticationService authenticationService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //User trying to log in with username and password
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws BadCredentialsException{
        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Incorrect credentials, please try again.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object
        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        return ResponseEntity.ok(
                LoginResponse.builder()
                        .status("success")
                        .message("Welcome " + authUserDetail.getUsername())
                        .token(token)
                        .isAdmin(authUserDetail.hasAuthority("admin"))
                        .build()
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        // check username and email if already exists
        if (userService.getUserByUsername(signUpRequest.getUsername()).isPresent() ||
                userService.getUserByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity.ok(
                    LoginResponse.builder()
                            .status("Conflict")
                            .message("Username or email already exists, please try again.")
                            .build());
        }
        // signup
        userService.signUp(signUpRequest);
        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getUsername(), signUpRequest.getPassword())
            );
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object
        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        //Returns the token as a response to the frontend/postman
        return ResponseEntity.ok(
                LoginResponse.builder()
                        .status("success")
                        .message("Welcome " + authUserDetail.getUsername())
                        .token(token)
                        .isAdmin(authUserDetail.hasAuthority("admin"))
                        .build()
        );
    }

}
