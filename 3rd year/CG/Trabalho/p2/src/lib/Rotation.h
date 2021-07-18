#ifndef ROTATION_H
#define ROTATION_H

class Rotation{
  private:
    float angle;
    float x;
    float y;
    float z;

  public:
    Rotation();
    Rotation(float a, float x, float y, float z);
    float getAngle() {return angle;}
    float getX() {return x;}
    float getY() {return y;}
    float getZ() {return z;}
    void setAngle(float a) {angle=a;}
    void setX(float x) {this->x=x;}
    void setY(float y) {this->y=y;}
    void setZ(float z) {this->z=z;}
};

#endif
