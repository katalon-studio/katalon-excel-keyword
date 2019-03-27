import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import java.io.File as File
import com.kms.katalon.keyword.excel.ExcelKeywords as ExcelKeywords

/**
 * Example for interact with excel using custom keywords
 *  Scenarios:
 *  - Create two files: File01.xlsx, File02.xlsx
 *  - Add some content to file File01
 *  - Read data from file File01, then set these values to File02 
 *  - Compare File01 with File02 (cell, row, sheet, file)
 *  - Clean up data (delete all files)
 */
String excelFile01 = 'Data Files\\File01.xlsx'
String excelFile02 = 'Data Files\\File02.xls'

// Create two files
ExcelKeywords.createExcelFile(excelFile01)
ExcelKeywords.createExcelFile(excelFile02)

// Verify files are created
File file1 = new File(excelFile01)
File file2 = new File(excelFile02)
assert file1.exists() == true
assert file2.exists() == true

// Create some new sheets for File01
workbook01 = ExcelKeywords.getWorkbook(excelFile01)
ExcelKeywords.createExcelSheet(workbook01) // Create default sheet 
ExcelKeywords.createExcelSheets(workbook01, ['File01Sheet01', 'File01Sheet02']) // Create list of sheets with name
ExcelKeywords.saveWorkbook(excelFile01, workbook01)

// Create a new sheet for File02
workbook02 = ExcelKeywords.getWorkbook(excelFile02)
ExcelKeywords.createExcelSheet(workbook02, 'File02Sheet01') // Create a sheet with name
ExcelKeywords.saveWorkbook(excelFile02, workbook02)

// Verify sheets created
String[] ExpectedListSheetFile1 = ['Sheet0','Sheet1','File01Sheet01', 'File01Sheet02']
String[] ExpectedListSheetFile2 = ['Sheet0','File02Sheet01']
workbookFile01 = ExcelKeywords.getWorkbook(excelFile01) // get latest workbook File01
workbookFile02 = ExcelKeywords.getWorkbook(excelFile02) // get latest workbook File02
assert ExcelKeywords.getSheetNames(workbookFile01) == ExpectedListSheetFile1
assert ExcelKeywords.getSheetNames(workbookFile02) == ExpectedListSheetFile2

// Write some data to File01Sheet01 in File01
workbook01 = ExcelKeywords.getWorkbook(excelFile01)
sheet01 = ExcelKeywords.getExcelSheet(workbook01, 'File01Sheet01')
ExcelKeywords.setValueToCellByIndex(sheet01, 0, 0, 'Fruites')
ExcelKeywords.setValueToCellByIndex(sheet01, 1, 0, 'Apple')
ExcelKeywords.setValueToCellByIndex(sheet01, 2, 0, 'Orange')
ExcelKeywords.setValueToCellByAddress(sheet01, 'B1', 'Price')
ExcelKeywords.setValueToCellByAddress(sheet01, 'B2', 10000)
ExcelKeywords.setValueToCellByAddress(sheet01, 'B3', 15000)
ExcelKeywords.setValueToCellByAddress(sheet01, 'C1', 'Quantity')
ExcelKeywords.setValueToCellByAddress(sheet01, 'C2', 5)
ExcelKeywords.setValueToCellByAddress(sheet01, 'C3', 6)
ExcelKeywords.setValueToCellByAddress(sheet01, 'D1', 'Total Prices')
ExcelKeywords.setValueToCellByAddress(sheet01, 'D2', '=B2*C2')
ExcelKeywords.setValueToCellByAddress(sheet01, 'D3', '=B3*C3')
ExcelKeywords.setValueToCellByAddress(sheet01, 'E1', 'Booking date')
ExcelKeywords.setValueToCellByAddress(sheet01, 'E2', "02/28/2019")
ExcelKeywords.setValueToCellByAddress(sheet01, 'E3', "02/29/2019")

// Write some data (random positions) to File01Sheet02 in File01
sheet02 = ExcelKeywords.getExcelSheet(workbook01, 'File01Sheet02')
paragraph = "This is a test paragraph:...\n " +
"Sample test case for interact with excel using Kalaton - Automation testing tool.\n "
Map content = new HashMap<>()
content.putAt("A1",12) // Set value is number
content.putAt("A1",-69) // Set value is number
content.putAt("B2",true) // Set value is boolean
content.putAt("C3",paragraph) // Set value is paragraph
content.putAt("C4","Sample text with special character: `~!@#%^&*()_+-")  // Set value is string
content.putAt("C5","") // Set value is none
content.putAt("C6"," ") // Set value is blank
content.putAt("C7","02/29/2019") // Set value is date
content.putAt("D8","=COUNTA(C1:C8)") // Set value is formula
ExcelKeywords.setValueToCellByAddresses(sheet02, content)

