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
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.keyword.excel.ExcelKeywords as ExcelKeywords

/**
 * Example for using some additional get data from excel
 *  Scenarios:
 *  - locateCell: Locate Cell based on cell content
 *  - getTableContent: Get all value of a table based on start row and end row
 *  - getDataRows: Get all value of a list of rows based on provided row indexes
 *  - getCellValuesByRangeIndexes: Get value of some cells based on start row,column  and end row,column
 *  - getCellValueByRangeAddress: Get value of some cells based on top left cell address and right bottom cell address
 *  - getRowIndexByCellContent: Get row index based on cell content in the provided column
 *  - getColumnsByIndex: Get all value of a list of columns based on provided column indexes
 *  - getMapOfKeyValueRows: Get a map of {{key1:value1},...{keyn:valuen}} from a row of keys and a row of values of a sheet base on row indexes
 *  - getCellIndexByAddress: Get row and column of a cell based on cell address
 */
sheet01 = ExcelKeywords.getExcelSheet("Data Files/SampleData.xlsx")

println ExcelKeywords.locateCell(sheet01, 1001)
println ExcelKeywords.locateCell(sheet01, "Burns")
println ExcelKeywords.locateCell(sheet01, '(B2&" "&C2)')
println ExcelKeywords.locateCell(sheet01, "02/14/2001")
println ExcelKeywords.locateCell(sheet01, 60)
println ExcelKeywords.locateCell(sheet01, 'IF(F2>=90,"A",IF(F2>=70,"B",IF(F2>=50,"C","D")))')
println ExcelKeywords.locateCell(sheet01, 16.67)
println ExcelKeywords.locateCell(sheet01, 0.15)
println ExcelKeywords.getTableContent(sheet01, 0, 10)
println ExcelKeywords.getDataRows(sheet01,[1,2,3])
println ExcelKeywords.getCellValuesByRangeIndexes(sheet01, 1,3,2,6)
println ExcelKeywords.getCellValueByRangeAddress(sheet01, "A1", "B3")
println ExcelKeywords.getRowIndexByCellContent(sheet01, "Burns", 2)
println ExcelKeywords.getRowIndexByCellContent(sheet01, "02/14/2001", 4)
println ExcelKeywords.getColumnsByIndex(sheet01,[1,2,3])
println ExcelKeywords.getMapOfKeyValueRows(sheet01,0,1)
println ExcelKeywords.getCellIndexByAddress(sheet01, "A10")
