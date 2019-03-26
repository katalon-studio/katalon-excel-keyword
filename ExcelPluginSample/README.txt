This project is a sample test case of using Excel Plugin. Please follow these steps below to using this plugin: 

1. Create/Open Katalon project (Katalon Studio version v6.0.5 or later)
2. Select Project > Settings > External Libraries
3. Add ExcelKeywords.jar enclosed then click OK
4. On test case file, import com.kms.katalon.keyword.excel.ExcelKeywords then use this plugin as below example.
5. Hover on import com.kms.katalon.keyword.excel.ExcelKeywords, right click, then select Open Declaration
6. Select Attach Source on Class File Editor page, add ExcelKeywords_sources.jar to External location to attach javadoc.

----------------------------
import com.kms.katalon.keyword.excel.ExcelKeywords

String excelFile = 'Data Files\\File.xlsx'

ExcelKeywords.createExcelFile(excelFile)

workbook = ExcelKeywords.getWorkbook(excelFile)

ExcelKeywords.createExcelSheet(workbook)

ExcelKeywords.saveWorkbook(excelFile, workbook)
----------------------------