import Pair from "/script/pair.js";

export default class Type {
    typeEnum;
    typeName;

    constructor(typeEnum, typeName) {
        this.typeEnum = typeEnum;
        this.typeName = typeName;
    }

    getTypeEnum() {
        return this.typeEnum;
    }

    getTypeName() {
        return this.typeName;
    }
}

export const TYPES = new Array(
    new Type("Empty", ""),
    new Type("Normal", "ノーマル"), 
    new Type("Heating", "ほのお"),
    new Type("Water", "みず"),
    new Type("Voltage", "でんき"),
    new Type("Leaf", "くさ"),
    new Type("Ice", "こおり"),
    new Type("Combat", "かくとう"),
    new Type("Poison", "どく"),
    new Type("Temblor", "じめん"),
    new Type("Aviation", "ひこう"),
    new Type("Esper", "エスパー"),
    new Type("Bug", "むし"),
    new Type("Rock", "いわ"),
    new Type("Ghost", "ゴースト"),
    new Type("Dragon", "ドラゴン"),
    new Type("Mischief", "あく"),
    new Type("Steel", "はがね"),
    new Type("Fairy", "フェアリー")
);

export const RESULT_SYMBOLS = new Array(
    new Pair(-3, "×"), 
    new Pair(-2, "▲"), 
    new Pair(-1, "△"), 
    new Pair(1, "○"), 
    new Pair(2, "◎")
);

/**
 * * タイプ名取得
 */
export function getTypeName(typeEnum) {
    for(let i = 0; i < TYPES.length; i++) {
        if(typeEnum == TYPES[i].getTypeEnum()) return TYPES[i].getTypeName();
    }

    return "";
}

/**
 * * 結果値から表示記号取得
 */
export function getResultSymbol(result) {
    for(let i = 0; i < RESULT_SYMBOLS.length; i++) {
        if(result == RESULT_SYMBOLS[i].getKey()) return RESULT_SYMBOLS[i].getValue();
    }

    return "";
}