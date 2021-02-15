//#include <DHT.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <UIPEthernet.h>
#include <Adafruit_CCS811.h>

Adafruit_CCS811 ccs;

EthernetClient client;
//server address
char server[] = "192.168.1.28";
//server port
int serverPort = 8080;

//my address
uint8_t mac[6] = {0x00,0x01,0x02,0x03,0x04,0x05};
IPAddress myIP(192,168,1,63);

//server link pin
int linkPin = A2;

//Pin DHT
int inputPin = 3;
OneWire oneWire(inputPin);
DallasTemperature ds(&oneWire);
//#define DHTTYPE DHT11
//DHT dht(inputPin, DHTTYPE);

void setup() {
  // Serial.begin(9600);
  
  pinMode(linkPin, OUTPUT);
  digitalWrite(linkPin, HIGH);

  //dht.begin();
  ds.begin();
  if(!ccs.begin()){
    while(1);
  }
  while(!ccs.available());
//  ccs.setEnvironmentalData(dht.readHumidity(), dht.readTemperature());
}

void loop() {
  ds.requestTemperatures();
 float h = 25;//dht.readHumidity();
 float t = ds.getTempCByIndex(0);//dht.readTemperature();
 if(ccs.available()){
  ccs.setEnvironmentalData(h, t);
  if(!ccs.readData()){
    float tvoc = ccs.getTVOC();
    float co2 = ccs.geteCO2();
    doGet(String(t), String(h), String(tvoc), String(co2)); //seng GET request
    while(client.connected()){
      if(client.available()){
        char c = client.read();
        // Serial.print(c);
      }
    }
  }
 }
//  delay(500);
}

// http get request
void doGet(String t, String h, String tvoc, String co2) {
    Ethernet.begin(mac, myIP);
    if (client.connect(server, serverPort)){
      // Serial.println(F("Connected to server"));
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
      client.println(server);
      client.println();
  }else{
      // Serial.println(F("Connection to server failed"));
      digitalWrite(linkPin, HIGH);
  }
}
