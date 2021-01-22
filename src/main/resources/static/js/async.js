//const url='http://192.168.1.28:8080/getsensor';
const TIMEOUT = 10_000;
const DECIMAL_LENGTH = 4;


function startAsync() {
    setInterval(
        () => {
            doGet('http://192.168.1.28:8080/getsensor', 'graph');
            doGet('http://192.168.1.28:8080/gettvoc', 'tvoc');
        },
        TIMEOUT
    );
}

function doGet(url, id) {
    const Http = new XMLHttpRequest();

    Http.open("GET", url);
    Http.send();

    Http.onreadystatechange = (e) => {
        let text = Http.responseText;
        text = text.substring(1, text.length - 1);
        let texts = text.split(",");
        let tmpText = "";
        texts.forEach((t) => {
            tmpText = addTextToGraph(t, tmpText);
        });
         document.getElementById(id).innerHTML = tmpText;
    }
}

function addTextToGraph(t, tmpText) {
    let tmp = tmpText;
    return tmp + setTextToGraph(t);
}

function setTextToGraph(t) {
    let v = parseFloat(t);
    //масштабирование 50 - максимальное значение на графике
    let k = v > 50 ? 50 / v : 1;
    return decorate(t.substring(0, DECIMAL_LENGTH), k);
}

function decorate(t, k) {
    let v = k * 2 * parseFloat(t);
    return '<div class="item" style="' + getColor(k) + ' height:'+v+'px;margin-top:'+(110 - v)+'px">'+t+'</div>';
}

function getColor(k) {
    return k < 1 ? "background-color: #d00318;" : "background-color: #00508C;";
}