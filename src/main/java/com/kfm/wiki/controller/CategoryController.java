package com.kfm.wiki.controller;

import com.kfm.wiki.model.Category;
import com.kfm.wiki.service.CategoryService;
import com.kfm.wiki.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public R list(Category category){
        return R.ok(categoryService.list(category));
    }

    @PostMapping("/addCategory")
    public R addEbook(@RequestBody Category category){
        if (category.getId() == null){
            categoryService.save(category);
        }else {
            categoryService.updateById(category);
        }
        return R.ok();
    }


    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Long id){
        categoryService.removeById(id);
        return R.ok("删除成功");
    }

}
