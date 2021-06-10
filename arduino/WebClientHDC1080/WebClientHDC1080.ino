//#include <DHT.h>
#include <UIPEthernet.h>
#include <Adafruit_CCS811.h>
#include <ClosedCube_HDC1080.h>

Adafruit_CCS811 ccs;

EthernetClient client;
//server address
char server[] = "192.168.1.28";
//server port
int serverPort = 8080;

//my address
uint8_t mac[6] = {0x00,0x01,0x02,0x03,0x04,0x05};
IPAddress myIP(192,168,1,66);

//server link pin
int linkPin = A2;

//Pin DHT
//int inputPin = 3;
//#define DHTTYPE DHT11
//DHT dht(inputPin, DHTTYPE);
ClosedCube_HDC1080 hdc1080;

void setup() {
  //Serial.begin(9600);
  //pinMode(inputPin, INPUT);
  hdc1080.begin(0x40);
  hdc1080.setResolution(HDC1080_RESOLUTION_11BIT, HDC1080_RESOLUTION_14BIT);
  
  pinMode(linkPin, OUTPUT);
  digitalWrite(linkPin, HIGH);
  
  if(!ccs.begin()){
    while(1);
  }
  while(!ccs.available());
  //ccs.setEnvironmentalData(dht.readHumidity(), dht.readTemperature());
  ccs.setEnvironmentalData(hdc1080.readHumidity(), hdc1080.readTemperature());
}

void loop() {
 //float h = dht.readHumidity();
 //float t = dht.readTemperature();
 float t = hdc1080.readTemperature();
 float h = hdc1080.readHumidity();

 if(ccs.available()){
  ccs.setEnvironmentalData(h, t);
  if(!ccs.readData()){
    float tvoc = ccs.getTVOC();
    float co2 = ccs.geteCO2();

      //Serial.print(t);
      //Serial.print(F(", "));
      //Serial.print(h);
      //Serial.print(F(", "));
      //Serial.print(ccs.getTVOC());
      //Serial.print(F(", "));
      //Serial.println(ccs.geteCO2());

    
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
      // Serial.println(F("Connection to server failed"));
      digitalWrite(linkPin, HIGH);
  }
}
