package github.olgakos;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class CsvParsingTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void parseCsvTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("files/SampleCSVFile.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {

            List<String[]> content = reader.readAll();
            assertThat(content.get(0)).contains(
                    "1",
                    "Alexander Pushkin",
                    "Onegin",
                    "Tatiana");
        }
    }
}
