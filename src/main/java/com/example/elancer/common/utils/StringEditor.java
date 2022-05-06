package com.example.elancer.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringEditor {
    private static final String COMMA = ",";

    public static String editStringListToString(List<String> stringList) {
        if (stringList == null) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String tmpString : stringList) {
            stringBuilder.append(tmpString);
            checkLastContent(stringList, stringBuilder, tmpString);
        }
        return String.valueOf(stringBuilder);
    }

    public static List<String> editStringToStringList(String string) {
        if (string == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(string.split(","));
    }

    private static void checkLastContent(List<String> stringList, StringBuilder stringBuilder, String tmpString) {
        if (!tmpString.equals(stringList.get(stringList.size() - 1))) {
            stringBuilder.append(COMMA);
        }
    }
}
