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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import internal.GlobalVariable as GlobalVariable
import com.google.common.io.Files as Files
import org.apache.poi.hssf.usermodel.HSSFWorkbook as HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook as Workbook
import org.apache.poi.xssf.usermodel.XSSFSheet as XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import java.io.FileNotFoundException as FileNotFoundException
import java.io.FileOutputStream as FileOutputStream
import java.io.IOException as IOException
import org.apache.poi.ss.util.WorkbookUtil as WorkbookUtil
import org.apache.poi.ss.usermodel.Cell as Cell
import com.kms.katalon.keyword.excel.ExcelKeywords as ExcelKeywords

String fileName = System.getProperty('user.home') + '\\Documents\\excel2.xlsx'

/*//ExcelKeywords.createExcelFile(fileName);
//
//Workbook workbook = ExcelKeywords.getWorkbook(fileName);
//ExcelKeywords.createExcelSheet(workbook);
//ExcelKeywords.createExcelSheet(workbook, "Sheet12635");
//ExcelKeywords.createExcelSheets(workbook, Arrays.asList("Sheet788", "Sheet0987"));
//ExcelKeywords.saveWorkbook(fileName, workbook);

//Workbook workbook1 = ExcelKeywords.getWorkbook(fileName)
//Map<Integer, List<Cell>> columns = ExcelKeywords.getColumnsByIndex(workbook1.getSheet("Sheet0"), Arrays.asList(0, 1))
//for (Map.Entry<Integer, List<Cell>> entry : columns.entrySet()) {
//	KeywordUtil.logInfo("===========================")
//	KeywordUtil.logInfo("Column " + entry.key)
//	List<Cell> cells = entry.value
//	for (int i = 0; i < cells.size(); i ++) {
//		KeywordUtil.logInfo(String.format("Cell at row %s is %s", i, ExcelKeywords.getCellValue(cells.get(i)).toString()))
//	}
//}
*/
String fileName2 = System.getProperty("user.home") + "\\Documents\\excel6.xlsx"

Workbook workbook2 = ExcelKeywords.getWorkbook(fileName)
Workbook workbook3 = ExcelKeywords.getWorkbook(fileName2)

KeywordUtil.logInfo(ExcelKeywords.compare2Excels(workbook2, workbook3, true).toString())
//String fileName2 = System.getProperty('user.home') + '\\Documents\\File02.xls'
//
//Workbook workbook2 = ExcelKeywords.getWorkbook(fileName)
//
//Workbook workbook3 = ExcelKeywords.getWorkbook(fileName2)
//
//Sheet sheet1 = workbook2.getSheet('File01Sheet02')
//
//Cell cell1 = ExcelKeywords.getCellByIndex(sheet1, 0, 0)
//
//Sheet sheet2 = workbook3.getSheet('File02Sheet01')
//
//Cell cell2 = ExcelKeywords.getCellByIndex(sheet2, 0, 0)
//
//KeywordUtil.logInfo('Compare: ' + ExcelKeywords.compareTwoCells(cell1, cell2, true))


