package com.asyncapi.streetlights.dim;

import io.smallrye.asyncapi.spec.annotations.schema.Schema;
import io.smallrye.asyncapi.spec.annotations.schema.SchemaType;

import java.time.LocalDateTime;

public class Dim {

  @Schema(description = "Percentage to which the light should be dimmed to.", minimum = "0", maximum = "100")
  private int percentage;

  @Schema(type = SchemaType.STRING, format = "date-time", description = "Date and time when the message was sent.")
  private LocalDateTime sentAt;

  public Dim(){
  }

  public Dim(final int percentage, final LocalDateTime sentAt) {
    this.percentage = percentage;
    this.sentAt = sentAt;
  }

  public int getPercentage() {
    return percentage;
  }

  public void setPercentage(final int percentage) {
    this.percentage = percentage;
  }

  public LocalDateTime getSentAt() {
    return sentAt;
  }

  public void setSentAt(final LocalDateTime sentAt) {
    this.sentAt = sentAt;
  }

  @Override
  public String toString() {
    return "Dim{" + "percentage=" + percentage + ", sentAt=" + sentAt.toString() + '}';
  }
}
