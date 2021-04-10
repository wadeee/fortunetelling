package com.chenhongliang.fortunetelling.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Data
@AllArgsConstructor
public class FormInfo {
    private String lastName;
    private String name;
    private String sex;
    private String date;
    private String hour;
    private String minute;
    private String payed;
}
