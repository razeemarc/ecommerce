package com.example.Ecommerce.Controller;

import com.example.Ecommerce.dto.SignUpRequestDTO;
import com.example.Ecommerce.dto.LoginRequestDTO;
import com.example.Ecommerce.Service.UserService;
import com.example.Ecommerce.util.JwtUtils;
import com.example.Ecommerce.common.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService loginService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = loginService.signUp(signUpRequestDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        APIResponse apiResponse = loginService.login(loginRequestDTO);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/privateApi")
    public ResponseEntity<APIResponse> privateApi(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
        APIResponse apiResponse = new APIResponse();
        jwtUtils.verify(auth);
        apiResponse.setData("this is private api");
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
