package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.config.JwtGenerator;
import org.example.ecomarcehandicraftbackend.exception.UserException;
import org.example.ecomarcehandicraftbackend.model.User;
import org.example.ecomarcehandicraftbackend.repository.UserRepository;
import org.example.ecomarcehandicraftbackend.service.service_model.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EcommerceUserService implements UserService {
    private UserRepository userRepository;
    private JwtGenerator jwtGenerator;

    public EcommerceUserService(UserRepository userRepository, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }


    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserException("User Not Found associated with Id: " + userId);
        }
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtGenerator.getEmailFromToken(jwt);

        User user = userRepository.findByEmail(email);
        if(user != null){
            return user;
        }else {
            throw new UserException("User Not Found associated with email: " + email);
        }
    }
}
