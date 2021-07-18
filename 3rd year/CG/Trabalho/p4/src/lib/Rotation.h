#ifndef ROTATION_H
#define ROTATION_H

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#endif
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

class Rotation{
  private:
    float angle;
    float time;
    float x;
    float y;
    float z;

  public:
    Rotation();
    Rotation(float t, float a, float x, float y, float z);
    float getAngle() {return angle;}
    float getTime() { return time; }
    float getX() {return x;}
    float getY() {return y;}
    float getZ() {return z;}
    void setAngle(float a) {angle=a;}
    void setTime(float t) { time = t; }
    void setX(float x) {this->x=x;}
    void setY(float y) {this->y=y;}
    void setZ(float z) {this->z=z;}
    void transform();
};

#endif
