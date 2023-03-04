import java.util.ArrayList;
import java.util.List;


public class Record {
    private String word;
    private List<String> translations = new ArrayList<>();

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public void addTranslation(String translation) {
        this.translations.add(translation);
    }


}
