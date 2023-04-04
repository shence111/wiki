package com.kfm.wiki.mapper;

import com.kfm.wiki.model.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Think
* @description 针对表【category(分类)】的数据库操作Mapper
* @createDate 2023-03-02 16:42:30
* @Entity com.kfm.wiki.model.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




