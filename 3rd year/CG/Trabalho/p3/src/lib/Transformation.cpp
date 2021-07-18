#include "Transformation.h"

Transformation::Transformation(){
  t=Translation();
  r=Rotation();
  s=Scale();
  c=Color();
}

Transformation::Transformation(Translation t, Rotation r, Scale s, Color c){
  this->t=t;
  this->r=r;
  this->s=s;
  this->c=c;
}
