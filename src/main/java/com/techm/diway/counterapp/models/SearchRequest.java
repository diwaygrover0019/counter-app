package com.techm.diway.counterapp.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequest {

    private List<String> searchText;

    @Override
    public String toString() {
        return "{" +
                "searchText=" + searchText +
                '}';
    }
}
