package com.kfm.wiki.controller;

import com.kfm.wiki.model.Doc;
import com.kfm.wiki.service.DocService;
import com.kfm.wiki.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("doc")
public class DocController {

    @Autowired
    private DocService docService;

    @GetMapping("/list")
    public R list(Doc doc){
        return R.ok(docService.list(doc));
    }

    @PostMapping("/addDoc")
    public R addEbook(@RequestBody Doc doc){
        if (doc.getId() == null){
            docService.save(doc);
        }else {
            docService.updateById(doc);
        }
        return R.ok();
    }


    @DeleteMapping("/delete/{id}")
    public R del(@PathVariable Long id){
        docService.removeById(id);
        return R.ok("删除成功");
    }
}
