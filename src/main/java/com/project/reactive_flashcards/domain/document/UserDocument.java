package com.project.reactive_flashcards.domain.document;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;

@Document(collection = "users")
public record UserDocument(@Id String id, String name, String email,
    @CreatedDate @Field("created_at") OffsetDateTime createdAt,
    @LastModifiedDate @Field("updated_at") OffsetDateTime updatedAt) {

  @Builder(toBuilder = true)
  public UserDocument {
  }
}
