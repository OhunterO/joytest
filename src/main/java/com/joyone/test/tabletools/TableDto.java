
package com.joyone.test.tabletools;

import java.io.Serializable;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;


@ExcelTarget("tableDto")
public class TableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name = "カラム名 (論理名)", isImportField = "true")
    private String ronriTableName;

    @Excel(name = "カラム名 (物理名)", isImportField = "true")
    private String pyTableName;

    public String getRonriTableName() {
        return ronriTableName;
    }

    public void setRonriTableName(String ronriTableName) {
        this.ronriTableName = ronriTableName;
    }

    public String getPyTableName() {
        return pyTableName;
    }

    public void setPyTableName(String pyTableName) {
        this.pyTableName = pyTableName;
    }


}
