package com.gavrilov.controllers;

import com.gavrilov.commons.Constant;
import org.gavrilov.commons.PagerModel;
import org.gavrilov.domain.User;
import org.gavrilov.dto.CategoryDTO;
import org.gavrilov.dto.WorldDTO;
import org.gavrilov.service.CategoryService;
import org.gavrilov.service.UserService;
import org.gavrilov.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class WorldController {
    private final UserService userService;
    private final WorldService worldService;
    private final CategoryService categoryService;

    @Autowired
    public WorldController(UserService userService, WorldService worldService, CategoryService categoryService) {
        this.userService = userService;
        this.worldService = worldService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/worlds")
    public ModelAndView getWorlds(@RequestParam("pageSize") Optional<Integer> pageSize,
                                  @RequestParam("page") Optional<Integer> page, @RequestParam("isDeleted") Optional<Boolean> isDeleted) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(authentication.getName());

        int evalPageSize = pageSize.orElse(Constant.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Constant.INITIAL_PAGE : page.get() - 1;

        Page<WorldDTO> worlds = worldService.getPageOfWorld(user, PageRequest.of(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(worlds.getTotalPages(), worlds.getNumber(), Constant.BUTTONS_TO_SHOW);

        Boolean aBoolean = isDeleted.orElse(null);
        if (aBoolean != null) {
            if (aBoolean) modelAndView.addObject("successMessage", "Слово успешно удалено");
            else modelAndView.addObject("errorMessage", "При удалении слова произошла ошибка");
        }

        modelAndView.addObject("worlds", worlds);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", Constant.PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("user/world/worlds");
        return modelAndView;
    }

    @GetMapping(value = "/createWorld")
    public ModelAndView world() {
        ModelAndView modelAndView = new ModelAndView();
        WorldDTO worldDTO = new WorldDTO();
        List<CategoryDTO> categories = categoryService.findAll();
        modelAndView.addObject("worldDTO", worldDTO);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("isEdit", false);
        modelAndView.setViewName("user/world/create-world");
        return modelAndView;
    }

    @PostMapping(value = "/createWorld")
    public ModelAndView createNewWorld(@Valid WorldDTO worldDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<CategoryDTO> categories = categoryService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(authentication.getName());
        worldService.isExist(worldDTO.getValue(), user).ifPresent(findWorld -> bindingResult.rejectValue("value", "error.world",
                "Такое слово уже есть"));
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("categories", categories);
            modelAndView.addObject("isEdit", false);
            modelAndView.setViewName("user/world/create-world");
        } else {
            worldService.saveWorld(worldDTO, authentication.getName());
            modelAndView.addObject("successMessage", "Слово успешно добавлено успешно");
            modelAndView.addObject("worldDTO", new WorldDTO());
            modelAndView.addObject("categories", categories);
            modelAndView.addObject("isEdit", false);
            modelAndView.setViewName("user/world/create-world");
        }
        return modelAndView;
    }

    @GetMapping(value = "/updateWorld/{id}")
    public ModelAndView updateWorld(@PathVariable Long id) {
        WorldDTO worldDTO = worldService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        List<CategoryDTO> categories = categoryService.findAll();
        modelAndView.addObject("worldDTO", worldDTO);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("isEdit", true);
        modelAndView.setViewName("user/world/create-world");
        return modelAndView;
    }

    @PutMapping(value = "/updateWorld")
    public ModelAndView updateWorld(@Valid WorldDTO worldDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<CategoryDTO> categories = categoryService.findAll();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("categories", categories);
            modelAndView.addObject("worldDTO", worldDTO);
            modelAndView.addObject("isEdit", true);
            modelAndView.setViewName("user/world/create-world");
        } else {
            worldService.updateWorld(worldDTO);
            modelAndView.setViewName("redirect:/worlds");
        }
        return modelAndView;
    }

    @DeleteMapping(value = "/deleteWorld/{id}")
    public ModelAndView deleteWorld(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        boolean isDeleted = worldService.deleteWorld(id);
        modelAndView.setViewName("redirect:/worlds?isDeleted=" + isDeleted);
        return modelAndView;
    }
}
