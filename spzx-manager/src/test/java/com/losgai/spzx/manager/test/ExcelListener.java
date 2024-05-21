package com.losgai.spzx.manager.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> data = new ArrayList<>();

    //读取excel内容，从第二行开始读取，把每行读取内容封装到对象t里面
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
       data.add(t);
    }

    public List<T> getData() { //返回data数据
        return data;
    }

    //读取完成后执行的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

}
