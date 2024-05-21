package com.losgai.spzx.manager.mapper;

import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //根据父节点id查询子节点
    List<Category> selectCategoryByParentId(Long parentId);

    //获取某个父节点下有多少子节点
    int selectCountByParentId(Long parentId);

    //获取所有分类数据的方法
    List<Category> selectAll();

    //批量保存方法
    void batchInsert(List<CategoryExcelVo> categoryList);

}
