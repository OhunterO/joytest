package com.joyone.test.tabletools;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTableXML {
    static Map<String,String> getTableMap(){
        Map<String,String> tlMap = new HashMap<String,String>();
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<TableDto> list;
        File file =new File("D:\\source\\テーブル一覧.xlsx");
        try {
            InputStream input = new FileInputStream(file);
            list = ExcelImportUtil.importExcel(input, TableDto.class, params);
            for (TableDto tableDto : list) {
                tlMap.put(tableDto.getRonriTableName(), tableDto.getPyTableName());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tlMap;
    }
    public static void main(String[] args) {
        Map<String,String> tlMap =getTableMap();
        for (Map.Entry<String,String> entryDto: tlMap.entrySet()) {
            String fileName= entryDto.getKey();
            String pytable= entryDto.getValue();
            String entry="\r\n";
            ImportParams params = new ImportParams();
            params.setTitleRows(7);
            params.setHeadRows(1);
            params.setStartSheetIndex(2);
            List<TableDto> list;
            File file =new File("D:\\joitsvn\\20.設計\\04.GateCtrl\\99.DB\\テーブル定義書\\【WFCRM】テーブル定義書_"+fileName+".xlsx");
            try {
                InputStream input = new FileInputStream(file);
                list = ExcelImportUtil.importExcel(input, TableDto.class, params);
                System.out.println(list.size());
                StringBuffer sb = new StringBuffer();
                sb.append("<!--テーブル名:"+fileName+"-->");
                sb.append(entry);
                sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                sb.append(entry);
                sb.append("<"+pytable+"s"+">");
                sb.append(entry);
                sb.append("    <"+pytable+">");
                sb.append(entry);
                for (TableDto tableDto : list) {
                    if(tableDto.getPyTableName()!=null){
                        sb.append("        <"+tableDto.getPyTableName()+">"+"</"+tableDto.getPyTableName()+">");
                        sb.append("<!--"+tableDto.getRonriTableName()+"-->");
                        sb.append(entry);
                    }
                }
                sb.append("    </"+pytable+">");
                sb.append(entry);
                sb.append("</"+pytable+"s"+">");
                String outPath = "D:\\joitsvn\\20.設計\\04.GateCtrl\\99.DB\\XML定義書\\";
                FileOutputStream output=new FileOutputStream(outPath+fileName+".xml");
                output.write(sb.toString().getBytes(), 0, sb.toString().getBytes().length);
                output.flush();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
