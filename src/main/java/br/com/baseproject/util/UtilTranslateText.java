package br.com.baseproject.util;

import com.google.cloud.translate.v3.*;

import java.io.IOException;
import java.util.List;

public class UtilTranslateText {

    private static final String projectId = "studied-groove-294515";
    private static final String targetLanguage = "pt";

    public static String translateText(String text) throws IOException {

        try (TranslationServiceClient client = TranslationServiceClient.create()) {
            LocationName parent = LocationName.of(projectId, "global");

            TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setTargetLanguageCode(targetLanguage)
                            .addContents(text)
                            .build();

            client.translateText(request).getTranslationsList().toString();
            return convertTranslationsListToString(client.translateText(request).getTranslationsList());
        }
    }

    private static String convertTranslationsListToString(List<Translation> translationsList) {
        String text = "";
        for (Translation translation : translationsList) {
            if (!text.isEmpty()) {
                text += " ";
            }
            text += translation.getTranslatedText();
        }
        return text;
    }
}
