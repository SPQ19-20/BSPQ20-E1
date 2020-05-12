package es.deusto.client.windows;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is used to manage the different languages 
 * supported by the application. It keeps track of the
 * current language, and it allows the users to change it
 * and to get the specified string in the current language.
 */
public class LanguageManager {

    private String language;
    private ResourceBundle resourceBundle;

    /**
     * Constructor.
     * Loads the default system language.
     */
    public LanguageManager() {
        this.language = "en";
        this.resourceBundle = ResourceBundle.getBundle("SystemMessages", new Locale("en", "GB"));
    }

    /**
     * Creates an instance of the class and 
     * sets the current language to the specified one.
     * 
     * @param language The String code of the desired language
     */
    public LanguageManager(String language) {
        this.setLanguage(language);
    }

    /**
     * Retrieves the translation to the current language of 
     * the String identified by the key.
     * @param key Identifier of the desired String
     * @return translation Translation of the specified String to the current language
     */
    public String getString(String key) {
            return this.resourceBundle.getString(key);
    }

    public String getLanguage() {
        return language;
    }

    /**
     * Sets the current language.
     * 
     * @param language The String code of the desired language
     */
    public void setLanguage(String language) {
        this.language = language;
        this.resourceBundle = ResourceBundle.getBundle("SystemMessages", Locale.forLanguageTag(language));
    }

}