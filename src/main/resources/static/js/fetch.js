const url = "http://" + location.host + "/";

// значения датчика СО2
sensor = {
    max: 1000,
    value: 0,
    time: "12:15",
    min: 400,

    getColor: function() {
        return this.value > this.max ? "#ed0949" : "#00508c";
    }
};

// Высота графика
const H = 250;
// отступ нижней подписи
const DELTA = 10;

// Параметры столбца
parameters = {
    k: 1,
    h: 1,
    mTop: 1
};

function co2Out(id) {
    sensor.max = 1000;
    sensor.min = 400;
    doGet(url + id, id);
}

function temperatureOut(id) {
    sensor.max = 50;
    sensor.min = 0;
    doGet(url + id, id);
}

function humidityOut(id) {
    sensor.max = 100;
    sensor.min = 0;
    doGet(url + id, id);
}

function tvocOut(id) {
    sensor.max = 100;
    sensor.min = 0;
    doGet(url + id, id);
}

function doGet(url, id) {
    fetch(url).then(function(response) {
        if(response.ok) {
            response.json().then(function(s) {
                sensor.value = s.value;
                sensor.time = s.time.substr(11, 5);
                calc(id, sensor);
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