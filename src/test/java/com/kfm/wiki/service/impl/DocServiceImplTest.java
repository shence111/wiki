package com.kfm.wiki.service.impl;

import cn.hutool.core.lang.tree.Tree;
import com.kfm.wiki.model.Doc;
import com.kfm.wiki.service.DocService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocServiceImplTest {

    @Autowired
    DocService docService;

    @Test
    void list() {
        Doc doc = new Doc();
        List<Tree<String>> list = docService.list(doc);
        System.out.println(list);
    }
}