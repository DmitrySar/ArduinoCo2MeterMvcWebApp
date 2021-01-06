function doGet() {
    const Http = new XMLHttpRequest();
    const url='http://localhost:8080/getsensor';
    Http.open("GET", url);
    Http.send();

    Http.onreadystatechange = (e) => {
        let text = Http.responseText;
        text = text.substring(1, text.length - 1);
        let texts = text.split(",");
        let tmpText = setTextToGraph(texts[0]);
        for(let i = 1; i < texts.length; i++) {
            tmpText = addTextToGraph(texts[i], tmpText);
        }
        document.getElementById("graph").innerHTML = tmpText;
    }
}

function addTextToGraph(t, tmpText) {
    let tmp = tmpText;
    return tmp + decorate(t.substring(0, 4));
}

function setTextToGraph(t) {
    return decorate(t.substring(0, 4));
}

function decorate(t) {
    let v = 2 * parseFloat(t);
    return '<div class="item" style="height:'+v+'px;margin-top:'+(110 - v)+'px">'+t+'</div>';
}