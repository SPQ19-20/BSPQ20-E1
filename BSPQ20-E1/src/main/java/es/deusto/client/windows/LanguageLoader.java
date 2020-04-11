package es.deusto.client.windows;

import java.io.*;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;

public class LanguageLoader {

    private final HashMap<String, String> localizedStrings;
    private String language;

    public LanguageLoader(String language) {
        this.language = language;
        localizedStrings = new HashMap<>();

        File f = new File(getClass().getClassLoader().getResource("languages/" + language + "/strings.conf").getFile());
        //try (BufferedReader br = new BufferedReader(new FileReader(f))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getClass().getClassLoader().getResource("languages/" + language + "/strings.conf").getFile()), StandardCharsets.UTF_8));) {
            String line;
            while ((line = br.readLine()) != null)
                localizedStrings.put(line.substring(0, line.indexOf('=')), line.substring(line.indexOf('=') + 2, line.length() - 1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringById(String stringId) {
        return localizedStrings.get(stringId);
    }

    public String getLanguage() {
        return language;
    }

}