package com.losgai.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.losgai.spzx.common.exception.SpzxException;
import com.losgai.spzx.manager.listener.ExcelListener;
import com.losgai.spzx.manager.mapper.CategoryMapper;
import com.losgai.spzx.manager.service.CategoryService;
import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.model.vo.common.ResultCodeEnum;
import com.losgai.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Long parentId) {
        //1.根据id查询，返回List集合
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(parentId);
        //2.遍历返沪List集合，判断每一个分类是有下层分类，有的话，hasChildren=true
        if (!CollectionUtils.isEmpty(categoryList)) { //只要返回列表非空就遍历
            categoryList.forEach(category -> {
                //判断每一层是否有下层分类
                int count = categoryMapper.selectCountByParentId(category.getId());
                category.setHasChildren(count > 0);
            });
        }
        return categoryList;
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        try {
            //1.设置响应头和其它信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // URLEncoder.encode防止中文乱码
            String fileName = URLEncoder.encode("分类数据", "UTF-8");

            //设置头信息 Content-disposition:以下载方式打开
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //2.调用mapper方法查询所有分类
            List<Category> categoryList = categoryMapper.selectAll();
            //将categoryList转换为CategoryExcelVo
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();

            for (Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                //工具类复制
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }
            //3.调用EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }
    }

    //导入
    @Override
    public void importExcel(MultipartFile file) {
        //TODO 监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
        //每次读取都是创建新的对象，避免了并发问题

        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener)
                    .sheet().doRead();
        } catch (IOException e) {
                e.printStackTrace();
                throw new SpzxException(ResultCodeEnum.DATA_ERROR);
        }

    }
}
