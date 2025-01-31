package com.example.Ecommerce.Service;

import com.example.Ecommerce.common.APIResponse;
import com.example.Ecommerce.dto.LoginRequestDTO;
import com.example.Ecommerce.dto.SignUpRequestDTO;
import com.example.Ecommerce.Model.User;
import com.example.Ecommerce.Repository.UserRepository;
import com.example.Ecommerce.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public APIResponse signUp(SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = new APIResponse();

        // validation

        // dto to entity
        User userEntity = new User();
        userEntity.setName(signUpRequestDTO.getName());
        userEntity.setEmail(signUpRequestDTO.getEmail());
        userEntity.setActive(Boolean.TRUE);

        userEntity.setPassword(signUpRequestDTO.getPassword());

        // store entity
        userEntity = userRepository.save(userEntity);

        // generate jwt
        String token = jwtUtils.generateJwt(userEntity);

        Map<String , Object> data = new HashMap<>();
        data.put("accessToken", token);

        apiResponse.setData(data);

        // return
        return apiResponse;
    }

    public APIResponse login(LoginRequestDTO loginRequestDTO) {

        APIResponse apiResponse = new APIResponse();

        // validation

        // verify user exist with given email and password
        User user = userRepository.findOneByEmailIdIgnoreCaseAndPassword(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

        // response
        if(user == null){
            apiResponse.setData("User login failed");
            return apiResponse;
        }

        // generate jwt
        String token = jwtUtils.generateJwt(user);

        Map<String , Object> data = new HashMap<>();
        data.put("accessToken", token);

        apiResponse.setData(data);

        return apiResponse;
    }
}