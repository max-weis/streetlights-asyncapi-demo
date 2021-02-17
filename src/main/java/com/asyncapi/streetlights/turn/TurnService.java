package com.asyncapi.streetlights.turn;

import io.smallrye.asyncapi.spec.annotations.channel.ChannelItem;
import io.smallrye.asyncapi.spec.annotations.message.CorrelationID;
import io.smallrye.asyncapi.spec.annotations.message.Message;
import io.smallrye.asyncapi.spec.annotations.message.MessageTrait;
import io.smallrye.asyncapi.spec.annotations.operation.Operation;
import io.smallrye.asyncapi.spec.annotations.parameter.Parameter;
import io.smallrye.asyncapi.spec.annotations.parameter.Parameters;
import io.smallrye.asyncapi.spec.annotations.schema.Schema;
import io.smallrye.asyncapi.spec.annotations.schema.SchemaProperty;
import io.smallrye.asyncapi.spec.annotations.schema.SchemaType;
import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.mqtt.MqttMessage;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@ApplicationScoped
public class TurnService {

  @ChannelItem(channel = "smartylighting/streetlights/1/0/action/{streetlightId}/turn/on",
      parameters = @Parameters(value = { @Parameter(name = "streetlightId",
          ref = "#/components/parameters/streetlightId") }),
      publish = @Operation(operationId = "turnOn",
          message = @Message(ref = "#/components/messages/turnOnOff")))

  @Parameter(name = "streetlightId",
      description = "The ID of the streetlight.",
      schema = @Schema(type = SchemaType.STRING))

  @Message(name = "turnOnOff",
      title = "TurnOnOff on/off",
      summary = "Command a particular streetlight to turn the lights on or off.",
      payload = @Schema(ref = "#/components/schemas/TurnOnOff"))

  @MessageTrait(name = "commonHeaders",
      description = "Common Headers",
      contentType = "application/json",
      headers = @Schema(type = SchemaType.OBJECT,
          properties = @SchemaProperty(type = SchemaType.INTEGER,
              name = "commonHeaders",
              minimum = "0",
              maximum = "100"))
  )
  @Outgoing("turnOn")
  public Multi<MqttMessage<TurnOnOff>> turnOn() {
    return Multi.createFrom()
        .ticks()
        .every(Duration.ofMinutes(1))
        .map(x -> generateMessage(Command.ON));
  }

  @ChannelItem(channel = "smartylighting/streetlights/1/0/action/{streetlightId}/turn/off",
      parameters = @Parameters(value = { @Parameter(name = "streetlightId",
          ref = "#/components/parameters/streetlightId") }),
      publish = @Operation(operationId = "turnOff",
          message = @Message(ref = "#/components/messages/turnOnOff")))
  @Outgoing("turnOff")
  public Multi<MqttMessage<TurnOnOff>> turnOff() {
    return Multi.createFrom()
        .ticks()
        .every(Duration.ofMinutes(1))
        .map(x -> generateMessage(Command.OFF));
  }

  private static MqttMessage<TurnOnOff> generateMessage(final Command command) {
    TurnOnOff turnOnOff = new TurnOnOff(command, LocalDateTime.now());

    String topic = String.format("smartylighting/streetlights/1/0/action/%d/turnOnOff/%s", new Random().nextInt(1000), command.toString());

    System.out.println(String.format("Send message: %s, to topic: %s", turnOnOff.toString(), topic));

    return MqttMessage.of(topic, turnOnOff);
  }

}
