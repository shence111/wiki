package com.kfm.wiki.mapper;

import com.kfm.wiki.model.Ebook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EbookMapperTest {

    @Autowired
    private EbookMapper mapper;

    @Test
    public void a(){
        List<Ebook> ebooks = mapper.selectList(null);
        ebooks.forEach(System.out::println);
    }
}