package github.olgakos.domain;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Student {
    public String id;
    public String name;
    public String lastname;
    public boolean isEnglishLiterature;
    public List<String> books;
}
