package br.com.gokan.autopickup.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtherUtils {

    public static boolean isChanceSuccessful(double chance) {
        chance = Math.min(chance, 100.0);
        Random random = new Random();
        double randomValue = random.nextDouble() * 100.0;
        return randomValue < chance;
    }

    public static Double extractDouble(String input) {
        String regex = "\\d+\\.\\d+|\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String numberStr = matcher.group();
            try {
                return Double.parseDouble(numberStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }

}
