package com.dkart.productservice.validation;

import com.dkart.productservice.entity.Inventory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InventoryValidator implements ConstraintValidator<ValidInventory,Object> {
    @Override
    public void initialize(ValidInventory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value!=null && isValidInventory((Inventory)value);
    }

    private boolean isValidInventory(Inventory value) {
        return value.getTotal()>=value.getAvailable() && value.getAvailable()>= value.getReserved();
    }
}
