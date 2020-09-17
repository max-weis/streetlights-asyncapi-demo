package com.asyncapi.streetlights.dim;

import com.asyncapi.api.annotations.Reference;
import com.asyncapi.api.annotations.channel.ChannelItem;
import com.asyncapi.api.annotations.channel.message.Message;
import com.asyncapi.api.annotations.channel.message.MessageTrait;
import com.asyncapi.api.annotations.channel.operation.Operation;
import com.asyncapi.api.annotations.channel.operation.OperationTrait;
import com.asyncapi.api.annotations.parameter.Parameter;
import com.asyncapi.api.annotations.parameter.Parameters;
import com.asyncapi.api.annotations.schema.Schema;
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
                  ref = @Reference(ref = "#/components/parameters/streetlightId")
              )
          }
      ),
      publish = @Operation(
          operationId = "dimLight",
          traits = {
              @OperationTrait(ref = @Reference(ref = "#/components/operationTraits/kafka"))
          },
          message = @Message(ref = @Reference(ref = "#/components/messages/dimLight"))
      )
  )
  @Message(
      name = "dimLight",
      title = "Dim light",
      summary = "Command a particular streetlight to dim the lights.",
      traits = {
          @MessageTrait(ref = @Reference(ref = "#/components/messageTraits/commonHeaders"))
      },
      payload = @Schema(ref = @Reference(ref = "#/components/schemas/dimLightPayload"))
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
