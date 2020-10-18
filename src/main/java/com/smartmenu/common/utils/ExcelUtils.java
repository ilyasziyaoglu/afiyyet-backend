package com.smartmenu.common.utils;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public class ExcelUtils {

	public XSSFWorkbook writeExcel(List<Object> headers, List<List<Object>> body) {
		return writeExcel(headers, body, "Sheet 1");
	}

	public XSSFWorkbook writeExcel(List<Object> headers, List<List<Object>> body, String sheetName) {

		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet(sheetName);

		// SETTING HEADERS
		XSSFRow headRow = sheet.createRow(0);
		IntStream.range(0, headers.size())
				.forEach(i -> headRow.createCell(i).setCellValue(headers.get(i).toString()));

		makeRowBold(workbook, headRow);

		// SETTING BODY
		AtomicInteger i = new AtomicInteger();
		body.forEach(row -> {
			XSSFRow bodyRow = sheet.createRow(i.get() + 1);
			AtomicInteger j = new AtomicInteger();
			row.forEach(cell -> {
				try {
					bodyRow.createCell(j.get()).setCellValue(cell.toString());
					j.getAndIncrement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			i.getAndIncrement();
		});

		return workbook;
	}

	public void makeRowBold(XSSFWorkbook wb, XSSFRow row) {
		XSSFCellStyle style = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);

		for (int i = 0; i < row.getLastCellNum(); i++) {
			row.getCell(i).setCellStyle(style);
		}
	}

	public void writeExcelFileToResponse(String fileName, XSSFWorkbook workbook, HttpServletResponse response) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);

			// Write content type and also length (determined via byte array).
			response.setContentType("application/vnd.ms-excel");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

			// Flush byte array to servlet output stream.
			ServletOutputStream out = response.getOutputStream();
			baos.writeTo(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeExcelFileToLocalStorage(String directoryPath, String fileName, XSSFWorkbook workbook) {
		try {
			workbook.write(new FileOutputStream(directoryPath + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
