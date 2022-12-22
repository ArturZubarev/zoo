package org.zubarev.instazoo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zubarev.instazoo.entity.User;
import org.zubarev.instazoo.entity.enums.ERole;
import org.zubarev.instazoo.exceptions.UserExistException;
import org.zubarev.instazoo.payload.request.SignUpRequest;
import org.zubarev.instazoo.repository.UserRepository;

@Service
public class UserService {
    public static final Logger log= LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * Через контроллер получаем объект SignUpRequest,
     * оттуда растягиваем данные для модели и сохраняем в базу через репозиторий
     * пароль предварительно кодируется
     */
    public User createUser(SignUpRequest userIn) {
        User user = new User();
        user.setEmail(user.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(encoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);
        try {
            log.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception exception){
            log.error("Error during registration");
            throw new UserExistException("This user "+ user.getUsername()+"already exists. Please check" +
                    "your credentials");

        }

    }
}
