package com.yellowaxe.googlereader.json;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

@Component
public class GoogleReaderJsonParser {

    private static final Logger LOG = Logger.getLogger(GoogleReaderJsonParser.class);

    private JsonFactory jsonFactory;

    @Inject
    public GoogleReaderJsonParser(JsonFactory jsonFactory) {
        this.jsonFactory = jsonFactory;
    }

    public GoogleReaderJsonData parse(InputStream jsonContent) {

        ObjectMapper om = new ObjectMapper(jsonFactory);

        try {

            GoogleReaderJsonData value = om.readValue(jsonContent, GoogleReaderJsonData.class);

            return value;

        } catch (JsonProcessingException e) {
            LOG.error(Throwables.getStackTraceAsString(e));
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(Throwables.getStackTraceAsString(e));
            LOG.error(e.getMessage());
        }

        return null;
    }

}
