package github.olgakos;

import github.olgakos.domain.Student; //для JSON
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths; //для JSON

import static org.assertj.core.api.Assertions.assertThat; //?

public class JsonFileTest {
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void jsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Student student = mapper.readValue(Paths.get
                ("src/test/resources/files/SimpleJSONfile.json")
                .toFile(), Student.class);
        assertThat(student.name).isEqualTo("Siegfried");
        assertThat(student.lastname).isEqualTo("Sassoon");
        assertThat(student.isEnglishLiterature).isEqualTo(true);
        assertThat(student.books).contains("Memoirs of a Fox-Hunting Man",
                "Counter-Attack and Other Poems");
    }

}
