package labprograms.log;

import labprograms.constant.Constant;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import static java.io.File.separator;

/**
 * @Description: record the tesing results
 * @auther phantom
 * @create 2019-09-11 下午8:18
 */
public class ResultRecorder {

    private static final int ROWS = 33;
    private static final String[] MEASURES = {"F-measure", "F2-measure", "FSelectTime",
            "FGenerateTime", "FExecuteTime", "F2SelectTime",
            "F2GenerateTime", "F2ExecuteTime"};
    private List<Integer> FArray;
    private List<Integer> F2Array;

    private double FAverage;
    private double F2Average;

    private double FVariance;
    private double F2Variance;

    private List<Long> FTimeSelectArray;
    private List<Long> FTimeGenerateArray;
    private List<Long> FTimeExecuteArray;
    private List<Long> F2TimeSelectArray;
    private List<Long> F2TimeGenerateArray;
    private List<Long> F2TimeExecuteArray;

    private double FSelectAverage;
    private double FGenerateAverage;
    private double FExecuteAverage;
    private double F2SelectAverage;
    private double F2GenerateAverage;
    private double F2ExecuteAverage;

    private double FSelectVariance;
    private double FGenerateVariance;
    private double FExecuteVariance;
    private double F2SelectVariance;
    private double F2GenerateVariance;
    private double F2ExecuteVariance;


    public void initializeMeasureArray(List<Integer> FArray, List<Integer> F2Array) {
        this.FArray = FArray;
        this.F2Array = F2Array;
    }

    public void initializeMeasureAverageAndVariance(double FAverage, double F2Average,
                                                    double FVariance, double F2Variance) {
        this.FAverage = FAverage;
        this.F2Average = F2Average;
        this.FVariance = FVariance;
        this.F2Variance = F2Variance;
    }

    public void getTimeArray(List<Long> FTimeSelectArray, List<Long> FTimeGenerateArray, List<Long> FTimeExecuteArray,
                             List<Long> F2TimeSelectArray, List<Long> F2TimeGenerateArray, List<Long> F2TimeExecuteArray) {
        this.FTimeSelectArray = FTimeSelectArray;
        this.FTimeGenerateArray = FTimeGenerateArray;
        this.FTimeExecuteArray = FTimeExecuteArray;
        this.F2TimeSelectArray = F2TimeSelectArray;
        this.F2TimeGenerateArray = F2TimeGenerateArray;
        this.F2TimeExecuteArray = F2TimeExecuteArray;
    }

    public void getTimeAverage(double FSelectAverage, double FGenerateAverage, double FExecuteAverage,
                               double F2SelectAverage, double F2GenerateAverage, double F2ExecuteAverage) {
        this.FSelectAverage = FSelectAverage;
        this.FGenerateAverage = FGenerateAverage;
        this.FExecuteAverage = FExecuteAverage;
        this.F2SelectAverage = F2SelectAverage;
        this.F2GenerateAverage = F2GenerateAverage;
        this.F2ExecuteAverage = F2ExecuteAverage;
    }

    public void getTimeVariance(double FSelectVariance, double FGenerateVariance, double FExecuteVariance,
                                double F2SelectVariance, double F2GenerateVariance, double F2ExecuteVariance) {
        this.FSelectVariance = FSelectVariance;
        this.FGenerateVariance = FGenerateVariance;
        this.FExecuteVariance = FExecuteVariance;
        this.F2SelectVariance = F2SelectVariance;
        this.F2GenerateVariance = F2GenerateVariance;
        this.F2ExecuteVariance = F2ExecuteVariance;
    }


    public void writeResult(String fileName, int idVersion) {

        String[] averages = initialAverage(FAverage, F2Average, FSelectAverage, FGenerateAverage, FExecuteAverage,
                F2SelectAverage, F2GenerateAverage, F2ExecuteAverage);

        String[] variances = initialVariance(FVariance, F2Variance, FSelectVariance, FGenerateVariance, FExecuteVariance,
                F2SelectVariance, F2GenerateVariance, F2ExecuteVariance);

        String path = Constant.resultPath + separator + fileName;
        Workbook workbook = getworkBook(path);
        XSSFCellStyle cellStyle = getCellStyle(workbook);

        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);

        //获得文件当前写入的行数
        int currentRows = sheet.getLastRowNum();

        if (currentRows != 0) {
            currentRows++;
        }

