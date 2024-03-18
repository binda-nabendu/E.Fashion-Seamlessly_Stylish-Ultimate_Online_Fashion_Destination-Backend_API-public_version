package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.config.JwtGenerator;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.UserRepository;
import org.example.ecomarcehandicraftbackend.model.request.LoginRequest;
import org.example.ecomarcehandicraftbackend.model.response.AuthResponse;
import org.example.ecomarcehandicraftbackend.service.implementation.EcommerceUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;
    private final PasswordEncoder passwordEncoder;
    private final EcommerceUserDetailsService ecommerceUserDetailsService;

    public AuthController(UserRepository userRepository, JwtGenerator jwtGenerator,PasswordEncoder passwordEncoder, EcommerceUserDetailsService ecommerceUserDetailsService) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.ecommerceUserDetailsService = ecommerceUserDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
        System.out.println("hit");
        String email = user.getEmail();
        email = email.replace(".",""); // it will remove all . form email
        String password = passwordEncoder.encode(user.getPassword());
        String firstname = user.getFirstName();;
        String lastname = user.getLastName();
        User isPresent = userRepository.findByEmail(email);
        if(isPresent != null){
            throw new UserException("Email is Already exits");
        }else{
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setFirstName(firstname);
            newUser.setLastName(lastname);

            User savedUser = userRepository.save(newUser);
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);

            AuthResponse authResponse = new AuthResponse(token, "SignUp Done");
            return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userHandleDuringLogin(@RequestBody LoginRequest request){
        Authentication authentication = authenticate(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String toke = jwtGenerator.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(toke, "Login Done");
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String email, String password)throws BadCredentialsException {
        UserDetails userDetails = ecommerceUserDetailsService.loadUserByUsername(email);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username or Password");
        }else{
            if(!passwordEncoder.matches(password, userDetails.getPassword())){
                throw new BadCredentialsException("Invalid Username or Password");
            }else {
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }
        }
    }
}
