package com.budget.deserializer;

import com.budget.model.MainCategory;
import com.budget.model.SubCategory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SubCategoryDeserializer extends StdDeserializer<SubCategory> {
    private static final long serialVersionUID = 2313213L;
    private static final String SUBCATEGORY_NAME = "name";
    private static final String SUBCATEGORY_ID = "id";
    private static final String MAIN_CATEGORY = "mainCategory";
    private static Logger log = Logger.getLogger(LocalDateDeserializer.class);


    protected SubCategoryDeserializer() {
        super(SubCategory.class);
    }

    @Override
    public SubCategory deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            String cost = node.get(MAIN_CATEGORY).get(MAIN_CATEGORY).asText();
            Long id = Long.parseLong(node.get(SUBCATEGORY_ID).toString());
            String name = node.get(SUBCATEGORY_NAME).asText();
            for (final MainCategory enumValue : MainCategory.values()) {
                if (enumValue.getMainCategory().equals(cost)) {
                    return new SubCategory(id, name, enumValue);
                }
            }
            return null;
        } catch (Exception e) {
            log.error("Cannot deserialize MainCategory from incoming JSON.");
            return null;
        }
    }
}