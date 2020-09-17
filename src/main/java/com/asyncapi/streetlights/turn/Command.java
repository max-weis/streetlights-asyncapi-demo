package com.asyncapi.streetlights.turn;

public enum Command {
  ON("on"), OFF("off");

  private String value;

  Command(final String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
