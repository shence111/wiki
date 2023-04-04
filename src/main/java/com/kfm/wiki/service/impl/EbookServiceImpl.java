package com.kfm.wiki.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kfm.wiki.model.Ebook;
import com.kfm.wiki.model.EbookDTO;
import com.kfm.wiki.service.EbookService;
import com.kfm.wiki.mapper.EbookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Think
* @description 针对表【ebook(电子书)】的数据库操作Service实现
* @createDate 2023-03-01 21:56:22
*/
@Service
public class EbookServiceImpl extends ServiceImpl<EbookMapper, Ebook>
    implements EbookService{
    private QueryWrapper<Ebook> queryWrapper = new QueryWrapper<>();

    @Autowired
    private EbookMapper ebookMapper;


    @Override
    public boolean save(Ebook ebook) {
        ebook.setId(IdUtil.getSnowflakeNextId());
        return super.save(ebook);
    }

    public List<EbookDTO> listWithCategory(Ebook ebook){
        return getBaseMapper().selectAllWithCategory(ebook);
    }

    @Override
    public List<Ebook> list(Ebook ebook){
        queryWrapper.like("name", ebook.getName());
        List<Ebook> ebooks = ebookMapper.selectList(queryWrapper);
        return ebooks;
    }
}




