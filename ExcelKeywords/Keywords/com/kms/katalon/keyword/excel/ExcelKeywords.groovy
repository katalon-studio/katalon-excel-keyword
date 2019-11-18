package com.kms.katalon.keyword.excel

import java.text.DateFormat
import java.text.SimpleDateFormat

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException

import com.google.common.io.Files
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.util.KeywordUtil

import groovy.transform.CompileStatic

@CompileStatic
class ExcelKeywords {

	/**
	 * Create a new excel file
	 *
	 * @param filePath path of excel file
	 */
	@CompileStatic
	@Keyword
	static void createExcelFile(String filePath) throws IOException {
		String ext = Files.getFileExtension(filePath)
		File file = new File(filePath)
		if (file.exists()) {
			file.delete()
			KeywordUtil.logInfo('Deleted the existing file')
		}
		FileOutputStream fileOutputStream = new FileOutputStream(filePath)
		Workbook workbook = ext.equalsIgnoreCase("xlsx") ? new XSSFWorkbook() : new HSSFWorkbook()

		workbook.createSheet()
		workbook.write(fileOutputStream)
		fileOutputStream.flush()
		fileOutputStream.close()
	}

	/**
	 * Add a default sheet to workbook
	 *
	 * @param workbook the workbook that needs to add new default sheet
	 */
	@CompileStatic
	@Keyword
	static void createExcelSheet(Workbook workbook) {
		workbook.createSheet()
	}

	/**
	 * Add a new sheet to workbook
	 *
	 * @param workbook the workbook that needs to add new sheet
	 * @param sheetName the name of new sheet
	 */
	@CompileStatic
	@Keyword
	static void createExcelSheet(Workbook workbook, String sheetName) {
		List<String> sheetNames = getSheetNames(workbook)
		if (sheetNames.contains(sheetName)) {
			KeywordUtil.logInfo('That name is already taken. Please try another one.')
			return
		}
		workbook.createSheet(WorkbookUtil.createSafeSheetName(sheetName))
	}

