package ru.rsreu.medicinalPlants;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Gears {

    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        CARDIOVASCULAR_DISEASES,LAXATIVES, SWEATSHOPS, EXPECTORANTS, SOOTHING, ANTI_INFLAMMATORY, VITAMIN_SUPPLEMENTS
    }
}
