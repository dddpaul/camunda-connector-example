package com.github.dddpaul.camunda.connectorexample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import com.github.dddpaul.camunda.connectorexample.dto.Authentication;
import com.github.dddpaul.camunda.connectorexample.dto.MyConnectorRequest;
import com.github.dddpaul.camunda.connectorexample.dto.MyConnectorResult;
import org.junit.jupiter.api.Test;

public class MyFunctionTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnReceivedMessageWhenExecute() throws Exception {
        // given
        var input = new MyConnectorRequest(
                "Hello World!",
                new Authentication("testUser", "testToken")
        );
        var function = new MyConnectorFunction();
        var context = OutboundConnectorContextBuilder.create()
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        var result = function.execute(context);

        // then
        assertThat(result)
                .isInstanceOf(MyConnectorResult.class)
                .extracting("myProperty")
                .isEqualTo("Message received: Hello World!");
    }

    @Test
    void shouldThrowWithErrorCodeWhenMessageStartsWithFail() throws Exception {
        // given
        var input = new MyConnectorRequest(
                "Fail: unauthorized",
                new Authentication("testUser", "testToken")
        );
        var function = new MyConnectorFunction();
        var context = OutboundConnectorContextBuilder.create()
                .variables(objectMapper.writeValueAsString(input))
                .build();

        // when
        var result = catchThrowable(() -> function.execute(context));

        // then
        assertThat(result)
                .isInstanceOf(ConnectorException.class)
                .hasMessageContaining("started with 'fail'")
                .extracting("errorCode").isEqualTo("FAIL");
    }
}
