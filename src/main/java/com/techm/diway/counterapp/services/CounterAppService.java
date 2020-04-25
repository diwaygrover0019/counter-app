package com.techm.diway.counterapp.services;

import com.techm.diway.counterapp.models.SearchRequest;
import com.techm.diway.counterapp.models.SearchResponse;
import com.techm.diway.counterapp.models.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CounterAppService {

    private final Logger logger = LoggerFactory.getLogger(CounterAppService.class);

    @Autowired
    @Qualifier("wordFrequencyCache")
    private Map<String, Integer> wordFrequencyCacheMap;

    public SearchResponse searchWords(final SearchRequest searchRequest) {
        SearchResponse searchResponse = new SearchResponse();
        Map<String, Integer> countMap = searchRequest.getSearchText()
                .stream()
                .collect(Collectors.toMap(
                        s -> s,
                        s -> {
                            if(wordFrequencyCacheMap.containsKey(s.toLowerCase()))
                                return wordFrequencyCacheMap.get(s.toLowerCase());
                            else
                                return 0;
                        },
                        (a, b) -> a,
                        LinkedHashMap::new));
        searchResponse.setWordMap(countMap);
        logger.info("searchResponse: {}", searchResponse);
        return searchResponse;
    }

    public List<Word> getTopWords(Integer value) {
        Map<String,Integer> resultMap = wordFrequencyCacheMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(value)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1,v2)-> v1, LinkedHashMap::new));

        List<Word> wordList = resultMap.entrySet().stream()
                .map(m-> new Word(m.getKey(),m.getValue()))
                .collect(Collectors.toList());

        logger.info("wordList: {}", wordList);
        return wordList;
    }
}
