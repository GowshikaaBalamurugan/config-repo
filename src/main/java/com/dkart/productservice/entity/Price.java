package com.dkart.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @NotNull(message = "currency is required")
    @Column(nullable = false)
    @Size(min = 3,max = 3,message = "Currency should be of length 3.eg:USD,INR,etc")
    private String currency;

    @Column(nullable = false)
    @NotNull(message = "Amount is required")
    @Min(value = 1L,message = "Amount should be minimum of Rs.1")
    private BigDecimal amount;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "price_id")
    private Long priceId;


}
