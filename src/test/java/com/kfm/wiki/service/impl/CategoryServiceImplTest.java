package com.kfm.wiki.service.impl;

import com.kfm.wiki.model.Category;
import com.kfm.wiki.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Test
    void tree() {
//        List<Category> list = categoryServiceImpl.list();
//        List<Category> tree = categoryServiceImpl.tree(0L, list);
//        System.out.println(tree);
    }
}