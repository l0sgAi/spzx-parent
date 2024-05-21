package com.losgai.spzx.manager.service;

import com.losgai.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findByParentId(Long parentId); // 根据父节点id查询分类列表

    void exportExcel(HttpServletResponse response); // 导出Excel

    void importExcel(MultipartFile file);
}
