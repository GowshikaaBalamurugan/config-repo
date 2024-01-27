package com.dkart.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private String currency;
    private BigDecimal amount;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long priceId;


}
