package com.losgai.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.losgai.spzx.manager.mapper.CategoryMapper;
import com.losgai.spzx.model.entity.product.Category;
import com.losgai.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class ExcelListener<T> implements ReadListener<T> {
    private static final int BATCH_COUNT = 500;

    // 每隔5条存储数据库，实际使用中可以500条，然后清理list ，方便内存回收
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    //构造传递Mapper并操作数据库
    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    //从第二行开始读取，把每行读取数据传入到t对象中
    @Override
    public void invoke(T t, AnalysisContext context) {
        //把每行数据对象t放到数据集合cachedDataList里面
        cachedDataList.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            //调用方法一次性批量添加到数据库内
            saveData();
            // 存储完成清理 list(缓存) 避免内存溢出
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

    }

    //读取完成后统一执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    //保存的方法
    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>)cachedDataList);
    }

}
