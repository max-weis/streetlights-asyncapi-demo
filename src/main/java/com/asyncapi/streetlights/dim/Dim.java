package com.asyncapi.streetlights.dim;

import java.time.LocalDateTime;

public class Dim {

  private int percentage;

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
