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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/profile.php#login')

sheet = CustomKeywords.'ExcelParser.ExcelParser.getExcelSheet'('D:\\Document\\Temp\\Demo2.xlsx', 0)

cell = CustomKeywords.'ExcelParser.ExcelParser.getCellByIndex'(sheet, 1, 0)

username = CustomKeywords.'ExcelParser.ExcelParser.getCellValue'(cell)

password = CustomKeywords.'ExcelParser.ExcelParser.getCellValueByIndex'(sheet, 1, 1)

comment = CustomKeywords.'ExcelParser.ExcelParser.getCellValueByAddress'(sheet, 'C2')

WebUI.setText(findTestObject('Page_CURA Healthcare Service/input_Username_username'), username)

WebUI.setText(findTestObject('Page_CURA Healthcare Service/input_Password_password'), password)

WebUI.click(findTestObject('Page_CURA Healthcare Service/button_Login'))

/*WebUI.click(findTestObject('Page_CURA Healthcare Service/span_Visit Date (Required)_glyphicon glyphicon-calendar'))

WebUI.click(findTestObject('null'))
*/
String text = comment.toString()

WebUI.setText(findTestObject('Page_CURA Healthcare Service/textarea_Comment_comment'), text)

