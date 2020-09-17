package com.asyncapi.streetlights.measure;

import com.asyncapi.api.annotations.Reference;
import com.asyncapi.api.annotations.channel.ChannelItem;
import com.asyncapi.api.annotations.channel.message.Message;
import com.asyncapi.api.annotations.channel.message.MessageTrait;
import com.asyncapi.api.annotations.channel.operation.Operation;
import com.asyncapi.api.annotations.channel.operation.OperationTrait;
import com.asyncapi.api.annotations.parameter.Parameter;
import com.asyncapi.api.annotations.parameter.Parameters;
import com.asyncapi.api.annotations.schema.Schema;
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
                  ref = @Reference(ref = "#/components/parameters/streetlightId")
              )
          }
      ),
      subscribe = @Operation(
          summary = "Receive information about environmental lighting conditions of a particular streetlight.",
          operationId = "receiveLightMeasurement",
          traits = {
            @OperationTrait(ref = @Reference(ref = "#/components/operationTraits/kafka"))
          },
          message = @Message(ref = @Reference(ref = "#/components/messages/lightMeasured"))
      )
  )
  @Message(
      name = "lightMeasured",
      title = "Light measured",
      summary = "Inform about environmental lighting conditions for a particular streetlight.",
      contentType = "application/json",
      traits = {
          @MessageTrait(ref = @Reference(ref = "#/components/messageTraits/commonHeaders"))
      },
      payload = @Schema(ref = @Reference(ref = "#/components/schemas/lightMeasuredPayload"))
  )
  @Incoming("measure")
  public CompletionStage<Void> consume(final org.eclipse.microprofile.reactive.messaging.Message<byte[]> message) {
    MqttMessage mqttMessage = message.unwrap(MqttMessage.class);

    LOG.info("topic: {}", mqttMessage.getTopic());
    LOG.info("message: {}", new String(message.getPayload()));

    return message.ack();
  }
}
