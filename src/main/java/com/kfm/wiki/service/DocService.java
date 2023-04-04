package com.kfm.wiki.service;

import cn.hutool.core.lang.tree.Tree;
import com.kfm.wiki.model.Category;
import com.kfm.wiki.model.Doc;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Think
* @description 针对表【doc(文档)】的数据库操作Service
* @createDate 2023-03-14 19:33:58
*/
public interface DocService extends IService<Doc> {

    List<Tree<String>> list(Doc doc);
}
