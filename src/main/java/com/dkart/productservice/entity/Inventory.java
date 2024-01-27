package com.dkart.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private long total;
    private long available;

    private long reserved;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
   @Column(name = "inventory_id")
    private Long inventoryId;


}
