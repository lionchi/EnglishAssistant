package com.gavrilov.controllers;

import com.gavrilov.commons.Constant;
import com.gavrilov.commons.PagerModel;
import com.gavrilov.dto.CategoryDTO;
import com.gavrilov.mappers.CategoryMapper;
import com.gavrilov.mappers.MapperFactory;
import com.gavrilov.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private CategoryMapper categoryMapper = MapperFactory.createMapper(CategoryMapper.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categories")
    public ModelAndView getCategories(@RequestParam("pageSize") Optional<Integer> pageSize,
                                      @RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView();

        int evalPageSize = pageSize.orElse(Constant.INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? Constant.INITIAL_PAGE : page.get() - 1;

        Page<CategoryDTO> categories = categoryService.getPagetOfCategory(PageRequest.of(evalPage, evalPageSize));
        PagerModel pager = new PagerModel(categories.getTotalPages(), categories.getNumber(), Constant.BUTTONS_TO_SHOW);

        modelAndView.addObject("categories", categories);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", Constant.PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.setViewName("user/category/categories");
        return modelAndView;
    }

    @GetMapping(value = "/createCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getViewCreateCategory() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryDTO", new CategoryDTO());
        modelAndView.addObject("isEdit", false);
        modelAndView.setViewName("user/category/managementWorld");
        return modelAndView;
    }

    @PostMapping(value = "/createCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createCategory(@Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        boolean exist = categoryService.isExist(categoryDTO.getNameCategory());
        if (exist) {
            bindingResult.rejectValue("nameCategory", "error.category", "Такая категория уже существует");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("isEdit", false);
            modelAndView.setViewName("user/category/managementWorld");
        }
        else {
            categoryService.saveCategory(categoryDTO);modelAndView.addObject("isEdit", false);
            modelAndView.addObject("categoryDTO", new CategoryDTO());
            modelAndView.addObject("successMessage", "Категория успешно добавлена");
            modelAndView.setViewName("user/category/managementWorld");
        }
        return modelAndView;
    }

    @GetMapping(value = "/updateCategory/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateWorld(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        CategoryDTO categoryDTO = categoryMapper.asCategoryDTO(categoryService.findById(id));
        modelAndView.addObject("categoryDTO", categoryDTO);
        modelAndView.addObject("isEdit", true);
        modelAndView.setViewName("user/category/managementWorld");
        return modelAndView;
    }

    @PutMapping(value = "/updateCategory")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView updateWorld(@Valid CategoryDTO categoryDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("categoryDTO", categoryDTO);
            modelAndView.addObject("isEdit", true);
            modelAndView.setViewName("user/category/managementWorld");
        } else {
            categoryService.updateCategory(categoryDTO);
            modelAndView.setViewName("redirect:/categories");
        }
        return modelAndView;
    }

    @DeleteMapping(value = "/deleteCategory/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteCategory(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        categoryService.deleteCategory(id);
        modelAndView.setViewName("redirect:/categories");
        return modelAndView;
    }
}
