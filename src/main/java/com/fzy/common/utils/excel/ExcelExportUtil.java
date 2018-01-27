package com.fzy.common.utils.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

/** 
 * EXCEL报表工具类
 *
 * @author fuzhongyu
 * @date 2017/11/27
 */  
public class ExcelExportUtil {  
  
    private HSSFWorkbook wb = null;  
    private HSSFSheet sheet = null;  
  
    /** 
     * @param wb 
     * @param sheet  
     */  
    public ExcelExportUtil(HSSFWorkbook wb, HSSFSheet sheet) {  
        this.wb = wb;
        this.sheet = sheet;  
    }  
  
    /** 
     * 创建通用EXCEL头部 
     *  
     * @param headString   头部显示的字符
     * @param colSum   该报表的列数
     */  
    public void createNormalHead(String headString, int colSum) {
        HSSFRow row = sheet.createRow(0);  
        // 设置第一行  
        HSSFCell cell = row.createCell(0); 
        
        // 指定行高  
        row.setHeight((short) 600);
  
        // 定义单元格为字符串类型  
        cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理  
        cell.setCellValue(new HSSFRichTextString(headString));  
  
        // 指定合并区域  
        /** 
         * 参数1：起始行  ,参数2：终止行   ,参数3：起始列 , 参数4：终止列  
         * public CellRangeAddress(int rowFrom, short colFrom, int rowTo, short colTo) 
         */  
        CellRangeAddress region = new CellRangeAddress(0, 0, (short) 0, (short) colSum); 
        sheet.addMergedRegion(region); 
  
        // 定义单元格格式，添加单元格表样式，并添加到工作簿  
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        // 设置单元格水平对齐类型  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐  
        cellStyle.setWrapText(true);// 指定单元格自动换行  
        // 设置单元格字体  
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        font.setFontName("宋体");  
        font.setFontHeight((short) 250);  
        cellStyle.setFont(font);  
        cell.setCellStyle(cellStyle);  
    }  
  
    /** 
     * 设置报表标题 
     *  
     * @param columHeader  标题字符串数组
     */  
    public void createColumHeader(String[] columHeader) {  
  
        // 设置列头 在第二行  
        HSSFRow row2 = sheet.createRow(1);  
  
        // 指定行高  
        row2.setHeight((short) 600);  
  
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐  
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐  
        cellStyle.setWrapText(true);// 指定单元格自动换行  
  
        // 单元格字体  
        HSSFFont font = wb.createFont();  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        font.setFontName("宋体");  
        font.setFontHeight((short) 200);  
        cellStyle.setFont(font);  
  
        // 设置单元格背景色  
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
  
        HSSFCell cell3 = null;  
  
        for (int i = 0; i < columHeader.length; i++) {  
            cell3 = row2.createCell(i);  
            cell3.setCellType(HSSFCell.ENCODING_UTF_16);  
            cell3.setCellStyle(cellStyle);  
            cell3.setCellValue(new HSSFRichTextString(columHeader[i]));
        }  
    }
    
    /**
     * 创建内容单元格
     * 
     * @param row  行
     * @param col  列索引
     * @param val  值
     * @param cellStyle  单元格格式
     */
    public void createCell(HSSFRow row, int col, String val, HSSFCellStyle cellStyle) {
        HSSFCell cell = row.createCell(col);
        cell.setCellType(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(new HSSFRichTextString(val));
        cell.setCellStyle(cellStyle);
    }
  
    /**
     * 自适应列宽
     * 
     * @param cells  列数
     */
    public void reSizeWidth(int cells) {
    	for (int j = 0; j < cells+1; j++) {
    		sheet.autoSizeColumn(j); //调整第一列宽度
    	}
    	
    }  
  
    public HSSFSheet getSheet() {  
        return sheet;  
    }  
  
    public void setSheet(HSSFSheet sheet) {  
        this.sheet = sheet;  
    }  
  
    public HSSFWorkbook getWb() {  
        return wb;  
    }  
  
    public void setWb(HSSFWorkbook wb) {  
        this.wb = wb;  
    }

}
