#ifndef Scale_H
#define Scale_H

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

class Scale{
  private:
    float x;
    float y;
    float z;

  public:
    Scale();
    Scale(float x, float y, float z);
    float getX() {return x;}
    float getY() {return y;}
    float getZ() {return z;}
    void setX(float x) {this->x=x;}
    void setY(float y) {this->y=y;}
    void setZ(float z) {this->z=z;}
    void transform();
};

#endif
