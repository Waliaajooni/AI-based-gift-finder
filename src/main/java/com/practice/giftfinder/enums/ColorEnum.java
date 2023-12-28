package com.practice.giftfinder.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ColorEnum {
    YELLOW ("yellow", "lemon"), BLUE("blue", "sky blue, navy blue"), GREEN("green", "olive green"), ORANGE("orange", "indigo"),
    PURPLE("purple", "violet"), RED("red", "maroon"), PINK("pink", "magenta"), BROWN("brown", "coffee"),
    BEIGE("beige", "camel"), GOLD("gold", "golden"), SILVER("silver", "shining"),
    WHITE("white", "cream"), BLACK("black", "jet black"),
    GREY("grey", "grey"), DEFAULT("default", "multicolor");

    private String name;
    private String synonyms;

    ColorEnum(String name, String synonyms) {
        this.name = name;
        this.synonyms = synonyms;
    }

    public ColorEnum getRespectiveColor(String color) {
        Optional<ColorEnum> optionalColorEnum = Arrays.stream(ColorEnum.values()).filter(colorEnum -> colorEnum.name.equalsIgnoreCase(color) || colorEnum.synonyms.contains(color)).findAny();

        return optionalColorEnum.isEmpty() ? ColorEnum.DEFAULT : optionalColorEnum.get();
    }
}
