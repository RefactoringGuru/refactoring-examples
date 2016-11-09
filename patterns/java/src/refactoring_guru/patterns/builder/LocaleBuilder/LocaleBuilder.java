package refactoring_guru.patterns.builder.LocaleBuilder;

import java.util.Locale;

public class LocaleBuilder {
    public static void main(String[] args) {
        Locale locale = new Locale.Builder()
                .setLanguage("en")
                .setScript("Cyrl")
                .setRegion("UA")
                .build();
        System.out.println(locale.getDisplayCountry() + "\n" + locale.getDisplayLanguage());
    }
}