        //为第一列添加内容
        for (int i = 0; i < ROWS + 1; i++) {
            //设置列宽
            sheet.setColumnWidth(i, 4500);

            Row tempRow = sheet.createRow(currentRows + i);
            tempRow.setHeightInPoints(30);
            Cell tempCell = tempRow.createCell(0);
            if (i == 0) {
                tempCell.setCellValue("id-version = " + String.valueOf(idVersion));
                tempCell.setCellStyle(cellStyle);
            } else if (i == 1) {
                tempCell.setCellValue("Measure");
                tempCell.setCellStyle(cellStyle);
            } else if (i == 2) {
                tempCell.setCellValue("均值：");
                tempCell.setCellStyle(cellStyle);
            } else if (i == 3) {
                tempCell.setCellValue("方差：");
                tempCell.setCellStyle(cellStyle);
            } else {
                tempCell.setCellValue("repeate-time = " + String.valueOf(i - 3));
                tempCell.setCellStyle(cellStyle);
            }
        }
        currentRows++;

        for (int i = 0; i < ROWS; i++) {

            Row tempRow = sheet.getRow(currentRows + i);

            if (i == 0) {
                for (int j = 0; j < MEASURES.length; j++) {
                    Cell tempCell = tempRow.createCell(j + 1);
                    tempCell.setCellValue(MEASURES[j]);
                    tempCell.setCellStyle(cellStyle);
                }
            } else if (i == 1) {
                for (int j = 0; j < averages.length; j++) {
                    Cell tempCell = tempRow.createCell(j + 1);
                    tempCell.setCellValue(averages[j]);
                    tempCell.setCellStyle(cellStyle);
                }
            } else if (i == 2) {
                for (int j = 0; j < variances.length; j++) {
                    Cell tempCell = tempRow.createCell(j + 1);
                    tempCell.setCellValue(variances[j]);
                    tempCell.setCellStyle(cellStyle);
                }
            } else {
                for (int j = 0; j < MEASURES.length; j++) {
                    Cell tempCell = tempRow.createCell(j + 1);
                    switch (j) {
                        case 0:
                            tempCell.setCellValue(FArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 1:
                            tempCell.setCellValue(F2Array.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 2:
                            tempCell.setCellValue(FTimeSelectArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 3:
                            tempCell.setCellValue(FTimeGenerateArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 4:
                            tempCell.setCellValue(FTimeExecuteArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 5:
                            tempCell.setCellValue(F2TimeSelectArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 6:
                            tempCell.setCellValue(F2TimeGenerateArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        case 7:
                            tempCell.setCellValue(F2TimeExecuteArray.get(i - 3));
                            tempCell.setCellStyle(cellStyle);
                            break;
                        default:
                            System.out.println("度量指标的数目异常！");
                            break;
                    }
                }
            }

        }

        writeContent(workbook, path);

    }


    private String[] initialAverage(double FAverage, double F2Average,
                                    double FSelectTimeAverage, double FGenerateTimeAverage, double FExecuteTimeAverage,
                                    double F2SelectTimeAverage, double F2GenerateTimeAverage, double F2ExecuteTimeAverage) {

        String[] averages = {String.valueOf(FAverage), String.valueOf(F2Average),
                String.valueOf(FSelectTimeAverage), String.valueOf(FGenerateTimeAverage), String.valueOf(FExecuteTimeAverage),
                String.valueOf(F2SelectTimeAverage), String.valueOf(F2GenerateTimeAverage), String.valueOf(F2ExecuteTimeAverage)};

        return averages;
    }


    private String[] initialVariance(double FVariance, double F2Variance,
                                     double FSelectTimeVariance, double FGenerateTimeVariance, double FExecuteTimeVariance,
                                     double F2SelectTimeVariance, double F2GenerateTimeVariance, double F2ExecuteTimeVariance) {
        String[] variances = {String.valueOf(FVariance), String.valueOf(F2Variance),
                String.valueOf(FSelectTimeVariance), String.valueOf(FGenerateTimeVariance), String.valueOf(FExecuteTimeVariance),
                String.valueOf(F2SelectTimeVariance), String.valueOf(F2GenerateTimeVariance), String.valueOf(F2ExecuteTimeVariance)};

        return variances;

    }


    private Workbook getworkBook(String path) {
        File file = new File(path);
        XSSFWorkbook workbook = null;
        FileInputStream fis = null;
        if (!file.exists()) {
            workbook = new XSSFWorkbook();
            workbook.createSheet();
        } else {
            try {
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return workbook;
    }


    private void writeContent(Workbook workbook, String path) {
        File file = new File(path);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置单元格的格式
     *
     * @param workbook
     * @return
     */
    private XSSFCellStyle getCellStyle(Workbook workbook) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor((short) 9);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
        cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
        cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
        return cellStyle;
    }


}
