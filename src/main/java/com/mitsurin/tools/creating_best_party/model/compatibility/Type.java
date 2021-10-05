package com.mitsurin.tools.creating_best_party.model.compatibility;

public enum Type {
    Empty(""),
    Normal("ノーマル"),
    Heating("ほのお"),
    Water("みず"),
    Voltage("でんき"),
    Leaf("くさ"),
    Ice("こおり"),
    Combat("かくとう"),
    Poison("どく"),
    Temblor("じめん"),
    Aviation("ひこう"),
    Esper("エスパー"),
    Bug("むし"),
    Rock("いわ"),
    Ghost("ゴースト"),
    Dragon("ドラゴン"),
    Mischief("あく"),
    Steel("はがね"),
    Fairy("フェアリー");

    public final String name;

    Type(String name) {
        this.name = name;
    }

    public static Type getTypeByTypeName(String name) {
        for (Type type : Type.values()) {
            if(name.equals(type.toString())) return type;
        }
        return null;
    }

    public static Type getTypeByName(String name) {
        for (Type type : Type.values()) {
            if(name.equals(type.name)) return type;
        }
        return null;
    }
}
