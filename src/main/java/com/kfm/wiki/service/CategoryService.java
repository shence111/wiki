package com.kfm.wiki.service;

import cn.hutool.core.lang.tree.Tree;
import com.kfm.wiki.model.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kfm.wiki.model.Ebook;

import java.util.List;

/**
* @author Think
* @description 针对表【category(分类)】的数据库操作Service
* @createDate 2023-03-02 16:42:30
*/
public interface CategoryService extends IService<Category> {


    List<Tree<String>> list(Category category);
}
