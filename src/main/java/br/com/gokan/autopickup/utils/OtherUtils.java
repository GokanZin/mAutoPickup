package br.com.gokan.autopickup.utils;

import static noppes.npcs.DataInventory.random;

public class OtherUtils {

    public static boolean isChanceSuccessful(double chance) {
        if (chance > 1.0) {
            chance = 1.0;
        }
        double randomValue = random.nextDouble();
        return randomValue < chance;
    }
}
