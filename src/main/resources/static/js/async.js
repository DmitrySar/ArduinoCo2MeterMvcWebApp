const url='http://localhost:8080/getsensor';
const TIMEOUT = 3000;
const DECIMAL_LENGTH = 4;


function startAsync() {
    setInterval(
        () => doGet(),
        TIMEOUT
    );
}

function doGet() {
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
         document.getElementById("graph").innerHTML = tmpText;
    }
}

function addTextToGraph(t, tmpText) {
    let tmp = tmpText;
    return tmp + setTextToGraph(t);
}

function setTextToGraph(t) {
    return decorate(t.substring(0, DECIMAL_LENGTH));
}

function decorate(t) {
    let v = 2 * parseFloat(t);
    return '<div class="item" style="height:'+v+'px;margin-top:'+(110 - v)+'px">'+t+'</div>';
}