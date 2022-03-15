package github.olgakos;

import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class ExcelParsingTest {

    @Test
    void parseXlsTest_SBIS_RU() throws Exception {
        Selenide.open("https://sbis.ru/help/ereport/xls");
        //$(byText("НД по налогу на прибыль организации")).isDisplayed();
        File xlsDownload = $(byText("НД по налогу на прибыль организации")).download();
        XLS xls = new XLS(xlsDownload);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(5)//строка 6
                .getCell(0)
                .getStringCellValue()).contains("Код налогового органа"); //ждем что строка содержит…
    }

    @Test
    void parseXlsTest_Romashka() throws Exception {
        Selenide.open("http://romashka2008.ru/price");
        //<a href="/f/prajs_ot_0103.xls">Скачать Прайс-лист Excel</a>
        File xlsDownload = Selenide.$(".site-main__inner a[href*='prajs_ot']").download();
        XLS xls = new XLS(xlsDownload);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(11) //строка 12
                .getCell(1) //столтбец2
                .getStringCellValue()).contains("Сахалинская обл, Южно-Сахалинск");
    }
}