	/**
	 * Get name of sheets in workbook
	 *
	 * @param workbook
	 * @return list of sheet's name
	 */
	@CompileStatic
	@Keyword
	static List<String> getSheetNames(Workbook workbook) {
		List<String> sheetNames = new ArrayList<>()
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheetNames.add(workbook.getSheetName(i))
		}
		return sheetNames
	}

	/**
	 * Add multiple sheets to workbook
	 *
	 * @param workbook the workbook that needs to add new sheets
	 * @param sheetNames the list of sheet names
	 */
	@CompileStatic
	@Keyword
	static void createExcelSheets(Workbook workbook, List<String> sheetNames) {
		for (String sheetName : sheetNames) {
			createExcelSheet(workbook, sheetName)
		}
	}

	/**
	 * Get workbook from existing excel file
	 *
	 * @param filePath location path of excel file
	 * @return workbook
	 */
	@CompileStatic
	@Keyword
	static Workbook getWorkbook(String filePath) throws IOException {
		File xlFile = new File(filePath)
		FileInputStream fis = new FileInputStream(xlFile)
		return Files.getFileExtension(filePath).equalsIgnoreCase("xlsx") ?
				new XSSFWorkbook(fis) : new HSSFWorkbook(fis)
	}

	/**
	 * Create new workbook
	 *
	 * @param filePath location path of excel file
	 * @return new workbook
	 */
	@CompileStatic
	@Keyword
	static Workbook createWorkbook(String filePath) {
		return Files.getFileExtension(filePath).equalsIgnoreCase("xlsx") ? new XSSFWorkbook() : new HSSFWorkbook()
	}


	/**
	 * Save workbook into file
	 *
	 * @param filePath the location of excel file that will be written
	 * @param workbook the workbook contains data
	 */
	@CompileStatic
	@Keyword
	static void saveWorkbook(String filePath, Workbook workbook) {
		workbook.getCreationHelper().createFormulaEvaluator().evaluateAll()
		FileOutputStream fileOutputStream = new FileOutputStream(filePath)
		workbook.write(fileOutputStream)
		fileOutputStream.flush()
		fileOutputStream.close()
	}

	/**
	 * Set value to cell
	 *
	 * @param cell target cell needs to be set value
	 * @param value value with any data type (String, Numeric, Boolean, Date with format MM/dd/yyyy,
	 * 					formula with character "=" at the beginning of the string
	 * @Example setValueToCell(cell, 123) || setValueToCell(cell, "value@123") || setValueToCell(cell, "=COUNTA(C2:C12)")
	 */
	static void setValueToCell(Cell cell, def value) {
		if (cell == null || value == null) {
			return
		}
		String type = value.getClass().getSimpleName()
		switch(type) {
			case "String":
				if (String.valueOf(value).equalsIgnoreCase("")) {
					cell.setCellType(Cell.CELL_TYPE_BLANK)
					cell.setCellValue("")
				} else if (String.valueOf(value).take(1)==("=")) {
					cell.setCellType(Cell.CELL_TYPE_FORMULA)
					cell.setCellFormula(String.valueOf(value).substring(1))
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING)
					cell.setCellValue(String.valueOf(value))
				}
				break
			case "Boolean":
				cell.setCellType(Cell.CELL_TYPE_BOOLEAN)
				cell.setCellValue((Boolean) value)
				break
			case "Integer":
			case "Long":
			case "Float":
			case "Double":
			case "BigDecimal":
				cell.setCellType(Cell.CELL_TYPE_NUMERIC)
				cell.setCellValue((Double) value)
				break
			case "Date":
				cell.setCellType(Cell.CELL_TYPE_NUMERIC)
				Workbook workbook = cell.getSheet().getWorkbook()
				short dateFormat = workbook.createDataFormat().getFormat("MM/dd/yyyy")
				CellStyle cellStyle =  workbook.createCellStyle()
				cellStyle.setDataFormat(dateFormat)
				cell.setCellStyle(cellStyle)
				cell.setCellValue((Date) value)
				break
			default:
				cell.setCellType(Cell.CELL_TYPE_STRING)
				cell.setCellValue(value.toString())
				break
		}
	}

	/**
	 * Set value to cell by cell index
	 *
	 * @param sheet sheet in workbook
	 * @param colIndex column index starts by 0
	 * @param rowIndex row index starts by 0
	 * @param value value with any data type (String, Numeric, Boolean, Date with format MM/dd/yyyy,
	 * 					formula with character "=" at the beginning of the string
	 * @Example setValueToCellByIndex(sheet, 1, 2, 123)
	 */
	@CompileStatic
	@Keyword
	static void setValueToCellByIndex(Sheet sheet, int rowIndex, int colIndex, def value) {
		if(colIndex < 0 || rowIndex < 0) {
			KeywordUtil.logInfo("Column Index or Row Index should >= 0")
			return
		}
		Row row = sheet.getRow(rowIndex)
		if (row == null) row = sheet.createRow(rowIndex)
		Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
		setValueToCell(cell, value)
	}

	/**
	 * Set value to cell by cell Address
	 *
	 * @param sheet sheet in workbook
	 * @param cellAddress cell address in sheet
	 * @param value value with any data type (String, Numeric, Boolean, Date with format MM/dd/yyyy,
	 * 					formula with character "=" at the beginning of the string
	 * @Example setValueToCellByAddress(sheet, "A10", 123)
	 */
	@CompileStatic
	@Keyword
	static void setValueToCellByAddress(Sheet sheet, String cellAddress, def value) {
		if(cellAddress == null || cellAddress.equalsIgnoreCase("")) {
			return
		}
		CellReference cr = new CellReference(cellAddress)
		Row row = sheet.getRow(cr.getRow())
		if (row == null) row = sheet.createRow(cr.getRow())
		Cell cell = row.getCell(cr.getCol(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)
		setValueToCell(cell, value)
	}

	/**
	 * Set multiple values to multiple cells with cell addresses
	 *
	 * @param sheet sheet in workbook
	 * @param content map object with "key" = "cell address" and "value" = "value" with any data type
	 * 					(String, Numeric, Boolean, Date with format MM/dd/yyyy,
	 * 					formula with character "=" at the beginning of the string
	 * @Example
	 * 		content.putAt("C5",12)
	 * 		content.putAt("C6",true)
	 * 		setValueToCellByAddresses(sheet, content)
	 */
	@CompileStatic
	@Keyword
	static void setValueToCellByAddresses(Sheet sheet, Map content) {
		for(String cellAddress: content.keySet()) {
			setValueToCellByAddress(sheet, cellAddress, content.get(cellAddress))
		}
	}

	/**
	 * Get an Excel sheet
	 *
	 * @param filePath the location of excel file
	 * @param sheetIndex the index of returned sheet
	 * @return a sheet object
	 * */
	@CompileStatic
	@Keyword
	static Sheet getExcelSheet(String filePath, int sheetIndex = 0) throws Exception {
		FileInputStream fs = new FileInputStream(filePath)
		String[] arrPath = filePath.split("\\.")
		String fileExt = arrPath[arrPath.length - 1]
		Workbook workbook = fileExt.equalsIgnoreCase("xlsx") ? new XSSFWorkbook(fs) : new HSSFWorkbook(fs)
		return workbook.getSheetAt(sheetIndex)
	}

	/**
	 * Get an Excel sheet from an opening workbook
	 *
	 * @param wbs the opening workbook
	 * @param sheetName the sheet name of the workbook
	 * @return a sheet object.
	 * @example getExcelSheet(workbook1,"Sheet1")
	 * */
	@CompileStatic
	@Keyword
	static Sheet getExcelSheet(Workbook wbs, String sheetName = null) throws Exception {
		return wbs.getSheet(sheetName)
	}

	/**
	 * Get an Excel sheet by name
	 *
	 * @param filePath the location of excel file
	 * @param sheetName the name of returned sheet
	 * @return a sheet object
	 * */
	@CompileStatic
	@Keyword
	static Sheet getExcelSheetByName(String filePath, String sheetName) throws Exception {
		FileInputStream fs = new FileInputStream(filePath)
		String[] arrPath = filePath.split("\\.")
		String fileExt = arrPath[arrPath.length - 1]
		Workbook workbook = fileExt.equalsIgnoreCase("xlsx") ? new XSSFWorkbook(fs) : new HSSFWorkbook(fs)
		return workbook.getSheet(sheetName)
	}

	/**
	 * Get cell by index
	 *
	 * @param sheet the sheet object that contains cell
	 * @param rowIdx row index of the cell
	 * @param colIdx column index of the cell
	 * @return a cell object (this function returns null if the object is null)
	 * */
	@CompileStatic
	@Keyword
	static Cell getCellByIndex(Sheet sheet, int rowIdx, int colIdx){
		Row row = sheet.getRow(rowIdx)
		Cell cell = row.getCell(colIdx)
		return cell
	}

	/**
	 * Get cell by address
	 *
	 * @param sheet the sheet object that contains cell
	 * @param cellAddress the address of cell
	 * @return a cell object (this function returns null if the object is null)
	 * */
	@CompileStatic
	@Keyword
	static Cell getCellByAddress(Sheet sheet, String cellAddress){
		CellReference cr = new CellReference(cellAddress)
		Row row = sheet.getRow(cr.getRow())
		Cell cell = row.getCell(cr.getCol())
		return cell
	}

	/**
	 * Locate the first position of the cell based on the cell content
	 * @param sheet the sheet object contains the cell
	 * @param cellContent the content to locate the cell. If the cell is in Date format, the cellContent should be in (MM/dd/yyyy), e.g ("10/01/2019")
	 * @return a list of integer (row, column)
	 */
	@CompileStatic
	@Keyword
	static List<Integer> locateCell(Sheet sheet, def cellContent) {
		List<Integer> indexes = new ArrayList<>()
		for (Row row : sheet) {
			for (Cell cell : row) {
				def cellValue = ""
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						if(DateUtil.isCellDateFormatted(cell)){
							DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")
							cellValue= sdf.format(cell.getDateCellValue())
							break
						}
						cellValue=cell.getNumericCellValue().toString()
						if (cellValue.split("\\.").size ()>0){
							if (cellValue.split("\\.")[1] == "0"){
								cellValue=cellValue.replace(".0","")
							}
						}
						break
					case Cell.CELL_TYPE_STRING:
						cellValue= cell.getStringCellValue().toString()
						break
					case Cell.CELL_TYPE_BLANK:
						cellValue= ""
						break
					case Cell.CELL_TYPE_BOOLEAN:
						cellValue= cell.getBooleanCellValue().toString()
						break
					case Cell.CELL_TYPE_FORMULA:
						cellValue= cell.getCellFormula().toString()
						break
				}
				cellContent=cellContent.toString()
				if (cellContent == cellValue) {
					indexes.add(row.getRowNum())
					indexes.add(cell.getColumnIndex())
					return indexes
				}
			}
		}
		return [-1, -1]
	}

	/**
	 * Read data from a single cell
	 *
	 * @param cell the cell that needs to read data
	 * @return the value of cell. If the cell is in date format, the returned value is in Date format "MM/dd/yyyy".
	 * */
	@CompileStatic
	@Keyword
	static def getCellValue(Cell cell){
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				try{
					if(DateUtil.isCellDateFormatted(cell)){
						DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")
						return sdf.format(cell.getDateCellValue())
					}
					else{
						if (cell.getNumericCellValue().toString().split("\\.").size ()>0){
							if (cell.getNumericCellValue().toString().split("\\.")[1] == "0"){
								return Integer.parseInt(cell.getNumericCellValue().toString().replace(".0",""))
							}
							else
								return cell.getNumericCellValue()
						}
						else {
							return cell.getNumericCellValue()
						}
					}
				}
				catch (XmlValueDisconnectedException ignored) {
					if (cell.getNumericCellValue().toString().split("\\.").size ()>0){
						if (cell.getNumericCellValue().toString().split("\\.")[1] == "0"){
							return Integer.parseInt(cell.getNumericCellValue().toString().replace(".0",""))
						}
						else {
							return cell.getNumericCellValue()
						}
					}
					else {
						return cell.getNumericCellValue()
					}
				}

				break
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue()
				break
			case Cell.CELL_TYPE_BLANK:
				return ""
				break
			case Cell.CELL_TYPE_BOOLEAN:
				return cell.getBooleanCellValue()
				break
			case Cell.CELL_TYPE_FORMULA:
				switch(cell.getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC:
					if (cell.getNumericCellValue().toString().split("\\.").size ()>0){
						if (cell.getNumericCellValue().toString().split("\\.")[1] == "0"){
							return Integer.parseInt(cell.getNumericCellValue().toString().replace(".0",""))
						}
						else
							return cell.getNumericCellValue()
					}
					else {
						return cell.getNumericCellValue()
					}
					case Cell.CELL_TYPE_STRING:
					return cell.getStringCellValue()
					break
				}
		}
	}

	/**
	 * Get cell value by index
	 *
	 * @param sheet the sheet object that contains cell
	 * @param rowIdx row index of the cell
	 * @param colIdx column index of the cell
	 * @return a cell value
	 * */
	@CompileStatic
	@Keyword
	static def getCellValueByIndex(Sheet sheet, int rowIdx, int colIdx){
		Cell cell = getCellByIndex(sheet, rowIdx, colIdx)
		if (null !=cell) {
			return getCellValue(cell)
		}
		else return ""
	}

	/**
	 * Get cell position by address
	 *
	 * @param sheet the sheet object that contains cell
	 * @param cellAddress the address of cell
	 * @return a map contains position of the cell, e.g: ["column":2, "row": 1]
	 * */
	@CompileStatic
	@Keyword
	static Map getCellIndexByAddress(Sheet sheet, String cellAddress){
		CellReference cr = new CellReference(cellAddress)
		int rowInd =  cr.getRow()
		int colInd = cr.getCol()
		return ["column": colInd, "row" : rowInd]
	}

	/**
	 * Get cell value by address
	 *
	 * @param sheet the sheet object that contains cell
	 * @param cellAddress the address of the cell
	 * @return a cell value
	 * */
	@CompileStatic
	@Keyword
	static def getCellValueByAddress(Sheet sheet, String cellAddress){
		Cell cell = getCellByAddress(sheet, cellAddress)
		if (null !=cell) {
			return getCellValue(cell)
		}
		else return ""
	}

	/**
	 * Get row index by cell content
	 *
	 * @param sheet the sheet object that contains the row
	 * @param cellContent the content of the cell
	 * @param columnIdxForCell the column index of the cell
	 * @return the row index
	 * */
	@CompileStatic
	@Keyword
	static int getRowIndexByCellContent(Sheet sheet, String cellContent, int columnIdxForCell){
		for(Row row: sheet){
			Cell cell = row.getCell(columnIdxForCell)
			if (null != cell){
				boolean match = getCellValue(cell).toString().trim() == cellContent
				if(match){
					return cell.getRowIndex()
				}
			}
		}
		return -1
	}

	/**
	 * Get the excel data to a list of rows
	 *
	 * @param sheet the sheet object to get the data
	 * @param startRow the start row to get the data
	 * @param endRow the end row to get the data
	 * @return a list of rows
	 * */
	@CompileStatic
	@Keyword
	static List<Row> getTableContent(Sheet sheet, int startRow, int endRow) {
		List<Row> rowCollection =[]
		for (int i = startRow; i <= endRow; i++){
			Row r = sheet.getRow(i)
			rowCollection.add(r)
		}
		return rowCollection
	}

	/**
	 * Get data rows from a list of row indexes
	 *
	 * @param sheet the sheet object contains the rows
	 * @param rowIndexes the list of row indexes
	 * @return a list of rows
	 * */
	@CompileStatic
	@Keyword
	static List<Row> getDataRows(Sheet sheet, List<Integer> rowIndexs){
		List rowCollection = []
		Row row
		for (int i = 0; i < rowIndexs.size(); i++){
			row = sheet.getRow(rowIndexs[i])
			if (null == row){
				sheet.createRow(i)
			}
			rowCollection.add(row)
		}
		return rowCollection
	}

	/**
	 * Get a map of {{key1:value1},...{keyn:valuen}} from a row of keys and a row of values of a sheet
	 *
	 * @param sheet the sheet object that contains the row
	 * @param keyRowIdx the index of the header row
	 * @param valueRowIdx the index of row needs to get the data
	 * @return a map from a row of keys and a row of values of a sheet, e.g: {{key1:value1},...{keyn:valuen}}
	 * */
	@CompileStatic
	@Keyword
	static Map getMapOfKeyValueRows (Sheet sheet, int keyRowIdx, int valueRowIdx){
		List<Row> valueWithKey = getDataRows(sheet, [keyRowIdx, valueRowIdx])
		Map mCells = [:]
		String cellValue
		for(Cell cell: valueWithKey[0]){
			String keyRow = getCellValue(cell).toString().trim()
			keyRow = keyRow.replaceAll("[\n]", "")
			int index = cell.getColumnIndex()
			if (null == valueWithKey[1]){
				return mCells
			}
			if (null!=valueWithKey[1].getCell(index)) {
				cellValue = getCellValue(valueWithKey[1].getCell(index)).toString()
			}
			else {
				cellValue=""
			}
			mCells.put(keyRow, cellValue)
		}
		return mCells
	}

	/**
	 * Get cell values by addresses
	 *
	 * @param sheet the sheet object that contains cells
	 * @param topLeftAddress the address of the first cell
	 * @param rightBottomAddress the address of the last cell
	 * @return a list of cell values
	 * */
	@CompileStatic
	@Keyword
	static List<String> getCellValueByRangeAddress(Sheet sheet, String topLeftAddress, String rightBottomAddress){
		int rowInd1 = Integer.valueOf(getCellIndexByAddress(sheet, topLeftAddress)["row"].toString())
		int colInd1 = Integer.valueOf(getCellIndexByAddress(sheet, topLeftAddress)["column"].toString())
		int rowInd2 = Integer.valueOf(getCellIndexByAddress(sheet, rightBottomAddress)["row"].toString())
		int colInd2 = Integer.valueOf(getCellIndexByAddress(sheet, rightBottomAddress)["column"].toString())
		return getCellValuesByRangeIndexes(sheet, rowInd1, colInd1, rowInd2, colInd2)
	}

	/**
	 * Get cell values by indexes
	 *
	 * @param sheet the sheet object that contains cells
	 * @param rowInd1 the row index of the first cell
	 * @param colInd1 the column index of the first cell
	 * @param rowInd2 the row index of the last cell
	 * @param colInd2 the column index of the last cell
	 * @return a list of cell values
	 * */
	@CompileStatic
	@Keyword
	static List<String> getCellValuesByRangeIndexes(Sheet sheet, int rowInd1, int colInd1, int rowInd2, int colInd2){
		List<String> cellValues = new ArrayList<>()
		String cellValue
		if (colInd2-colInd1<0 || rowInd2-rowInd1<0) {
			KeywordUtil.logInfo('The addresses is not valid.')
			return null
		}
		for (int i=rowInd1;i<=rowInd2;i++){
			for (int j = colInd1; j <= colInd2; j++){
				if (null != sheet.getRow(i)){
					Cell cell = sheet.getRow(i).getCell(j)
					if (null != cell){
						cellValue=getCellValue(cell).toString()
					}
					else {
						cellValue=""
					}
				}
				else {
					cellValue=""
				}
				cellValues.add(cellValue)
			}
		}
		return cellValues
	}

	/**
	 * Get map of columns data
	 *
	 * @param sheet the sheet object that contains the row
	 * @param columnIndexes indexes of columns
	 * @return map of a columns data contains the column index and cell data
	 * */
	@CompileStatic
	@Keyword
	static def getColumnsByIndex(Sheet sheet, List<Integer> columnIndexes) {
		Map<Integer, List<Cell>> columns = new HashMap<>()
		for (int index : columnIndexes) {
			List<Cell> cells = new ArrayList<>()
			int lastRowNum = sheet.getLastRowNum()
			for (int i= 0; i <= lastRowNum; i ++){
				if (sheet.getRow(i) == null) sheet.createRow(i)
				cells.add(sheet.getRow(i).getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
			}
			columns.put(index, cells)
		}
		return columns
	}

	/**
	 * Compare value of two excel workbooks
	 *
	 * @param workbook1 the first workbook need to compare
	 * @param workbook2 the second workbook need to compare
	 * @param isValueOnly true if check value and type only regardless of cell style, false if not
	 * @return true if two excel workbooks equal, false if not
	 * */
	@CompileStatic
	@Keyword
	static boolean compareTwoExcels(Workbook workbook1, Workbook workbook2, boolean isValueOnly = true) {
		int numberOfSheets1 = workbook1.getNumberOfSheets()
		int numberOfSheets2 = workbook2.getNumberOfSheets()

		if (numberOfSheets1 != numberOfSheets2) {
			KeywordUtil.logInfo("The number of sheets does not match.")
			return false
		}
		boolean equalExcels = true

		for (int i = 0; i < numberOfSheets1; i++) {
			Sheet sheet1 = workbook1.getSheetAt(i)
			Sheet sheet2 = workbook2.getSheetAt(i)
			if (sheet1.getSheetName() != sheet2.getSheetName()) {
				KeywordUtil.logInfo("Sheet names " + i + " does not match - Name of sheet 1 is " + sheet1.getSheetName() +
						", name of sheet2 is  " + sheet2.getSheetName())
				return false
			}

			if (!compareTwoSheets(sheet1, sheet2, isValueOnly)) {
				equalExcels = false
				KeywordUtil.logInfo("Sheet " + i + " not equal")
				break
			}
		}
		return equalExcels
	}

	/**
	 * Compare two excel sheets
	 *
	 * @param sheet1 the first sheet need to compare
	 * @param sheet2 the second sheet need to compare
	 * @param isValueOnly true if check value and type only regardless of cell style, false if not
	 * @return true if two excel sheets equal, false if not
	 * */
	@CompileStatic
	@Keyword
	static boolean compareTwoSheets(Sheet sheet1, Sheet sheet2, boolean isValueOnly = true) {
		int firstRowNum1 = sheet1.getFirstRowNum()
		int lastRowNum1 = sheet1.getLastRowNum()
		int firstRowNum2 = sheet2.getFirstRowNum()
		int lastRowNum2 = sheet2.getLastRowNum()

		if (firstRowNum1 != firstRowNum2 || lastRowNum1 != lastRowNum2) {
			KeywordUtil.logInfo("The number of rows does not match")
			return false
		}

		boolean equalSheets = true

		for (int i = firstRowNum1; i <= lastRowNum1; i++) {
			Row row1 = sheet1.getRow(i)
			Row row2 = sheet2.getRow(i)

			if (!compareTwoRows(row1, row2, isValueOnly)) {
				equalSheets = false
				KeywordUtil.logInfo("Row " + i + " not equals")
				break
			}
		}
		return equalSheets
	}

	/**
	 * Compare two excel rows
	 *
	 * @param row1 the first row need to compare
	 * @param row2 the second row need to compare
	 * @param isValueOnly true if check value and type only regardless of cell style, false if not
	 * @return true if two excel rows equal, false if not
	 * */
	@CompileStatic
	@Keyword
	static boolean compareTwoRows(Row row1, Row row2, boolean isValueOnly = true) {
		if (row1 == null && row2 == null) return true
		else if ((row1 == null) || (row2 == null)) return false

		int firstCellNum1 = row1.getFirstCellNum()
		int lastCellNum1 = row1.getLastCellNum()
		int firstCellNum2 = row2.getFirstCellNum()
		int lastCellNum2 = row2.getLastCellNum()

		if (firstCellNum1 != firstCellNum2 || lastCellNum1 != lastCellNum2) {
			KeywordUtil.logInfo("The number of rows does not match")
			return false
		}

		boolean equalRows = true
		for (int i = firstCellNum1; i <= lastCellNum1; i++) {
			Cell cell1 = row1.getCell(i)
			Cell cell2 = row2.getCell(i)
			if (!compareTwoCells(cell1, cell2, isValueOnly)) {
				equalRows = false
				KeywordUtil.logInfo("Cell " + i + " not equal - Value of cell 1 is " + cell1 + ", value of cell 2 is " + cell2 )
				return equalRows
			}
		}
		return equalRows
	}

	/**
	 * Compare two excel cells
	 *
	 * @param cell1 the first cell need to compare
	 * @param cell2 the second cell need to compare
	 * @param isValueOnly true if check value and type only regardless of cell style, false if not
	 * @return true if two excel cells equal, false if not
	 * */
	@CompileStatic
	@Keyword
	static boolean compareTwoCells(Cell cell1, Cell cell2, boolean isValueOnly = true) {
		if (cell1 == null && cell2 == null) return true
		else if (cell1 == null || cell2 == null) return false

		boolean equalCells = false
		if (cell1.getCellType() == cell2.getCellType()) {
			if ((isValueOnly) || (cell1.getCellStyle() == cell2.getCellStyle())) {
				switch (cell1.getCellType()) {
					case Cell.CELL_TYPE_FORMULA:
						equalCells = cell1.getCellFormula() == cell2.getCellFormula()
						break
					case Cell.CELL_TYPE_NUMERIC:
						equalCells = cell1.getNumericCellValue() == cell2.getNumericCellValue()
						break
					case Cell.CELL_TYPE_BLANK:
						equalCells = cell2.getCellType() == Cell.CELL_TYPE_BLANK
						break
					case Cell.CELL_TYPE_BOOLEAN:
						equalCells = cell1.getBooleanCellValue() == cell2.getBooleanCellValue()
						break
					case Cell.CELL_TYPE_ERROR:
						equalCells = cell1.getErrorCellValue() == cell2.getErrorCellValue()
						break
					case Cell.CELL_TYPE_STRING:
						equalCells = cell1.getStringCellValue() == cell2.getStringCellValue()
						break
					default:
						equalCells = cell1.getStringCellValue() == cell2.getStringCellValue()
						break
				}
			} else {
				System.out.println("Style does not match. Style of cell 1 is " + cell1.getCellStyle() + ", style of cell 2 is " + cell2.getCellStyle())
			}
		} else {
			System.out.println("Type does not match. Type of cell 1 is " + cell1.getCellType() + ", type of cell 2 is " + cell2.getCellType())
		}
		return equalCells
	}

	/**
	 * Get cell formula
	 *
	 * @param cell the cell object that need to get formula
	 * @return a formula if cell is formula type, cell value if not
	 */
	static def getCellFormula(Cell cell) {
		if(cell.getCellType() == Cell.CELL_TYPE_FORMULA)
			return "=" + cell.getCellFormula()
		else return getCellValue(cell)
	}
}

