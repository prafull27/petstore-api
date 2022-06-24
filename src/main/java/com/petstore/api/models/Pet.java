package com.petstore.api.models;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Pet {
    int id;
    String name;
    Category category;
    List<String> photoUrls;
    List<Tag> tags;
    PetStatus status;
}
