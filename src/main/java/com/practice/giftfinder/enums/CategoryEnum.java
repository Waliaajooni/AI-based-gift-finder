package com.practice.giftfinder.enums;

public enum CategoryEnum {

    WOMENS_CLOTHES("women_clothes"), MENS_CLOTHS("men_clothes"), ELECTRONICS("electronics"), DECOR("decor"),
    FURNITURE("furniture"), SILVERWARE("silverware"), KITCHEN("kitchen_items"), LUGGAGE("luggages"), MEN_SHOES("men_shoes"),
    WOMEN_SHOES("women_shoes"), JEWELLERY("jewellery"), TECH_ACCESSORIES("tech_accessories");

    private String name;

    CategoryEnum(String name) {
        this.name = name;
    }
}
