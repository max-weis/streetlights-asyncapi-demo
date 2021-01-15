package com.asyncapi.streetlights.measure;

import io.smallrye.asyncapi.spec.annotations.channel.ChannelItem;
import io.smallrye.asyncapi.spec.annotations.message.Message;
import io.smallrye.asyncapi.spec.annotations.message.MessageTrait;
import io.smallrye.asyncapi.spec.annotations.operation.Operation;
import io.smallrye.asyncapi.spec.annotations.operation.OperationTrait;
import io.smallrye.asyncapi.spec.annotations.parameter.Parameter;
import io.smallrye.asyncapi.spec.annotations.parameter.Parameters;
import io.smallrye.asyncapi.spec.annotations.schema.Schema;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class MeasuredService {

  private static final Logger LOG = LoggerFactory.getLogger(MeasuredService.class);

  @ChannelItem(
      channel = "smartylighting/streetlights/1/0/event/{streetlightId}/lighting/measure",
      description = "The topic on which measure values may be produced and consumed.",
      parameters = @Parameters(
          value = {
              @Parameter(
                  name = "streetlightId",
                  ref = "#/components/parameters/streetlightId"
              )
          }
      ),
      subscribe = @Operation(
          summary = "Receive information about environmental lighting conditions of a particular streetlight.",
          operationId = "receiveLightMeasurement",
          traits = {
            @OperationTrait(ref = "#/components/operationTraits/kafka")
          },
          message = @Message(ref = "#/components/messages/lightMeasured")
      )
  )
  @Message(
      name = "lightMeasured",
      title = "Light measured",
      summary = "Inform about environmental lighting conditions for a particular streetlight.",
      contentType = "application/json",
      traits = {
          @MessageTrait(ref = "#/components/messageTraits/commonHeaders")
      },
      payload = @Schema(ref = "#/components/schemas/lightMeasuredPayload")
  )
  @Incoming("measure")
  public CompletionStage<Void> consume(final org.eclipse.microprofile.reactive.messaging.Message<byte[]> message) {
    MqttMessage mqttMessage = message.unwrap(MqttMessage.class);

    LOG.info("topic: {}", mqttMessage.getTopic());
    LOG.info("message: {}", new String(message.getPayload()));

    return message.ack();
  }
}
