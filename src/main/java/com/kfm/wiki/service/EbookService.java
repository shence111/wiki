package com.kfm.wiki.service;

import com.kfm.wiki.model.Ebook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kfm.wiki.model.EbookDTO;

import java.util.List;

/**
* @author Think
* @description 针对表【ebook(电子书)】的数据库操作Service
* @createDate 2023-03-01 21:56:22
*/
public interface EbookService extends IService<Ebook> {

    List<Ebook> list(Ebook ebook);

    List<EbookDTO> listWithCategory(Ebook ebook);
}
