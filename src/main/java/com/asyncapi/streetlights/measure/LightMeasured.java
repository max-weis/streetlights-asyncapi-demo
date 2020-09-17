package com.asyncapi.streetlights.measure;

import java.time.LocalDateTime;

public class LightMeasured {

  private int lumens;

  private LocalDateTime sentAt;

  public LightMeasured() {
  }

  public LightMeasured(final int lumens, final LocalDateTime sentAt) {
    this.lumens = lumens;
    this.sentAt = sentAt;
  }

  public int getLumens() {
    return lumens;
  }

  public void setLumens(final int lumens) {
    this.lumens = lumens;
  }

  public LocalDateTime getSentAt() {
    return sentAt;
  }

  public void setSentAt(final LocalDateTime sentAt) {
    this.sentAt = sentAt;
  }

  @Override
  public String toString() {
    return "LightMeasured{" + "lumens=" + lumens + ", sentAt=" + sentAt + '}';
  }
}
