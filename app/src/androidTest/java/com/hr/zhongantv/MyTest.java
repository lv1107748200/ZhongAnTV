package com.hr.zhongantv;

import org.junit.Test;

/**
 * Created by 吕 on 2018/5/9.
 */

public class MyTest {


    @Test
    public void Test1(){

      //  System.out.println("test--->"+toChinese("1"));
    }


    private String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  "+result);
        }
        return result;

    }
}
