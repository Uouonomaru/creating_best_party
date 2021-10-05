/**
 * * import
 * ? class (Type)
 * ? constant (TYPES)
 * ? class (Pair)
 */
import Type from "/script/type.js";
import { TYPES, RESULT_SYMBOLS } from "/script/type.js";
import { getTypeName, getResultSymbol } from "/script/type.js";
import Pair from "/script/pair.js";

/**
 * * グローバル変数
 */
var type1Obj, type2Obj;
var masterDataList;

/**
 * * onload時の処理
 * ? 1. HTMLの初期化処理
 * ? 2. クリック系イベントの定義
 */
window.onload = () => {
    initializeHTML();
    defineClickEvent();
    defineChangeEvent();
};

/**
 * * HTMLの初期化処理
 * ? グローバル変数の初期化
 * ? selectの候補をスクリプト生成
 * ? masterデータ準備
 */
function initializeHTML() {
    type1Obj = document.getElementById("type1");
    type2Obj = document.getElementById("type2");
    masterDataList = new Array();

    for(let i = 0; i < TYPES.length; i++) {
        let type = TYPES[i];

        let value = type.getTypeEnum();
        let text = type.getTypeName();

        let obj1 = document.createElement("option");
        obj1.setAttribute("value", value);
        obj1.textContent = text;
        let obj2 = document.createElement("option");
        obj2.setAttribute("value", value);
        obj2.textContent = text;
        
        type1Obj.appendChild(obj1);
        type2Obj.appendChild(obj2);
    }

    Array.from(type1Obj.children).forEach(child => {
        masterDataList.push(child.cloneNode(true));
    });
}

/**
 * * クリック系イベントの定義
 */
function defineClickEvent() {
    new Array(
        new Pair(document.getElementById("CreatingParty"), () => {
            let type1 = type1Obj.options[type1Obj.options.selectedIndex].getAttribute("value");
            let type2 = type2Obj.options[type2Obj.options.selectedIndex].getAttribute("value");

            let xhr = new XMLHttpRequest();
            xhr.open("GET", "/CreatingParty/Result?t1=" + type1 + "&t2=" + type2);
            xhr.send();

            xhr.onreadystatechange = () => {
                if(xhr.readyState == 4 && xhr.status == 200) {
                    let response = xhr.response;
                    let json = JSON.parse(response);

                    outputResultJSON(json);
                }
            };
        })
    ).forEach(pair => {
        pair.getKey().addEventListener("click", () => {
            pair.getValue()();
        });
    });
}

/**
 * * 変更系イベントの定義
 */
function defineChangeEvent() {
    type1Obj.addEventListener("change", ev => {
        while(type2Obj.children.length > 1) type2Obj.removeChild(type2Obj.lastChild);

        let index = ev.target.options.selectedIndex;

        for(let i = 1; i < masterDataList.length; i++) {
            if(i == index) continue;
            type2Obj.appendChild(masterDataList[i].cloneNode(true));
        }
    });
}

/**
 * * 結果表示用メソッド
 */
function outputResultJSON(json) {
    let resultArea = document.getElementById("resultArea");
    while(resultArea.firstChild) resultArea.removeChild(resultArea.firstChild);

    json.forEach(party => {
        let compatibilities = party["compatibilities"];

        let partyTable = document.createElement("table");
        partyTable.setAttribute("border", "1");
        initializePartyTable(partyTable);

        compatibilities.forEach(compatibility => {
            let resultRow = document.createElement("tr");

            let types = compatibility["types"];
            types.forEach(type => {
                let typeHeading = document.createElement("th");
                typeHeading.textContent = getTypeName(type);

                resultRow.appendChild(typeHeading);
            });

            let weakpoints = compatibility["weakpoints"];
            TYPES.forEach(type => {
                if(type.getTypeEnum() != "Empty") {
                    let weakpointData = document.createElement("td");

                    weakpoints.forEach(weakpoint => {
                        if(weakpoint[type.getTypeEnum()] != undefined) {
                            weakpointData.textContent = getResultSymbol(weakpoint[type.getTypeEnum()]);
                        }
                    });

                    resultRow.appendChild(weakpointData);
                }
            });

            partyTable.appendChild(resultRow);
        });

        resultArea.appendChild(partyTable);
    });
}

/**
 * * テーブル初期化
 */
function initializePartyTable(partyTable) {
    let headingRow = document.createElement("tr");

    let type1Heading = document.createElement("th");
    type1Heading.textContent = "タイプ１";
    let type2Heading = document.createElement("th");
    type2Heading.textContent = "タイプ２";

    headingRow.appendChild(type1Heading);
    headingRow.appendChild(type2Heading);

    TYPES.forEach(type => {
        if(type.getTypeEnum() != "Empty") {
            let typeHeading = document.createElement("th");
            typeHeading.textContent = type.getTypeName();
            
            headingRow.appendChild(typeHeading);
        }
    });

    partyTable.appendChild(headingRow);
}