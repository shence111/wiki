package com.kfm.wiki.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kfm.wiki.model.Doc;
import com.kfm.wiki.service.DocService;
import com.kfm.wiki.mapper.DocMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author Think
* @description 针对表【doc(文档)】的数据库操作Service实现
* @createDate 2023-03-14 19:33:58
*/
@Service
public class DocServiceImpl extends ServiceImpl<DocMapper, Doc>
    implements DocService{

    @Override
    public boolean save(Doc doc) {
        doc.setId(IdUtil.getSnowflakeNextId());
        return super.save(doc);
    }

    @Override
    public List<Tree<String>> list(Doc doc){
        // 查询所有分类
        List<Doc> list = query(doc);
        // 树形数据
        //return tree(0L, list);
        List<Tree<String>> treeList = TreeUtil.build(list, "0", (treeNode, tree) -> {
            tree.setId(treeNode.getId().toString());
            tree.setParentId(treeNode.getParent().toString());
            tree.setName(treeNode.getName());
            // 扩展属性 ...
            tree.putExtra("parent", treeNode.getParent().toString());
            tree.putExtra("sort", treeNode.getSort());
            tree.putExtra("viewCount", treeNode.getViewCount());
            tree.putExtra("voteCount", treeNode.getVoteCount());
            tree.putExtra("content", treeNode.getContent());
        });
        return treeList;
    }

    private List<Doc> query(Doc doc){
        // 创建搜索条件
        QueryWrapper<Doc> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(doc.getName())){
            queryWrapper.like("name", doc.getName());
        }
        queryWrapper.orderByAsc("sort");
        List<Doc> list = super.list(queryWrapper);

        return list;
    }
}
