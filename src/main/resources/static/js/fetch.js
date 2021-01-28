const url = "http://192.168.1.28:8080/";

function out(a) {
    doGet(url + a);
}

function doGet(url) {
    fetch(url).then(function(response) {
        if(response.ok) {
            response.json().then(function(sensor) {
                alert("value:\n" + sensor.value
                    + "\ntime:\n" + sensor.time);
            });
        } else {
            console.log('Network request for products.json failed with response ' + response.status + ': ' + response.statusText);
        }
    });
}