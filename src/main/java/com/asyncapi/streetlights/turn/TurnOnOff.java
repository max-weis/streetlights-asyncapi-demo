package com.asyncapi.streetlights.turn;

import io.smallrye.asyncapi.spec.annotations.schema.Schema;

import java.time.LocalDateTime;

@Schema(name = "turnOnOffPayload")
public class TurnOnOff {

  @Schema(required = true)
  public Command command;

  @Schema(name = "sentAt", required = true)
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
}
