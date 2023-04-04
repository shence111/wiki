package com.kfm.wiki.mapper;

import com.kfm.wiki.model.Ebook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kfm.wiki.model.EbookDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Think
* @description 针对表【ebook(电子书)】的数据库操作Mapper
* @createDate 2023-03-01 21:56:22
* @Entity com.kfm.wiki.model.Ebook
*/
@Mapper
public interface EbookMapper extends BaseMapper<Ebook> {

    List<EbookDTO> selectAllWithCategory(Ebook ebook);
}




