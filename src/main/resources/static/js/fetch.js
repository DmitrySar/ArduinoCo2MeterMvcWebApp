const url = "http://192.168.1.28:8080/";

// значения датчика СО2
sensor = {
    max: 1000,
    value: 700,
    time: "12:15",
    min: 400,

    getColor: function() {
        let color = this.value > this.max ? "#ed0949" : "#00508c";
        return color;
    }
};

// Высота графика
const H = 250;
// отступ нижней подписи
const DELTA = 10;

function insertData (id) {
    sensor.time = getTime();
    sensor.value = (Math.random() * (1500 - sensor.min) + sensor.min).toFixed(0);
    calc(id, sensor);
}

// Параметры столбца
parameters = {
    k: 1,
    h: 1,
    mTop: 1
};

function out(id) {
    doGet(url + id);
    calc(id, sensor);
}

function doGet(url) {
    fetch(url).then(function(response) {
        if(response.ok) {
            response.json().then(function(s) {
                sensor.value = s.value;
                // sensor.time = s.time;
            });
        } else {
            console.log('Network request for products.json failed with response ' + response.status + ': ' + response.statusText);
        }
    });
}

// Расчёт параметров
function calc(id, sensor) {
    parameters.k = H / sensor.max;
    let h = sensor.value * parameters.k;
    parameters.h = h < H ? h : H;
    parameters.mTop = H - parameters.h;
    outElement(id, parameters, sensor);
}

function outElement(id, parameters, sensor) {
    addInnerHTML(id, parameters, sensor);
}

function addInnerHTML(id, parameters, sensor) {
    let element = document.getElementById(id);
    element.innerHTML = element.innerHTML + `<div class="div" style="margin-top: ${parameters.mTop}px; height: ${parameters.h}px; background-color: ${sensor.getColor()};">${sensor.value}`
        + `<div class="innerDiv" style="margin-top: ${parameters.h - DELTA}px;">${sensor.time}`
        + `</div></div>`;

}

function getTime() {
    let today = new Date();
    let h = today.getHours();
    let m = today.getMinutes();

    return ("0" + h).slice(-2) + ":" + ("0" + m).slice(-2);
}