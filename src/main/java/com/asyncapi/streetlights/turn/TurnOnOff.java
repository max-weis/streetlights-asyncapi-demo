package com.asyncapi.streetlights.turn;

import com.asyncapi.api.annotations.Reference;
import com.asyncapi.api.annotations.schema.Schema;
import com.asyncapi.api.annotations.schema.SchemaProperty;
import com.asyncapi.api.annotations.schema.SchemaType;

import java.time.LocalDateTime;

@Schema(
    name = "turnOnOffPayload",
    type = SchemaType.OBJECT,
    properties = {
        @SchemaProperty(
            name = "command",
            type = SchemaType.STRING,
            description = "Whether to turn on or off the light.",
            enumeration = {"on","off"}
        ),
        @SchemaProperty(
            ref = @Reference(ref = "#/components/schemas/sentAt")
        )
    }
)
public class TurnOnOff {

  private Command command;

  private LocalDateTime sentAt;

  public TurnOnOff() {
  }

  public TurnOnOff(final Command command, final LocalDateTime sentAt) {
    this.command = command;
    this.sentAt = sentAt;
  }

  public Command getCommand() {
    return command;
  }

  public void setCommand(final Command command) {
    this.command = command;
  }

  public LocalDateTime getSentAt() {
    return sentAt;
  }

  public void setSentAt(final LocalDateTime sentAt) {
    this.sentAt = sentAt;
  }

  @Override
  public String toString() {
    return "TurnOnOff{" + "command=" + command + ", sentAt=" + sentAt.toString() + '}';
  }
}
