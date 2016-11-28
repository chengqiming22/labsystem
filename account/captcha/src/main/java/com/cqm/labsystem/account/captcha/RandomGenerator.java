package com.cqm.labsystem.account.captcha;

import java.util.Random;

/**
 * Created by qmcheng on 2016/11/28 0028.
 */
public class RandomGenerator {
    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static synchronized String getRandomString(){
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for(int i = 0;i < 8;i++){
            result.append(range.charAt(random.nextInt(range.length())));
        }
        return result.toString();
    }
}
