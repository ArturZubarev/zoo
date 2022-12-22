package org.zubarev.instazoo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zubarev.instazoo.payload.request.LoginRequest;
import org.zubarev.instazoo.payload.request.SignUpRequest;
import org.zubarev.instazoo.payload.response.JWTTokenSuccessResponse;
import org.zubarev.instazoo.payload.response.MessageResponse;
import org.zubarev.instazoo.security.JWTTokenProvider;
import org.zubarev.instazoo.security.SecurityConstants;
import org.zubarev.instazoo.services.UserService;
import org.zubarev.instazoo.validatons.ResponseErrorValidation;

import javax.validation.Valid;

/**
 * Класс-контроллер, отвечающий за авторизацию пользователей
 */
@Controller
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (ObjectUtils.isEmpty(errors))
            return errors;
            userService.createUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        }
    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (ObjectUtils.isEmpty(errors))
            return errors;
        Authentication authentication=manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt= SecurityConstants.TOKEN_PREFIX+ jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true,jwt));

    }

    }



