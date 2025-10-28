package com.example.Employee.Detail.Controlller;


import com.example.Employee.Detail.Model.User;
import com.example.Employee.Detail.Repository.UserRepository;
import com.example.Employee.Detail.Service.UserService;
import com.example.Employee.Detail.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
// @CrossOrigin(origins = "http://127.0.0.1:5500")
    //after deployment of front end changing cors
    @CrossOrigin(origins = {
    "http://127.0.0.1:5500",
    "https://employeecrud-frontend.vercel.app",
    "https://employeecrud-frontend-q3kmzxd1x.vercel.app"
})

public class AuthController {
    private static String token="";

    public static String getToken() {
        return token;
    }

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String,String> credentials)
    {
        String email = credentials.get("email");
        String password = credentials.get("password");
        password = passwordEncoder.encode(password);

        if(userRepository.findByEmail(email).isPresent())
        {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }
       userService.createnewuser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("Successfully Registered", HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> credentials)
    {
        String email = credentials.get("email");
        String password = credentials.get("password");

        var optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
        {
           return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(password,optionalUser.get().getPassword()))
        {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }

        token=jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));
    }


}
