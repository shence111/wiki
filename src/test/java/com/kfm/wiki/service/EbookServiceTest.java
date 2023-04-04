package com.kfm.wiki.service;

import com.kfm.wiki.model.Ebook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EbookServiceTest {

    @Autowired
    EbookService ebookService;

    @Test
    public void list(){
        ebookService.list().forEach(System.out::println);
    }

    @Test
    public void add(){
        Ebook ebook = new Ebook();
        ebook.setName("TypeScript 入门教程");
        ebook.setDescription("零基础入门 TypeScript 开发，企业级应用开发最佳首选框架");
        boolean save = ebookService.save(ebook);
        System.out.println(save);
    }

}