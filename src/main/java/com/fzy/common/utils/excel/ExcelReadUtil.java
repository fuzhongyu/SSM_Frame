package com.fzy.common.utils.excel;

import com.fzy.common.entity.ErrorsMsg;
import com.fzy.common.exception.ServiceException;
import com.fzy.common.utils.ParamUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel 读取工具类
 *
 * @author fuzhongyu
 * @date 2017/11/28
 */

public class ExcelReadUtil {

    //.xlsx后缀
    public static final String XLSX_SUFFIX = ".xlsx";
    //.xls后缀
    public static final String XLS_SUFFIX=".xls";


    /**
     * 数据类型枚举类
     */
    public enum DATA_TYPE_ENUM{
        INT_TYPE(1),     //int类型单元格
        DOUBLE_TYPE(2),  //浮点型类型单元格
        STRING_TYPE(3);  //字符串类型单元格

        private Integer type;

        DATA_TYPE_ENUM(Integer type){
            this.type=type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    private String[] colName;    //接收数据列key值

    private DATA_TYPE_ENUM[] colDataType; //列数据类型

    private Boolean[] blankSign;  //列数据是否可以为空  true-不允许为空， false-可以为空

    private Integer dataStartRow;    //数据起始导入行（第一行为0）

    private Workbook wb;

    private List<String> errorList=new ArrayList<>(); //错误列表

    /**
     * 构造器
     * @param inputStream  excel文件流
     * @param fileType     文件后缀
     * @param colName       接收数据列key值
     * @param colDataType   列数据类型
     * @param dataStartRow  数据起始导入行（）
     */
    public ExcelReadUtil(InputStream inputStream, String fileType, String[] colName, DATA_TYPE_ENUM[] colDataType,Boolean[] blankSign, Integer dataStartRow){
        //检查后缀
        if(XLSX_SUFFIX.equalsIgnoreCase(fileType)){
            try {
                wb=new XSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"创建workbook失败！");
            }
        }else if(XLS_SUFFIX.equalsIgnoreCase(fileType)){
            try {
                wb=new HSSFWorkbook(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"创建workbook失败！");
            }
        }else {
            throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"文件格式错误，请导入excel文件！");
        }

        if(inputStream==null){
            throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"输入流为空!");
        }
        //检查列数据是否正确(保证列书相同)
        if(colName==null || colDataType==null || blankSign==null || colName.length==0 || colName.length !=colDataType.length || colName.length !=blankSign.length){
            throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"请检查colName或colDataType或blankSign是否有误！");
        }
        if(dataStartRow==null || dataStartRow<0){
            throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"起始导入行有误 ！");
        }

        this.colName=colName;
        this.colDataType=colDataType;
        this.blankSign=blankSign;
        this.dataStartRow=dataStartRow;

    }

    /**
     * 获取所有工作表数据
     * @return
     */
    public List<Map<String,Object>> getAllSheetData(){
        Integer sheetNum=wb.getNumberOfSheets();
        return getSheetData(0,sheetNum-1);
    }


    /**
     * 读取的工作表,第一页从0开始
     * @param startSheet  开始工作表索引（可取到）
     * @param endSheet    结束工作表索引 （不可取到） 如填getSheetData(1,2)则只读取第一个工作表数据
     *
     */
    public List<Map<String,Object>> getSheetData(Integer startSheet, Integer endSheet) {
        List<Map<String,Object>> returnList=new ArrayList<>();

        if(startSheet==null||endSheet==null||endSheet<=startSheet){
            return returnList;
        }

        for (int i=startSheet;i<endSheet;i++){
            getSheetData(i,returnList);
        }

        //如果数据格式有误，则返回错误信息。没有则返回从excel中读取的数据
        if(errorList.size()>0){
            StringBuilder builder=new StringBuilder("excel数据格式有误！</br>");
            for (String errString:errorList){
                builder.append(errString).append("</br>");
            }
            throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),builder.toString());
        }

        return returnList;
    }


    /**
     * 获取一页工作表数据
     * @param sheetNum  第sheetNum页工作表数据
     * @param list      保存数据list
     */
    private void getSheetData(Integer sheetNum, List<Map<String,Object>> list){
        Sheet sheet=wb.getSheetAt(sheetNum);
        //总行数为lastRowNum+1 从0开始数据
        Integer lastRowNum=sheet.getLastRowNum();
        for (int i=dataStartRow;i<=lastRowNum;i++){
            Row row=sheet.getRow(i);
            if (row==null){
                continue;
            }
            Map<String,Object> rowDataMap=getRowData(row,sheetNum,i);
            if(rowDataMap==null){
                continue;
            }

            list.add(rowDataMap);
        }

    }

    /**
     * 获取行数据
     * @param row
     * @return
     */
    private Map<String,Object> getRowData(Row row,Integer sheetNum,Integer rowIndex){

        Map<String,Object> returnMap=new HashMap<>();
        //记录该行是否有错误，有错误的话不对这行进行插入
        Boolean flag=true;
        //总列数为colNum ,从1开始数数
        short colNum=row.getLastCellNum();
        if(colNum!=colName.length){
            throw new ServiceException(ErrorsMsg.ERR_1001.getCode(),"数据表列和设置的列不等！");
        }
        for (int i=0;i<colNum;i++){
            Cell cell=row.getCell(i);
            String cellStringVal=getCellStringValue(cell);
            if(cellStringVal==null){
                //如果允许为空，则继续，如果不允许为空，则添加数据错误信息
                if(!blankSign[i]){
                  continue;
                }
                flag=false;
                errorList.add("工作表"+(sheetNum+1)+"的第"+(rowIndex+1)+"行，第"+(i+1)+"列，数据不能为空 ！");
            }

            Integer dataType=colDataType[i].getType();
            if(new Integer(1).equals(dataType)){
                Integer cellVal= ParamUtils.IntegerParam(cellStringVal);
                if (cellVal==null){
                    //处理如：1.0 这种情况
                    Double cellValDoub=ParamUtils.DoubleParam(cellStringVal);
                    if(cellValDoub==null){
                        flag=false;
                        errorList.add("工作表"+(sheetNum+1)+"的第"+(rowIndex+1)+"行，第"+(i+1)+"列，数据类型有误！");
                    }else {
                        cellVal=ParamUtils.IntegerParam(cellStringVal.substring(0,cellStringVal.indexOf(".")));
                        returnMap.put(colName[i],cellVal);
                    }
                }else {
                    returnMap.put(colName[i],cellVal);
                }
            }else if(new Integer(2).equals(dataType)){
                Double cellVal=ParamUtils.DoubleParam(cellStringVal);
                if (cellVal==null){
                    flag=false;
                    errorList.add("工作表"+(sheetNum+1)+"的第"+(rowIndex+1)+"行，第"+(i+1)+"列，数据类型有误！");
                }else {
                    returnMap.put(colName[i],cellVal);
                }
            }else {
                returnMap.put(colName[i],cellStringVal);
            }
        }

        if(!flag){
            return null;
        }

        return returnMap;
    }


    /**
     * 获取单元格数据
     * @param cell
     * @return
     */
    private String getCellStringValue(Cell cell) {
        if(cell==null){
            return null;
        }
        String cellValue = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING://字符串类型
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: //数值类型
                Double val=cell.getNumericCellValue();
                if(val!=null){
                    cellValue=String.valueOf(val);
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return cellValue;
    }

}
