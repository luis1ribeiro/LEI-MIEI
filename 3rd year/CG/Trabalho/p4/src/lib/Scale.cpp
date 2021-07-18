#include "Scale.h"


Scale::Scale(){
  x=1.0;
  y=1.0;
  z=1.0;
}

// Scale Constructor
Scale::Scale(float x, float y, float z){
  this->x=x;
  this->y=y;
  this->z=z;
}

void Scale::transform() {
    glScalef(x,y,z);
}

