package io.camunda.example;

import io.camunda.connector.generator.dsl.OutboundElementTemplate;
import io.camunda.connector.generator.java.OutboundClassBasedTemplateGenerator;

public class MyTemplateGenerator {

    public static void main(String[] args) {
        OutboundClassBasedTemplateGenerator generator = new OutboundClassBasedTemplateGenerator();
        OutboundElementTemplate template = generator.generate(MyConnectorFunction.class);
    }
}
