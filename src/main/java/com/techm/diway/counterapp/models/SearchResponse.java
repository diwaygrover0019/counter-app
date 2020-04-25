package com.techm.diway.counterapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

public class SearchResponse {

    @JsonIgnore
    @Getter
    private Map<String,Integer> wordMap;

    @Getter
    private Set<Map.Entry<String,Integer>> counts;

    public void setWordMap(Map<String, Integer> wordMap) {
        this.wordMap = wordMap;
        this.counts = wordMap.entrySet();
    }

    @Override
    public String toString() {
        return "{" +
                "wordMap=" + wordMap +
                ", counts=" + counts +
                '}';
    }
}
