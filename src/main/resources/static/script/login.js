import Pair from "/script/pair.js"

window.onload = () => {
    setButtonClickEventListener();
};

function setButtonClickEventListener() {
    new Array(
        new Pair(document.getElementById("new_account"), () => { window.location.href = "/new_account" })
    ).forEach(pair => {
        pair.getKey().addEventListener("click", () => {
            pair.getValue()();
        });
    });
}