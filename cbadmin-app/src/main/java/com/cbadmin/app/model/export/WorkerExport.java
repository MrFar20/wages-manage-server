package com.cbadmin.app.model.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
@HeadStyle(fillPatternType = FillPatternTypeEnum.NO_FILL)
public class WorkerExport implements Serializable {


    private static final long serialVersionUID = -6422026616586404100L;


    @ExcelProperty("序号")
    @ColumnWidth(10)
    private Integer no;

    /**
     * 姓名
     */
    @ExcelProperty("姓名")
    @ColumnWidth(20)
    private String name;

    /**
     * 身份证号
     */
    @ExcelProperty("身份证号码")
    @ColumnWidth(30)
    private String idNumber;

    /**
     * 银行卡号
     */
    @ExcelProperty("银行卡号")
    @ColumnWidth(30)
    private String bankcardNumber;

    /**
     * 开户行
     */
    @ExcelProperty("开户行")
    @ColumnWidth(40)
    private String bankOrg;

    /**
     * 联系方式
     */
    @ExcelProperty("联系方式")
    @ColumnWidth(20)
    private String contractInfo;


    @ExcelProperty("应发工资(元)")
    @ColumnWidth(18)
    private String amount = "";

}
