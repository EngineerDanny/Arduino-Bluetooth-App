# Arduino_Bluetooth
It helps the user to control lights and the fans connected to the arduino using bluetooth


This is basically the code for the arduino bluetooth app

//declaring our constants
#define ledOne_Two 2
#define ledThree_Four 3
#define ledFive_Six 4
#define ledSeven_Eight 5
#define ledNine_Ten 6
#define ledEleven_Twelve 7 


#define fanPin 9
#define led_brightness_pin 10


char command;  
String string;


void setup() { 
Serial.begin(9600);  //Setting up the baud rate to the default value which is 9600

//Declaring the modes as output
pinMode(ledOne_Two, OUTPUT);  
pinMode(ledThree_Four, OUTPUT);
pinMode(ledFive_Six, OUTPUT);
pinMode(ledSeven_Eight, OUTPUT);
pinMode(ledNine_Ten, OUTPUT);
pinMode(ledEleven_Twelve, OUTPUT);

pinMode(led_brightness_pin, OUTPUT);
pinMode(fanPin,OUTPUT);
} 

void loop() { 
  
  if (Serial.available() > 0) 		//Checking if serial reads any data 
  {string = "";} 					//initialize the string value if any data is read
  
  while(Serial.available() > 0) 	//looping through whenever an information is sent 
  { command = (byte)Serial.read(); 	//read any data sent through the bluetooth socket 
									//and change it to computer language which is the bytes
  
  if(command == ':') {				//Checking if the data sent is not null
    break; 
	} 
    else { 
      string += command;			//if there is something sent, store it in an initialized string variable 
      }
      delay(1);						//delays for very small time to account for any threads 
      } 
      
      //Setting the on buttons and giving it a higher voltage
      if(string == "S1ON")
      { ledOn(ledOne_Two); }
       if(string == "S2ON")
      { ledOn(ledThree_Four);}
       if(string == "S3ON")
      { ledOn(ledFive_Six);}
       if(string == "S4ON")
      { ledOn(ledSeven_Eight);}
       if(string == "S5ON")
      { ledOn(ledNine_Ten);}
       if(string == "S6ON")
      { ledOn(ledEleven_Twelve);
      } 

      //Setting the off buttons and giving it a lower voltage
       if(string == "S1OFF")
      { ledOff(ledOne_Two); }//Calling functions that will control the state of the voltages 
       if(string == "S2OFF")
      { ledOff(ledThree_Four);}
       if(string == "S3OFF")
      { ledOff(ledFive_Six);}
       if(string == "S4OFF")
      { ledOff(ledSeven_Eight);}
       if(string == "S5OFF")
      { ledOff(ledNine_Ten);}
       if(string == "S6OFF")
      { ledOff(ledEleven_Twelve);
      }

      //Setting the analog voltage for the one led pin
      
      if(string.toInt()>=0 && string.toInt()<=255){ //checking if the integer is in the analog range
        if (string.toInt()==0){
          digitalWrite(led_brightness_pin , LOW);
          }
        else{
        analogWrite(led_brightness_pin,string.toInt());
            }
        delay(1);
        }
	//Setting the analog voltage for the fan
		if(string.toInt()>=300 && string.toInt()<=555){ //checking if the integer is in the analog range
          analogWrite(fanPin , string.toInt()-300);
      }  
        delay(1);
        
		
		
} 
//Funtions that will control the state of the Voltages
	void ledOn(int x) {
	digitalWrite(x, HIGH); //high voltage
	delay(10);			 //delay for some time
	} 



	void ledOff(int y) { 
	digitalWrite(y, LOW); //low voltage
	delay(10);			//delay for sometime
	}

