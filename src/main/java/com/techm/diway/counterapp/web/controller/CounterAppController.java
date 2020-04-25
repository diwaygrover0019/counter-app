package com.techm.diway.counterapp.web.controller;

import com.techm.diway.counterapp.models.SearchRequest;
import com.techm.diway.counterapp.models.SearchResponse;
import com.techm.diway.counterapp.models.Word;
import com.techm.diway.counterapp.services.CounterAppService;
import com.techm.diway.counterapp.utils.CsvUtil;
import com.techm.diway.counterapp.web.validators.CounterAppValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/counter-api")
public class CounterAppController {

    private final Logger logger = LoggerFactory.getLogger(CounterAppController.class);

    @Autowired
    private CounterAppValidator counterAppValidator;

    @Autowired
    private CounterAppService counterAppService;

    @Autowired
    private CsvUtil csvUtil;

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResponse> searchWordCounts(@RequestBody SearchRequest searchRequest) {
        counterAppValidator.validateSearchRequest(searchRequest);
        logger.info("postCounts for searchRequest: {}", searchRequest);
        return new ResponseEntity(counterAppService.searchWords(searchRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/top/{value}", produces = {"text/csv"})
    public ResponseEntity<String> getTopWords(@PathVariable Integer value) {
        counterAppValidator.validateTopValue(value);
        logger.info("topWords for value: {}", value);
        List<Word> topWordsList = counterAppService.getTopWords(value);
        return new ResponseEntity(csvUtil.getCsvResponse(topWordsList), HttpStatus.OK);
    }
}