// Save data of File01
ExcelKeywords.saveWorkbook(excelFile01, workbook01)

// Read data from File01, then copy to File02
workbook01 = ExcelKeywords.getWorkbook(excelFile01)
workbook02 = ExcelKeywords.getWorkbook(excelFile02)
sheet01 = ExcelKeywords.getExcelSheet(workbook01, 'File01Sheet01')
sheet02 = ExcelKeywords.getExcelSheet(workbook01, 'File01Sheet02')
cellA1 = ExcelKeywords.getCellByIndex(sheet01, 0, 0)
def A1 = ExcelKeywords.getCellValue(cellA1)
def A2 = ExcelKeywords.getCellValueByIndex(sheet01, 1, 0)
def A3 = ExcelKeywords.getCellValueByAddress(sheet01, 'A3')
cellB1 = ExcelKeywords.getCellByAddress(sheet01, 'B1')
def B1 = ExcelKeywords.getCellValue(cellB1)
def B2 = ExcelKeywords.getCellValueByIndex(sheet01, 1, 1)
def B3 = ExcelKeywords.getCellValueByAddress(sheet01, 'B3')
cellC1 = ExcelKeywords.getCellByAddress(sheet01, 'C1')
def C1 = ExcelKeywords.getCellValue(cellC1)
def C2 = ExcelKeywords.getCellValueByIndex(sheet01, 1, 2)
def C3 = ExcelKeywords.getCellValueByAddress(sheet01, 'C3')
cellD1 = ExcelKeywords.getCellByAddress(sheet01, 'D1')
def D1 = ExcelKeywords.getCellValue(cellD1)
def D2 = ExcelKeywords.getCellValueByIndex(sheet01, 1, 3)
def D3 = ExcelKeywords.getCellValueByAddress(sheet01, 'D3')
cellE1 = ExcelKeywords.getCellByAddress(sheet01, 'E1')
def E1 = ExcelKeywords.getCellValue(cellE1)
def E2 = ExcelKeywords.getCellValueByIndex(sheet01, 1, 4)
def E3 = ExcelKeywords.getCellValueByAddress(sheet01, 'E3')
sheet03 = ExcelKeywords.getExcelSheet(workbook02, 'File02Sheet01')
ExcelKeywords.setValueToCellByAddress(sheet03, 'A1', A1)
ExcelKeywords.setValueToCellByAddress(sheet03, 'A2', A2)
ExcelKeywords.setValueToCellByAddress(sheet03, 'A3', A3)
ExcelKeywords.setValueToCellByIndex(sheet03, 0, 1, B1)
ExcelKeywords.setValueToCellByIndex(sheet03, 1, 1, B2)
ExcelKeywords.setValueToCellByIndex(sheet03, 2, 1, B3)
Map content02 = new HashMap<>()
content02.putAt("C1",C1) 
content02.putAt("C2",C2)
content02.putAt("C3",C3)
content02.putAt("D1",D1)
content02.putAt("D2",D2)
content02.putAt("D3",D3)
content02.putAt("E1",E1)
content02.putAt("E2",E2)
content02.putAt("E3",E3)
ExcelKeywords.setValueToCellByAddresses(sheet03, content02)

// Save data of File02Sheet01
ExcelKeywords.saveWorkbook(excelFile02, workbook02)

// Compare some data of File02Sheet01 vs File01Sheet01
// Compare cells
a = ExcelKeywords.compareTwoCells(cellA1,cellB1)

// Compare rows
for (int i = 0; i < 2; i++) {
			row01 = sheet01.getRow(i)
			row02 = sheet02.getRow(i)
			CompareRow = ExcelKeywords.compareTwoRows(row01,row02)
			println("Compare row "+ i +" of File01Sheet01 and File02Sheet01 is: " + CompareRow)
		}

// Compare sheets
CompareSheet = ExcelKeywords.compareTwoSheets(sheet01,sheet02)
println("Result of compare sheet01 of File01Sheet01 and File02Sheet01 is: " + CompareSheet)

// Compare workbook
CompareFile = ExcelKeywords.compareTwoExcels(workbook01,workbook02)
println("Result of compare File01 and File02 is: " + CompareFile)

// Delete file
file1.delete()
file2.delete()

