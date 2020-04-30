package es.deusto.client.windows;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private String language;
    private ResourceBundle resourceBundle;

    /**
     * Loads the default system language.
     */
    public LanguageManager() {
        /*
        this.language = Locale.getDefault().getLanguage();
        this.resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.getDefault());
        */
        this.language = "en";
        this.resourceBundle = ResourceBundle.getBundle("SystemMessages", new Locale("en", "GB"));
    }

    /**
     * Loads the specified language
     */
    public LanguageManager(String language) {
        this.setLanguage(language);
    }

    public String getString(String key) {
            return this.resourceBundle.getString(key);
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language of the program
     */
    public void setLanguage(String language) {
        this.language = language;
        this.resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.forLanguageTag(language));
    }

}