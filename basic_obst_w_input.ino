#include <Smartcar.h>

ArduinoRuntime arduinoRuntime;

//Motors
BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

const int triggerPin           = 6; 
const int echoPin              = 7; 
const unsigned int maxDistance = 100;
const auto pulsesPerMeter = 600;
const int lDegree = -25; // only 25* so it doesnt turn so much
const int rDegree = 25;


//Top Sensor
GY50 gyroscope(arduinoRuntime, 37);
//Ultrasonic
SR04 front{arduinoRuntime, triggerPin, echoPin, maxDistance};

//Odometer
DirectionlessOdometer leftOdometer{ arduinoRuntime,
                                    smartcarlib::pins::v2::leftOdometerPin,
                                    []() { leftOdometer.update(); },
                                    pulsesPerMeter };
DirectionlessOdometer rightOdometer{ arduinoRuntime,
                                     smartcarlib::pins::v2::rightOdometerPin,
                                     []() { rightOdometer.update(); },
                                     pulsesPerMeter };

SmartCar car(arduinoRuntime, control, gyroscope, leftOdometer, rightOdometer);

void setup()
{
    Serial.begin(9600);
    carGo();
  
}
//car is moving at 60$% of its speed
void carGo() {
        car.setSpeed(60);

}

//detects obstacles within distance range
void obstacle() {
    const auto distance = front.getDistance();
 if (distance > 0 && distance < 100) {
    car.setSpeed(0);
 }
}

// input A D makes the car turn left / right X degrees
void input(){
    if (Serial.available())
    {
        char input = Serial.read(); // read everything that has been received so far and log down
                                    // the last entry
        switch (input)
        {
        case 'a': // rotate counter-clockwise going forward
            car.setSpeed(60);
            car.setAngle(lDegree);
            break;
        case 'd': // turn clock-wise
            car.setSpeed(60);
            car.setAngle(rDegree);
            break;
        default: // if you receive something that you don't know, just stop
            car.setSpeed(0);
            car.setAngle(0);
        }
    }
}

 void loop()
{
   obstacle();
   input();
    
#ifdef __SMCE__
//avoid overloading of cpu when emulator is running
  delay(1);
#endif
}
