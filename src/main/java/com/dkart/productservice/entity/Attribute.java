package com.dkart.productservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    @NotBlank(message = "Attribute name is required")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Attribute value is required")
    @Column(nullable = false)
    private String value;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attr_id")
    private Long attrId;



}
