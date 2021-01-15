package com.asyncapi.streetlights.dim;

import io.smallrye.asyncapi.spec.annotations.channel.ChannelItem;
import io.smallrye.asyncapi.spec.annotations.message.Message;
import io.smallrye.asyncapi.spec.annotations.message.MessageTrait;
import io.smallrye.asyncapi.spec.annotations.operation.Operation;
import io.smallrye.asyncapi.spec.annotations.operation.OperationTrait;
import io.smallrye.asyncapi.spec.annotations.parameter.Parameter;
import io.smallrye.asyncapi.spec.annotations.parameter.Parameters;
import io.smallrye.asyncapi.spec.annotations.schema.Schema;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@ApplicationScoped
public class DimService {

  private static final Logger LOG = LoggerFactory.getLogger(DimService.class);

  @ChannelItem(
      channel = "smartylighting/streetlights/1/0/action/{streetlightId}/dim",
      parameters = @Parameters(
          value = {
              @Parameter(
                  name = "streetlightId",
                  ref = "#/components/parameters/streetlightId"
              )
          }
      ),
      publish = @Operation(
          operationId = "dimLight",
          traits = {
              @OperationTrait(ref = "#/components/operationTraits/kafka")
          },
          message = @Message(ref = "#/components/messages/dimLight")
      )
  )
  @Message(
      name = "dimLight",
      title = "Dim light",
      summary = "Command a particular streetlight to dim the lights.",
      traits = {
          @MessageTrait(ref = "#/components/messageTraits/commonHeaders")
      },
      payload = @Schema(ref = "#/components/schemas/dimLightPayload")
  )
  @Outgoing("dim")
  public Multi<MqttMessage<Dim>> dim() {
    return Multi
        .createFrom()
        .ticks()
        .every(Duration.ofSeconds(5))
        .map(x -> generateMessage());
  }

  private static MqttMessage<Dim> generateMessage() {
    Random random = new Random();
    Dim dim = new Dim(random.nextInt(100), LocalDateTime.now());

    String topic = String.format("smartylighting/streetlights/1/0/action/%d/dim", random.nextInt(1000));

    LOG.info("Send message: {}, to topic: {}", dim.toString(), topic);

    return MqttMessage.of(topic, dim);
  }
}
