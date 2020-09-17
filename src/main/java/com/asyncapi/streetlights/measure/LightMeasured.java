package com.asyncapi.streetlights.measure;

import com.asyncapi.api.annotations.Reference;
import com.asyncapi.api.annotations.schema.Schema;
import com.asyncapi.api.annotations.schema.SchemaProperty;
import com.asyncapi.api.annotations.schema.SchemaType;

import java.time.LocalDateTime;

@Schema(
    name = "lightMeasuredPayload",
    type = SchemaType.OBJECT,
    properties = {
        @SchemaProperty(
            name = "lumens",
            type = SchemaType.INTEGER,
            description = "Light intensity measured in lumens.",
            minimum = "0"
        ),
        @SchemaProperty(
            ref = @Reference(ref = "#/components/schemas/sentAt")
        )
    }
)
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
