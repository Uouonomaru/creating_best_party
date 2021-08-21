export default class Pair {
    key;
    value;

    constructor(key, value) {
        this.key = key;
        this.value = value;
    }

    getKey() {
        return this.key;
    }

    getValue() {
        return this.value;
    }
}