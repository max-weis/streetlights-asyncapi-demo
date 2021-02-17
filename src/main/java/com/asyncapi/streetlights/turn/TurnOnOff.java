package com.asyncapi.streetlights.turn;

import io.smallrye.asyncapi.spec.annotations.schema.Schema;

import java.time.LocalDateTime;

@Schema
public class TurnOnOff {

  @Schema
  public Command command;

  @Schema(name = "sentAt")
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
