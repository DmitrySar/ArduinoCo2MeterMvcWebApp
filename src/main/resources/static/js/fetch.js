const url = "http://" + location.host + "/";
const TIMEOUT = 30000;

function startAsync() {
    start();
    setInterval(() => start(), TIMEOUT);
}

function start() {
    temperatureOut('temperature');
    humidityOut('humidity');
    tvocOut('tvoc');
    co2Out('co2');
}

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
    doGet(url + id, id, 400, 1000);
}

function temperatureOut(id) {
    doGet(url + id, id, 0, 50);
}

function humidityOut(id) {
    doGet(url + id, id, 0, 100);
}

function tvocOut(id) {
    doGet(url + id, id, 0, 100);
}

function doGet(url, id, min, max) {
    fetch(url).then(function(response) {
        if(response.ok) {
            response.json().then(function(s) {
                sensor.min = min;
                sensor.max = max;
                sensor.value = s.value;
                sensor.time = s.time.substr(11, 5);
                calc(id, sensor);
            });
        } else {
            console.log('Network request for products.json failed with response ' + response.status + ': ' + response.statusText);
        }
    });
}

function addLocalCO2(value) {
    sensor.min = 400;
    sensor.max = 1000;
    sensor.value = value;
    sensor.time = "12:00";//time.substr(11, 5);
    calc('co2', sensor);
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