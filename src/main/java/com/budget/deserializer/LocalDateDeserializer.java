package com.budget.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.log4j.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(LocalDateDeserializer.class);

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext deserializationContext) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return LocalDate.parse(parser.readValueAs(String.class), formatter);
        } catch (Exception e) {
            log.error("Cannot deserialize Date from incoming JSON.");
            return null;
        }
    }
}
