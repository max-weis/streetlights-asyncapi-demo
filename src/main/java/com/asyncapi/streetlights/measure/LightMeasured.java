package com.asyncapi.streetlights.measure;
import java.time.LocalDateTime;

import io.smallrye.asyncapi.spec.annotations.schema.Schema;
import io.smallrye.asyncapi.spec.annotations.schema.SchemaType;

@Schema(name = "lightMeasuredPayload", type = SchemaType.OBJECT)
public class LightMeasured {

  @Schema(description = "Light intensity measured in lumens.", minimum = "0", required = true)
  private int lumens;

  @Schema(ref = "#/components/schemas/sentAt")
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
}
