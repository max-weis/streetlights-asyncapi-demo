package com.asyncapi.streetlights.turn;

import com.asyncapi.api.annotations.Reference;
import com.asyncapi.api.annotations.channel.ChannelItem;
import com.asyncapi.api.annotations.channel.message.Message;
import com.asyncapi.api.annotations.channel.message.MessageTrait;
import com.asyncapi.api.annotations.channel.operation.Operation;
import com.asyncapi.api.annotations.channel.operation.OperationTrait;
import com.asyncapi.api.annotations.parameter.Parameter;
import com.asyncapi.api.annotations.parameter.Parameters;
import com.asyncapi.api.annotations.schema.Schema;
import com.asyncapi.api.annotations.schema.SchemaProperty;
import com.asyncapi.api.annotations.schema.SchemaType;
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
public class TurnService {

  private static final Logger LOG = LoggerFactory.getLogger(TurnService.class);

  @ChannelItem(
      channel = "smartylighting/streetlights/1/0/action/{streetlightId}/turn/on",
      parameters = @Parameters(
          value = {
              @Parameter(
                  name = "streetlightId",
                  description = "The ID of the streetlight.",
                  schema = @Schema(
                      type = SchemaType.STRING
                  )
              )
          }
      ),
      publish = @Operation(
          operationId = "receiveLightMeasurement",
          traits = {
              @OperationTrait(ref = @Reference(ref = "#/components/operationTraits/kafka"))
          },
          message = @Message(
              name = "turnOnOff",
              title = "TurnOnOff on/off",
              summary = "Command a particular streetlight to turn the lights on or off.",
              traits = {
                  @MessageTrait(
                      name = "commonHeaders",
                      headers = @Schema(
                          type = SchemaType.OBJECT,
                          properties = @SchemaProperty(
                              type = SchemaType.INTEGER,
                              minimum = "0",
                              maximum = "100"
                          )
                      )
                  )
              },
              payload = @Schema(ref = @Reference(ref = "#/components/schemas/turnOnOffPayload"))
          )
      )
  )
  @Outgoing("turnOn")
  public Multi<MqttMessage<TurnOnOff>> turnOn() {
    return Multi
        .createFrom()
        .ticks()
        .every(Duration.ofSeconds(5))
        .map(x -> generateMessage(Command.ON));
  }

  @ChannelItem(
      channel = "smartylighting/streetlights/1/0/action/{streetlightId}/turn/off",
      parameters = @Parameters(
          value = {
              @Parameter(
                  name = "streetlightId",
                  ref = @Reference(ref = "#/components/parameters/streetlightId")
              )
          }
      ),
      publish = @Operation(
          operationId = "receiveLightMeasurement",
          traits = {
              @OperationTrait(ref = @Reference(ref = "#/components/operationTraits/kafka"))
          },
          message = @Message(ref = @Reference(ref = "#/components/messages/turnOnOff"))
      )
  )
  @Outgoing("turnOff")
  public Multi<MqttMessage<TurnOnOff>> turnOff() {
    return Multi
        .createFrom()
        .ticks()
        .every(Duration.ofSeconds(5))
        .map(x -> generateMessage(Command.OFF));
  }

  private static MqttMessage<TurnOnOff> generateMessage(final Command command) {
    TurnOnOff turnOnOff = new TurnOnOff(command, LocalDateTime.now());

    String topic = String.format("smartylighting/streetlights/1/0/action/%d/turnOnOff/%s", new Random().nextInt(1000), command.toString());

    LOG.info("Send message: {}, to topic: {}", turnOnOff.toString(), topic);

    return MqttMessage.of(topic, turnOnOff);
  }

}
