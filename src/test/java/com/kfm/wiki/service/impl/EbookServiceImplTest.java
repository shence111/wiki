package com.kfm.wiki.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kfm.wiki.mapper.EbookMapper;
import com.kfm.wiki.model.Ebook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EbookServiceImplTest {

    @Autowired
    EbookMapper ebookMapper;

    @Test
    void list() {
        Ebook ebook = new Ebook();
        ebook.setName("Spring Boot");
        QueryWrapper<Ebook> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", ebook.getName());
        List<Ebook> ebooks = ebookMapper.selectList(queryWrapper);
        System.out.println(ebooks);
    }
}