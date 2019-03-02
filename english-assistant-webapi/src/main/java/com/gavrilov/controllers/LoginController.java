package com.gavrilov.controllers;


import org.gavrilov.domain.User;
import org.gavrilov.dto.UserDTO;
import org.gavrilov.mappers.MapperFactory;
import org.gavrilov.mappers.UserMapper;
import org.gavrilov.service.UserService;
import org.gavrilov.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {
    private final UserService userService;
    private final WorldService worldService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static UserMapper userMapper = MapperFactory.createMapper(UserMapper.class);

    @Autowired
    public LoginController(UserService userService, WorldService worldService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.worldService = worldService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = {"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> authorizedUser = Optional.ofNullable(userService.findUserByLogin(authentication.getName()));
        if (authorizedUser.isPresent()) return new ModelAndView("redirect:/user/home");
        else modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        UserDTO userDTO = new UserDTO();
        modelAndView.addObject("userDTO", userDTO);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByLogin(userDTO.getLogin());
        if (userExists != null) {
            bindingResult.rejectValue("login", "error.user",
                    "Уже есть пользователь, зарегистрированный c таким логином");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userService.saveUser(userMapper.asUser(userDTO));
            modelAndView.addObject("successMessage", "Пользователь успешно зарегистрирован");
            modelAndView.addObject("userDTO", new UserDTO());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping(value = "/user/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        Integer countWorldOjUser = worldService.getCountWorldOjUser(user);
        modelAndView.addObject("userName", "Добро пожаловать " + user.getFio());
        modelAndView.addObject("statisticText", "Ваша статистика: Всего слов в словаре "
                + countWorldOjUser);
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @GetMapping(value = "/invalidSession")
    private ModelAndView getInvalidSession () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("invalidSession");
        return modelAndView;
    }

    @GetMapping(value = "/sessionExpired")
    private ModelAndView getSessionExpired () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sessionExpired");
        return modelAndView;
    }
}
