package com.announcement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String title;
    private String subTitle;
    private String imgBase64;
    private Date published;
    private Integer period;
    private String content;
    private String name;
    private String message;
}
