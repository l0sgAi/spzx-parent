package com.losgai.spzx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.losgai.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelTest { //EasyExcel读写测试
    @Test
    public void testExcel() {
        read();
        write();
    }

    //读操作
    public static void read(){
        //1.定义读取的excel文件的位置
        String fileName = "C:\\Users\\Losgai\\Desktop\\springboot\\test_spzx_excel.xlsx";
        //2.调用方法读取excel文件
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>();
        EasyExcel.read(fileName, CategoryExcelVo.class,excelListener).sheet().doRead();
        List<CategoryExcelVo> data = excelListener.getData();
        //3.输出
        System.out.println(data);
    }

    //写操作
    public static void write(){
        List<CategoryExcelVo> list = new ArrayList<>(); //插入的数据
        list.add(new CategoryExcelVo(21L,"机身附件",
                "https://lilishop-oss.oss-cn-beijing.aliyuncs.com/1348576427264204962.png",
                12L,
                1,
                0));
        list.add(new CategoryExcelVo(22L,"镜头附件",
                "https://lilishop-oss.oss-cn-beijing.aliyuncs.com/1348576427264204963.png",
                12L,
                1,
                0));
        String fileName = "C:\\Users\\Losgai\\Desktop\\springboot\\test_write.xlsx";
        //1.调用读方法
        EasyExcel.write(fileName, CategoryExcelVo.class).sheet("分类数据").doWrite(list);
    }
}
