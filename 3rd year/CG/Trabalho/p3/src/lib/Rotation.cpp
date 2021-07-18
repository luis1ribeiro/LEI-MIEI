#include "Rotation.h"


Rotation::Rotation (){
  angle = 0.0;
  time = 0.0;
  x=0.0;
  y=0.0;
  z=0.0;
}

// Rotation Constructor
Rotation::Rotation(float a, float t, float x, float y, float z){
  this->angle=a;
  this->time = t;
  this->x=x;
  this->y=y;
  this->z=z;
}

