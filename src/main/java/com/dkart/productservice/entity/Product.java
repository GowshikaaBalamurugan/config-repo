package com.dkart.productservice.entity;

import com.dkart.productservice.validation.ValidInventory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long productId;
    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    @Size(min = 3, message = "Product name must be min of length 3 ")
    private String name;
    @NotBlank(message = "Product brand is required")
    @Column(nullable = false)
    @Size(min = 3, message = "Product brand must be min of length 3 ")
    private String brand;
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_id", referencedColumnName = "price_id")
    @NotNull
    private Price price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "inventory_id")
    @NotNull
    @ValidInventory
    private Inventory inventory;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "attr_id", referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    @Size(min = 2,message = "Product must contain atleast 2 attributes")
    private Set<Attribute> attributes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

}
