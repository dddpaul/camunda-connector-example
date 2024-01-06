package io.camunda.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.connector.generator.dsl.OutboundElementTemplate;
import io.camunda.connector.generator.java.OutboundClassBasedTemplateGenerator;

import java.io.FileWriter;
import java.io.IOException;

public class MyTemplateGenerator {

    public static void main(String[] args) throws IOException {
        OutboundClassBasedTemplateGenerator generator = new OutboundClassBasedTemplateGenerator();
        OutboundElementTemplate template = generator.generate(MyConnectorFunction.class);
        try (FileWriter writer = new FileWriter("element-templates/template-connector.json")) {
            writer.write(new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(template));
        }
    }
}
