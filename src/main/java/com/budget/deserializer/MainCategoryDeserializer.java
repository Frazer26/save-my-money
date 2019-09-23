package com.budget.deserializer;

import com.budget.model.MainCategory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class MainCategoryDeserializer extends StdDeserializer<MainCategory> {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(LocalDateDeserializer.class);

    protected MainCategoryDeserializer() {
        super(MainCategory.class);
    }

    @Override
    public MainCategory deserialize(JsonParser parser, DeserializationContext deserializationContext) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, String>> typeRef = new TypeReference<>() {};
            Map<String, String> map = mapper.readValue(parser, typeRef);
            String mainCategory = map.get("mainCategory");
            for (final MainCategory enumValue : MainCategory.values())
            {
                if (enumValue.getMainCategory().equals(mainCategory))
                {
                    return enumValue;
                }
            }
            return null;
        } catch (Exception e) {
            log.error("Cannot deserialize MainCategory from incoming JSON.");
            return null;
        }
    }
}

