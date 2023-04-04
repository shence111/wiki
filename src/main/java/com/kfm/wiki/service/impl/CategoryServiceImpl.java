package com.kfm.wiki.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kfm.wiki.mapper.EbookMapper;
import com.kfm.wiki.model.Category;
import com.kfm.wiki.model.Ebook;
import com.kfm.wiki.service.CategoryService;
import com.kfm.wiki.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* @author Think
* @description 针对表【category(分类)】的数据库操作Service实现
* @createDate 2023-03-02 16:42:30
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{


    @Override
    public boolean save(Category category) {
        category.setId(IdUtil.getSnowflakeNextId());
        return super.save(category);
    }

    @Override
    public List<Tree<String>> list(Category category){
        // 查询所有分类
        List<Category> list = query(category);
        // 树形数据
        //return tree(0L, list);
        List<Tree<String>> treeList = TreeUtil.build(list, "0", (treeNode, tree) -> {
            tree.setId(treeNode.getId().toString());
            tree.setParentId(treeNode.getParent().toString());
            tree.setName(treeNode.getName());
            // 扩展属性 ...
            tree.putExtra("parent", treeNode.getParent().toString());
            tree.putExtra("sort", treeNode.getSort());
        });

        return treeList;
    }

    private List<Category> query(Category category){
        // 创建搜索条件
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(category.getName())){
            queryWrapper.like("name", category.getName());
        }
        queryWrapper.orderByAsc("sort");
        List<Category> list = super.list(queryWrapper);

        return list;
    }


    private List<Category> tree(Long parentId, List<Category> categories) {
        //子分类集合
        List<Category> children = new ArrayList();
        //遍历
        Iterator<Category> iterator = categories.iterator();
        while (iterator.hasNext()) {
            Category next = iterator.next();
            //判断是不是子分类
            if (next.getParent().equals(parentId)) {
                //是就放入集合
                //找子分类的子集合
                List<Category> tree = tree(next.getId(), categories);
                next.setChildren(tree);
                children.add(next);
            }
        }
        return children;
    }
}




