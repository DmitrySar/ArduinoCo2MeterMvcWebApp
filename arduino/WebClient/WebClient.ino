#include <DHT.h>
#include <UIPEthernet.h>
#include <Adafruit_CCS811.h>

Adafruit_CCS811 ccs;

EthernetClient client;
//server address
char server[] = "130.61.244.38";
//server port
int serverPort = 8080;

//my address
uint8_t mac[6] = {0x00,0x01,0x02,0x03,0x04,0x05};
IPAddress myIP(192,168,1,63);
byte myDns[] = {192, 168, 1, 5};  // адрес DNS-сервера
byte gateway[] = {192, 168, 1, 5};  // адрес сетевого шлюза
byte subnet[] = {255, 255, 255, 0};  // маска подсети

//server link pin
int linkPin = A2;

//Pin DHT
int inputPin = 3;
#define DHTTYPE DHT22
DHT dht(inputPin, DHTTYPE);

void setup() {
  
  pinMode(linkPin, OUTPUT);
  digitalWrite(linkPin, HIGH);
  dht.begin();
  if(!ccs.begin()){
    while(1);
  }
  while(!ccs.available());
  ccs.setEnvironmentalData(dht.readHumidity(), dht.readTemperature());

}

void loop() {
 float h = dht.readHumidity();
 float t = dht.readTemperature();

 if(ccs.available()){
  ccs.setEnvironmentalData(h, t);
  if(!ccs.readData()){
    float tvoc = ccs.getTVOC();
    float co2 = ccs.geteCO2();
    
    doGet(String(t), String(h), String(tvoc), String(co2)); //seng GET request
    while(client.connected()){
      if(client.available()){
        char c = client.read();
      }
    }
  }
 }

}

// http get request
void doGet(String t, String h, String tvoc, String co2) {
    Ethernet.begin(mac, myIP, myDns, gateway, subnet);
    if (client.connect(server, serverPort)){
      digitalWrite(linkPin, LOW);
      client.print(F("GET /sens?t="));
      client.print(t);
      client.print(F("&h="));
      client.print(h);
      client.print(F("&tvoc="));
      client.print(tvoc);
      client.print(F("&co2="));
      client.print(co2);
      client.println(F(" HTTP/1.1"));
      client.print(F("Host: "));
      client.print(server);
      client.print(":");
      client.println(serverPort);
      client.println(F("Connection: keep-alive"));
      client.println(F("Upgrade-Insecure-Requests: 1"));
      client.println(F("User-Agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Mobile Safari/537.36"));
      client.println(F("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"));
      client.println(F("Accept-Encoding: gzip, deflate"));
      client.println(F("Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7"));
      client.println();
  }else{
      digitalWrite(linkPin, HIGH);
  }
}
