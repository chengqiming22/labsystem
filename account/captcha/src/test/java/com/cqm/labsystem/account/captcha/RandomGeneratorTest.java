package com.cqm.labsystem.account.captcha;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;

/**
 * Created by qmcheng on 2016/11/28 0028.
 */
public class RandomGeneratorTest {
    @Test
    public void testGetRandomString(){
        Set<String> randoms= new HashSet<String>();
        for(int i = 0;i < 100;i++){
            String random = RandomGenerator.getRandomString();
            assertFalse(randoms.contains(random));
            randoms.add(random);
        }
    }
}
