
#include <ClosedCube_HDC1080.h>
#include <OneWire.h>
#include <DallasTemperature.h>

int inputPin = A2;

OneWire oneWire(inputPin);
DallasTemperature ds(&oneWire);

ClosedCube_HDC1080 hdc1080;

void setup() {
  Serial.begin(9600);
  hdc1080.begin(0x40);
  hdc1080.setResolution(HDC1080_RESOLUTION_11BIT, HDC1080_RESOLUTION_14BIT);
  Serial.println(F("e, t, dT"));
  pinMode(inputPin, INPUT);

}

void loop() {
  ds.requestTemperatures();
  float e = ds.getTempCByIndex(0);
  float t = hdc1080.readTemperature();

  Serial.print(e);
  Serial.print(F(", "));
  Serial.print(t);
  Serial.print(F(", "));
  Serial.println(t - e);

  delay(1000);
}
