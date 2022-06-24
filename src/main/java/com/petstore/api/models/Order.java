package com.petstore.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Order {
    int id;
    int petId;
    int quantity;
    String shipDate;
    OrderStatus status;
    boolean complete;
}
