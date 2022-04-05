package github.olgakos;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PdfParsingTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void parsePdfDownloadEbook() throws Exception {
        Selenide.open("https://git-scm.com/book/en/v2");
        //File pdfDownload = Selenide.$(".ebooks a[href*='download/2.1.339/progit.pdf']").download();
        File pdfDownload = Selenide.$("a[href='https://github.com/progit/progit2/releases/download/2.1.339/progit.pdf']").download();
        PDF pdf = new PDF(pdfDownload);
        assertThat(pdf.author).contains("Scott Chacon, Ben Straub");
        //assertThat(pdf.numberOfPages).isEqualTo(519);
        //assertThat(pdf.text).contains("Война и мир"); //ожидаемо-ошибочный
        assertThat(pdf.text).contains("Pro Git");
    }

    //тест на ЗАГРУЗКУ PDF файла из resources todo
    @Test
    void parsePdfFromResourcesTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("files/SiegfriedSassoon.pdf");
        PDF reader = new PDF(is);
        assertThat(reader.text).contains("Fledged with forest May has crowned.");
        //assertThat(reader.text).contains("Fledged1 with forest May has crowned."); // заведомо ошибка (ок)
    }

    // todo
    //НЕ работает:
    //если в запросе к PDF больше 1 строки (склеенные), упадет из-за символа \n"
    @Test
    void parsePdfFromResourcesTest_Fail() throws Exception {
        InputStream is = classLoader.getResourceAsStream("files/SiegfriedSassoon.pdf");
        PDF pdfFile = new PDF(is);
        assertThat(pdfFile.text).contains(
                        "Here I'm sitting in the gloom\n" +
                        "Of my quiet attic room.\n" +
                        "France goes rolling all around,\n" +
                        "Fledged with forest May has crowned.");
    }

    @Test
    void parsePdfFromResourcesTest_OneString() throws Exception {
        InputStream is = classLoader.getResourceAsStream("files/SiegfriedSassoon.pdf");
        PDF pdfFile = new PDF(is);
        assertThat(pdfFile.text).contains(
                "Here I'm sitting in the gloom");
    }

    /*
    @Test
    //тесты на ЗАГРУЗКУ PDF файла из resources (?)
    void parsePdfTest2() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("files/SiegfriedSassoon.pdf");
            Pdf reader = new PDF(new InputStreamReader(is))) {
            List<String[]> content = reader.readAll();
            assertThat(pdf.text).contains("Fledged with forest May has crowned.");
        }
    }

/*
    @Test
    //тесты на ЗАГРУЗКУ PDF файла из resources (?)
    void pdfTest3() throws Exception {
        PdfFile pdfFile = new PdfFile("files/SiegfriedSassoon.pdf");
        //PdfEntry pdfEntry = pdfFile.getEntry("SiegfriedSassoon.pdf");
        InputStream inputStream = pdfFile.getInputStream(pdfEntry);
        PDF pdf = new PDF(inputStream);
        assertThat(pdf.text).contains(
                "Here I'm sitting in the gloom\n" +
                "Of my quiet attic room.\n" +
                "France goes rolling all around,\n" +
                "Fledged with forest May has crowned.");
    }

*/


    }




