#include <vector>
 
#include <MQTT.h>
#include <WiFi.h>
#ifdef __SMCE__
#include <OV767X.h>
#endif
 
#include <Smartcar.h>
 
MQTTClient mqtt;
WiFiClient net;
 
 
//empty for local host connection
const char ssid[] = " ";
const char pass[] = " ";
const String speedometer = "car/status/odometer/speed";

ArduinoRuntime arduinoRuntime;
BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);
 
const auto pulsesPerMeter = 600;
const auto oneSecond = 1000UL;
const auto triggerPin = 6;
const auto echoPin = 7;
const unsigned int maxDistance = 100;

//Top Sensor
GY50 gyroscope(arduinoRuntime, 37);
 
//Ultrasonic
SR04 front(arduinoRuntime, triggerPin, echoPin, maxDistance);
 
//Odometer
DirectionlessOdometer leftOdometer{ arduinoRuntime,
                                    smartcarlib::pins::v2::leftOdometerPin,
                                    []() { leftOdometer.update(); },
                                    pulsesPerMeter };
DirectionlessOdometer rightOdometer{ arduinoRuntime,
                                     smartcarlib::pins::v2::rightOdometerPin,
                                     []() { rightOdometer.update(); },
                                     pulsesPerMeter };
SmartCar car(arduinoRuntime, control, gyroscope, leftOdometer, rightOdometer); // add distance
   
std::vector<char> frameBuffer;
 
void setup() {
  Serial.begin(9600);
  
#ifdef __SMCE__
  Camera.begin(QVGA, RGB888, 15);
  frameBuffer.resize(Camera.width() * Camera.height() * Camera.bytesPerPixel());
#endif
 
  WiFi.begin();
  mqtt.begin("127.0.0.1", 1883, net);
 
  Serial.println("Connecting to WiFi...");
  auto wifiStatus = WiFi.status();
  while (wifiStatus != WL_CONNECTED && wifiStatus != WL_NO_SHIELD) {
    Serial.println(wifiStatus);
    Serial.print(".");
    delay(1000);
    wifiStatus = WiFi.status();
  }
 
 
  Serial.println("Connecting to MQTT broker");
  while (!mqtt.connect("arduino", "public", "public")) {
    Serial.print(".");
    delay(1000);
  }
 
  mqtt.subscribe("/smartcar/control/#", 1);
  mqtt.onMessage([](String topic, String message) {
    if (topic == "/smartcar/control/takeInput") {
      takeInput(message);
    } else {
      Serial.println(topic + " " + message);
    }
  });
}
 
void takeInput(String input) {
        int inputSelection = input.substring(0,1).toInt();
            int appInput;
            if(input.length() > 1) {
              unsigned int stringInput = input.substring(1).toInt(); 
              appInput = stringInput;
            }
            
            switch(inputSelection) {
              case 2:  //forward
                car.setSpeed(appInput); // incrementing number from app to go forward
                break;
              
              case 3:  //backwards
                car.setSpeed(-appInput); // incrementing number from app to go backwards
                break;
            
              case 4:  //right
                  car.setAngle(-appInput); // incrementing number from app to turn right
                break;
            
              case 5:  //left
                  car.setAngle(appInput); // incrementing number from app to turn right
                break;
                
              case 7: //stop
                car.setSpeed(0);
                car.setAngle(0);
              break;
              
              default:
                break;
            }
        }

void loop() {
 
  if (mqtt.connected()) {
    mqtt.loop();
    const auto currentTime = millis();
    mqtt.publish(speedometer, String(car.getSpeed()));
#ifdef __SMCE__
    static auto previousFrame = 0UL;
    if (currentTime - previousFrame >= 65) {
      previousFrame = currentTime;
      Camera.readFrame(frameBuffer.data());
      mqtt.publish("/smartcar/camera", frameBuffer.data(), frameBuffer.size(),
                   false, 0);
    }
#endif
    static auto previousTransmission = 0UL;
    if (currentTime - previousTransmission >= oneSecond) {
      previousTransmission = currentTime;
      const auto distance = String(front.getDistance());
      mqtt.publish("/smartcar/speedometer", String(car.getSpeed()));
      mqtt.publish("/smartcar/ultrasound/front", distance);
    }
    
  }
#ifdef __SMCE__
  // Avoid over-using the CPU if we are running in the emulator
  delay(1);
#endif
}
