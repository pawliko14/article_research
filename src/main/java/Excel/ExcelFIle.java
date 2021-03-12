package Excel;

import Objetcs.StorenoteBestellingdetails_Stock;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelFIle {

    private static String[] columns = {"Order-No", "Description","MatSource", "Itemm no.", "Desc", "Consumer", "Quantity", "Consumer"};


    public void ExcelFIle(){};





    public void CreateFile2(List<StorenoteBestellingdetails_Stock> storenoteBestellingdetails_stocks) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Sheet
        Sheet sheet = workbook.createSheet("Parts_overview");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with employees data
        int rowNum = 1;
        for(StorenoteBestellingdetails_Stock obj: storenoteBestellingdetails_stocks) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue("Machine_num");

            row.createCell(1)
                    .setCellValue("Machine_desc");

            row.createCell(2)
                    .setCellValue(obj.getLeverancier_bestelling() + "/" + obj.getORDERNUMMER_bestelling());

            row.createCell(3)
                    .setCellValue(obj.getARTIKELCODE_storenotes());

            row.createCell(4)
                    .setCellValue(obj.getARTIKELOMSCHRIJVING_storenotes());


            row.createCell(5)
                    .setCellValue(obj.getAFDELING_storenotes() + "/" + obj.getAFDELINGSEQ_storenotes());


            row.createCell(6)
                    .setCellValue(obj.getBESTELD_storenotes());


            row.createCell(7)
                    .setCellValue(obj.getLeverancier_storenotes() + "/" + obj.getORDERNUMMER_storenotes());
        }

        // Resize all columns to fit the content size
        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }


        createFile(workbook);

        workbook.close();
    }

    private void createFile(Workbook workbook) throws IOException {

        File f = new File("poi-generated-file_20052103.xlsx");

        if(!f.exists()) {
            f.createNewFile();
        }else {
            f = new File("poi-generated-file1.xlsx");
            f.createNewFile();
        }

        FileOutputStream fileOut = new FileOutputStream(f);



        workbook.write(fileOut);
        fileOut.close();
    }

}


