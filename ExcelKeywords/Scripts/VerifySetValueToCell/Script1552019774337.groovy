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
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.text.SimpleDateFormat
import com.kms.katalon.keyword.excel.ExcelKeywords

String SAMPLE_XLS_FILE_PATH = "./SetValueXLS.xls"
String SAMPLE_XLSX_FILE_PATH = "./SetValueXLSX.xlsx"
def paragraph = "Stir sugar, cream, and milk into a saucepan over low heat until sugar has dissolved. ...\n " +
"Transfer cream mixture to a pourable container such as a large measuring cup. ...\n " +
"Pour cold ice cream mix into an ice cream maker, turn on the machine, and churn according to manufacturer's directions, 20 to 25 minutes."
String date = "12/27/2019"
Date convertDate = new SimpleDateFormat("MM/dd/yyyy").parse(date)
Map content = new HashMap<>()
content.putAt("C4",convertDate)
content.putAt("C5",12)
content.putAt("C6",true)
content.putAt("C7",false)
content.putAt("C8","kem Vani@!\$#%")
content.putAt("C9","")
content.putAt("C10"," ")
content.putAt("C11","02/13/2019")
content.putAt("C12","=COUNTA(C5:C11)")

ExcelKeywords.createExcelFile(SAMPLE_XLS_FILE_PATH)
Workbook workbookXLS = ExcelKeywords.getWorkbook(SAMPLE_XLS_FILE_PATH)
ExcelKeywords.createExcelSheets(workbookXLS, Arrays.asList("van", "test"))
Sheet sheet1 = workbookXLS.getSheet("test")
Sheet sheet2 = workbookXLS.getSheet("van")

ExcelKeywords.setValueToCellByIndex(sheet1, 2, 2, -1.52)
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 3, false)
ExcelKeywords.setValueToCellByIndex(sheet2, 2, 4, true)
ExcelKeywords.setValueToCellByIndex(sheet2, 2, 5, "Test @string")
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 6, "")
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 7, " ")
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 8, "02/13/2019")
ExcelKeywords.setValueToCellByIndex(sheet2, 2, 9, 45678)
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 10, 1.35)
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 11, convertDate)
ExcelKeywords.setValueToCellByIndex(sheet2, 2, 12, "!@#~%^&*()\$")
ExcelKeywords.setValueToCellByIndex(sheet2, 2, 13, "=COUNTA(C2:C12)")
ExcelKeywords.setValueToCellByIndex(sheet1, 2, 14, paragraph)
ExcelKeywords.setValueToCellByIndex(sheet1, 5, 9, 5)
ExcelKeywords.setValueToCellByIndex(sheet1, 5, 10, 100L)
ExcelKeywords.setValueToCellByIndex(sheet1, 5, 11, 10.56f)
ExcelKeywords.setValueToCellByIndex(sheet1, 5, 12, 10.5e40)
ExcelKeywords.setValueToCellByIndex(sheet1, -2, 0, 5)
ExcelKeywords.setValueToCellByIndex(sheet1, 2, -5, 100L)
ExcelKeywords.setValueToCellByIndex(sheet1, -2, -7, 5)
ExcelKeywords.setValueToCellByAddresses(sheet1, content)
ExcelKeywords.saveWorkbook(SAMPLE_XLS_FILE_PATH, workbookXLS)

def c4 = ExcelKeywords.getCellValueByAddress(sheet1, "C4")
def c5 = ExcelKeywords.getCellValueByAddress(sheet1, "C5")
def c6 = ExcelKeywords.getCellValueByAddress(sheet1, "C6")
def c8 = ExcelKeywords.getCellValueByAddress(sheet1, "C8")
def c12 = ExcelKeywords.getCellValueByAddress(sheet1, "C12")
System.out.println("C4 " + c4)
System.out.println("C5 " + c5)
System.out.println("C6 " + c6)
System.out.println("C8 " + c8)
System.out.println("C12 " + c12)


ExcelKeywords.createExcelFile(SAMPLE_XLSX_FILE_PATH)
Workbook workbookXLSX = ExcelKeywords.getWorkbook(SAMPLE_XLSX_FILE_PATH)
ExcelKeywords.createExcelSheets(workbookXLSX, Arrays.asList("van", "test"))
Sheet sheet3 = workbookXLSX.getSheet("test")
Sheet sheet4 = workbookXLSX.getSheet("van")

ExcelKeywords.setValueToCellByAddress(sheet4, "E3", true)
ExcelKeywords.setValueToCellByAddress(sheet3, "E4", false)
ExcelKeywords.setValueToCellByAddress(sheet3, "E5", "Abcdef@123!")
ExcelKeywords.setValueToCellByAddress(sheet3, "E6", "")
ExcelKeywords.setValueToCellByAddress(sheet4, "E7", " ")
ExcelKeywords.setValueToCellByAddress(sheet3, "E8", "02/13/2019")
ExcelKeywords.setValueToCellByAddress(sheet4, "E9", 1)
ExcelKeywords.setValueToCellByAddress(sheet3, "E10", 1000)
ExcelKeywords.setValueToCellByAddress(sheet4, "E11", 12.56456456)
ExcelKeywords.setValueToCellByAddress(sheet3, "E12", 4535346547567586786)
ExcelKeywords.setValueToCellByAddress(sheet3, "E13", "!@#~%^&*()\$")
ExcelKeywords.setValueToCellByAddress(sheet4, "E14", 11)
ExcelKeywords.setValueToCellByAddress(sheet4, "E15", "=COUNTA(E3:E14)")
ExcelKeywords.setValueToCellByAddress(sheet4, "E16", paragraph)
ExcelKeywords.setValueToCellByAddress(sheet4, "E17", "1,2345")
ExcelKeywords.setValueToCellByAddress(sheet3, "E18", convertDate)
ExcelKeywords.setValueToCellByAddress(sheet4, "", "1,2345")
ExcelKeywords.setValueToCellByAddress(sheet4, null, "1,2345")
ExcelKeywords.setValueToCellByAddresses(sheet1, content)
ExcelKeywords.saveWorkbook(SAMPLE_XLSX_FILE_PATH, workbookXLSX)

def numInd = ExcelKeywords.getCellValueByIndex(sheet4, 8, 4)
def e4 = ExcelKeywords.getCellValueByAddress(sheet3, "E4")
def e5 = ExcelKeywords.getCellValueByAddress(sheet3, "E5")
def e9 = ExcelKeywords.getCellValueByAddress(sheet4, "E9")
def e10 = ExcelKeywords.getCellValueByAddress(sheet3, "E10")
def e11 = ExcelKeywords.getCellValueByAddress(sheet4, "E11")
def e12 = ExcelKeywords.getCellValueByAddress(sheet3, "E12")
def e15 = ExcelKeywords.getCellValueByAddress(sheet4, "E15")
def e18 = ExcelKeywords.getCellValueByAddress(sheet3, "E18")

System.out.println(numInd)
System.out.println("e4 " + e4)
System.out.println("e5 " + e5)
System.out.println("e9 " + e9)
System.out.println("e10 " + e10)
System.out.println("e11 " + e11)
System.out.println("e12 " + e12)
System.out.println("e15 " + e15)
System.out.println("e18 " + e18)




