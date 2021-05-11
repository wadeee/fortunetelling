package com.chenhongliang.fortunetelling;

import com.chenhongliang.fortunetelling.util.Lunar;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SpringBootTest
class FortunetellingApplicationTests {

    @Test
    void contextLoads() throws ParseException {

        Calendar today = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        today.setTime(formatter.parse("1993-12-11"));
        Lunar lunar = new Lunar(today);
        System.out.print(lunar.cyclical() + "年");//计算输出阴历年份
        System.out.println(lunar.toString());//计算输出阴历日期
        System.out.println(lunar.animalsYear());//计算输出属相
        System.out.println(new java.sql.Date(today.getTime().getTime()));//输出阳历日期
        System.out.println("星期"
                + lunar.getChinaWeekdayString(today.getTime().toString
                ().substring(0, 3)));//计算输出星期几 }
    }

}
