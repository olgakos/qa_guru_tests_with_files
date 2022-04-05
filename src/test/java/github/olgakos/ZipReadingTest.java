package github.olgakos;

import org.junit.jupiter.api.Test;
import com.codeborne.xlstest.XLS;
import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


import static org.assertj.core.api.Assertions.assertThat;

public class ZipReadingTest {
    // Constants
    private static final String
            CSV_FILE = "SampleCSVfile.csv",
            XLS_FILE = "SampleXLSXfile.xlsx",
            PDF_FILE = "SiegfriedSassoon.pdf";

    @Test
    void parseZipTest() throws Exception {
        ZipFile zf = new ZipFile("src/test/resources/files/some-files-in-zip3.zip");
        for (Enumeration<? extends ZipEntry> iter = zf.entries(); iter.hasMoreElements(); ) {
            ZipEntry entryFile = iter.nextElement();
            if (entryFile.getName().contains("pdf")) {
                assertThat(entryFile.getName()).isEqualTo(PDF_FILE);
                parsePdfTest1(zf.getInputStream(entryFile)); //PDF
            } else if (entryFile.getName().contains("xlsx")) {
                assertThat(entryFile.getName()).isEqualTo(XLS_FILE);
                parseXlsTest(zf.getInputStream(entryFile)); //XLSX
            } else if (entryFile.getName().contains("csv")) {
                assertThat(entryFile.getName()).isEqualTo(CSV_FILE);
                parseCsvTest(zf.getInputStream(entryFile)); //CSV
            }
        }
    }

//работает:
       void parsePdfTest1 (InputStream pdfFile) throws Exception {
        PDF pdf = new PDF(pdfFile);
        assertThat(pdf.text).contains(
                "For now we've marched to a green, trenchless land");
    }

    // todo
    //НЕ работает:
    //если в запросе к PDF больше 1 строки (склеенные), упадет из-за символа \n"
    //(для запуска заменить ИМЯ parsePdfTest_2FAIL на parsePdfTest1)
    void parsePdfTest_2FAIL (InputStream pdfFile) throws Exception {
        PDF pdf = new PDF(pdfFile);
        assertThat(pdf.text).contains(
                "For now we've marched to a green, trenchless land\n" +
                  "Twelve miles from battering guns: along the grass\n" +
                  "Brown lines of tents are hives for snoring men;\n" +
                  "Wide, radiant water sways the floating sky\n" +
                  "Below dark, shivering trees. And living-clean");
    }

    void parseXlsTest (InputStream xlsFile) throws Exception {
        XLS xls = new XLS(xlsFile);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(5)//строка 6
                .getCell(0)
                .getStringCellValue()).contains("Код налогового органа"); //ждем что строка содержит…

    }

    void parseCsvTest (InputStream csvFile) throws Exception {
        try (CSVReader reader = new CSVReader(new InputStreamReader(csvFile));)
        {
            List<String[]> containt = reader.readAll();
            assertThat(containt.get(0)).contains(
                    "1",
                    "Alexander Pushkin",
                    "Onegin",
                    "Tatiana");
        }
    }
}