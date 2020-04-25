package com.techm.diway.counterapp.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Word {

    private String content;
    private Integer count;

    public Word(String content, Integer count) {
        this.content = content;
        this.count = count;
    }

    @Override
    public String toString() {
        return "{" +
                "content='" + content + '\'' +
                ", count=" + count +
                '}';
    }
}
