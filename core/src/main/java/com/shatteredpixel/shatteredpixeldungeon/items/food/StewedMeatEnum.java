package com.shatteredpixel.shatteredpixeldungeon.items.food;

public enum StewedMeatEnum {
    STEWED_MEAT_ONE(1),
    STEWED_MEAT_TWO(2),
    STEWED_MEAT_THREE(3);

    private final int quantity;

    StewedMeatEnum(int quantity) {
        if (quantity < 1 || quantity > 3) {
            throw new IllegalArgumentException("StewedMeat Quantity value must be between 1 and 3.");
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}