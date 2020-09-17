package com.asyncapi.streetlights.turn;

import java.time.LocalDateTime;

public class Turn {

  private Command command;

  private LocalDateTime sentAt;

  public Turn() {
  }

  public Turn(final Command command, final LocalDateTime sentAt) {
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
    return "Turn{" + "command=" + command + ", sentAt=" + sentAt.toString() + '}';
  }
}
