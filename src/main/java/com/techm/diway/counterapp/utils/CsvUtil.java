package com.techm.diway.counterapp.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.techm.diway.counterapp.error.ErrorCodes;
import com.techm.diway.counterapp.error.exceptions.AppRuntimeException;
import com.techm.diway.counterapp.models.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CsvUtil {

    private final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    @Autowired
    private ObjectMapper objectMapper;

    public String getCsvResponse(List<Word> wordList) {
        try {
            JsonNode jsonTree = objectMapper.readTree(objectMapper.writeValueAsString(wordList));

            CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
            JsonNode firstObject = jsonTree.elements().next();
            firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn);

            CsvSchema csvSchema = csvSchemaBuilder.build()
                    .withoutHeader()
                    .withColumnSeparator('|')
                    .withoutQuoteChar();

            CsvMapper csvMapper = new CsvMapper();
            return csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValueAsString(jsonTree);

        } catch (IOException ex) {
            String errorMsg = "getCsvResponse: failed to generate csv response";
            logger.error(errorMsg + ", cause: {}, message: {}, class: {}", ex.getCause(), ex.getMessage(), ex.getClass());
            throw new AppRuntimeException(errorMsg, ErrorCodes.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
