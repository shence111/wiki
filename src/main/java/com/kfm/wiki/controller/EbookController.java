package com.kfm.wiki.controller;

import com.kfm.wiki.model.Ebook;
import com.kfm.wiki.service.EbookService;
import com.kfm.wiki.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @GetMapping("/list")
    public R list(Ebook ebook) {
        return R.ok(ebookService.listWithCategory(ebook));
    }

    @PostMapping("/addEbook")
    public R addEbook(@RequestBody Ebook ebook) {
        if (ebook.getId() == null){
            ebookService.save(ebook);
        }else {
            ebookService.updateById(ebook);
        }
        return R.ok();
    }

    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Long id) {
        ebookService.removeById(id);
        return R.ok("删除成功");
    }
}