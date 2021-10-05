package com.mitsurin.tools.creating_best_party.model.compatibility;

public enum Type {
    Normal(0, "ノーマル"),
    Heating(1, "ほのお"),
    Water(2, "みず"),
    Voltage(3, "でんき"),
    Leaf(4, "くさ"),
    Ice(5, "こおり"),
    Combat(6, "かくとう"),
    Poison(7, "どく"),
    Temblor(8, "じめん"),
    Aviation(9, "ひこう"),
    Esper(10, "エスパー"),
    Bug(11, "むし"),
    Rock(12, "いわ"),
    Ghost(13, "ゴースト"),
    Dragon(14, "ドラゴン"),
    Mischief(15, "あく"),
    Steel(16, "はがね"),
    Fairy(17, "フェアリー");

    public final int id;
    public final String name;

    Type(int id, String name) {
        this.id = id;
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
