#include "Translation.h"
#include "Rotation.h"
#include "Scale.h"
#include "Color.h"

#ifndef TRANSFORMATION_H
#define TRANSFORMATION_H

class Transformation{
  private:
    Translation t;
    Rotation r;
    Scale s;
    Color c;

  public:
    Transformation();
    Transformation(Translation t, Rotation r, Scale s, Color c);
    Translation getTranslation() {return t;}
    Rotation getRotation() {return r;}
    Scale getScale() {return s;}
    Color getColor() {return c;}
    void setTranslation(Translation t) {this->t=t;}
    void setRotation(Rotation r) {this->r=r;}
    void setScale(Scale s) {this->s=s;}
    void setColor(Color c) {this->c=c;}
};

#endif

