package labprograms.log;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

import static java.io.File.separator;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;
import static org.apache.poi.ss.usermodel.VerticalAlignment.BOTTOM;

/**
 * describe:
 * 将测试过程的信息记录起来，写入文档中，记录的信息主要包括以下几点：
 * 1，METRIC：Fn
 * 2，每一个度量标准对应的时间，包括;选择测试用例的时间、生成测试用例的时间和执行测试用例的时间
 * 3，每一组数据对应的方差
 * 4，每一组数据对应的平均值
 *
 * @author phantom
 * @date 2019/07/27
 */
public class WriteDataToExcel {
    public void writeTestingInfoToExcel(String fileName, List<List<Integer>> metrics,
                                        List<List<Long>> times) {
        int allColumns = 2 * metrics.size() + 1;
        String path = System.getProperty("user.dir") + separator + "excelLog"
                + separator + fileName + ".xlsx";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        XSSFWorkbook workbook = (XSSFWorkbook) getWorkBook(path);
        CellStyle cellStyle = getCellStyle(workbook);
        //初始化sheet
        Sheet sheet = initializeSheet(workbook, cellStyle);
        //向sheet中添加内容
        sheet = writeContent(sheet, cellStyle, metrics, times);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(path));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向sheet中添加内容
     *
     * @param sheet
     * @param cellStyle
     * @return
     */
    private Sheet writeContent(Sheet sheet, CellStyle cellStyle, List<List<Integer>> metrics,
                               List<List<Long>> times) {
        //首先对第一行进行初始化
        Row row = sheet.createRow(0);
        for (int i = 0; i < metrics.size(); i++) {
            Cell cell1 = row.createCell(i * 4 + 1);
            String content1 = "F" + String.valueOf(i + 1) + "-measure";
            cell1.setCellValue(content1);
            cell1.setCellStyle(cellStyle);

            Cell cell2 = row.createCell(i * 4 + 2);
            String content2 = "T" + String.valueOf(i + 1) + "-time-selection";
            cell1.setCellValue(content2);
            cell1.setCellStyle(cellStyle);

            Cell cell3 = row.createCell(i * 4 + 3);
            String content3 = "T" + String.valueOf(i + 1) + "-time-generation";
            cell1.setCellValue(content3);
            cell1.setCellStyle(cellStyle);

            Cell cell4 = row.createCell(i * 4 + 4);
            String content4 = "T" + String.valueOf(i + 1) + "-time-execution";
            cell1.setCellValue(content4);
            cell1.setCellStyle(cellStyle);
        }
        //接着向表中添加数据
        for (int i = 1; i < 32; i++) {
            Row tempRow = sheet.createRow(i);
            for (int j = 0; j < metrics.size(); j++) {
                Cell cell1 = tempRow.createCell(i * 4 + 1);
                Cell cell2 = tempRow.createCell(i * 4 + 2);
                Cell cell3 = tempRow.createCell(i * 4 + 3);
                Cell cell4 = tempRow.createCell(i * 4 + 4);
                cell1.setCellValue(String.valueOf(metrics.get(j).get(i - 1)));
                cell2.setCellValue(String.valueOf(times.get(j * 3).get(i - 1)));
                cell3.setCellValue(String.valueOf(times.get(j * 3 + 1).get(i - 1)));
                cell4.setCellValue(String.valueOf(times.get(j * 3 + 2).get(i - 1)));
            }
        }
        return sheet;
    }

    /**
     * 对表格做初始化处理，主要为第一列添加标号
     *
     * @param workbook
     * @param cellStyle
     * @return
     */
    private Sheet initializeSheet(XSSFWorkbook workbook, CellStyle cellStyle) {
        Sheet sheet = workbook.getSheetAt(0);
        for (int i = 1; i < 34; i++) {
            if (i != 32 || i != 33) {
                Row row = sheet.createRow(i);
                row.setHeightInPoints(30);
                Cell cell = row.createCell(0);
                cell.setCellValue(String.valueOf(i));
                cell.setCellStyle(cellStyle);
            } else if (i == 32) {
                Row row = sheet.createRow(i);
                row.setHeightInPoints(30);
                Cell cell = row.createCell(0);
                cell.setCellValue("平均值");
                cell.setCellStyle(cellStyle);
            } else {
                Row row = sheet.createRow(i);
                row.setHeightInPoints(30);
                Cell cell = row.createCell(0);
                cell.setCellValue("方差");
                cell.setCellStyle(cellStyle);
            }
        }
        return sheet;
    }


    /**
     * 设置单元格的格式
     *
     * @param workbook 要写入的excel对象
     * @return 单元格的格式的对象
     */
    private CellStyle getCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CENTER);
        cellStyle.setVerticalAlignment(BOTTOM);
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("#,##0.00"));
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }

    private Workbook getWorkBook(String path) {
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream(path);
            workbook = new XSSFWorkbook(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }


}
