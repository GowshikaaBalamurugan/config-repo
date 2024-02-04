package com.dkart.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @NotNull(message = "Total is required")
    @Column(nullable = false)
    @Min(value = 5,message = "Total products cannot be below 5")
    private long total;
    @Column(nullable = false)
    @Min(value = 3,message = "Minimum availabity of product must be 3")
    private long available;
    @Column(nullable = false)
    @Min(value = 2,message = "Minimum availabity of reserved product must be 2")
    private long reserved;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
   @Column(name = "inventory_id")
    private Long inventoryId;


}
