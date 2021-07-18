#ifndef Translation_H
#define Translation_H

#include "Point.h"
#include <vector>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

using namespace std;

class Translation{
  private:
    float x;
    float y;
    float z;
    float time;
    vector<Point> points;

  public:
    Translation();
    Translation(float x, float y, float z);
    Translation(float time, vector<Point> points);
    float getX() {return x;}
    float getY() {return y;}
    float getZ() {return z;}
    float getTime() { return time; }
    vector<Point> getPoints() { return points; }
    void setX(float x) {this->x=x;}
    void setY(float y) {this->y=y;}
    void setZ(float z) {this->z=z;}
    void setTime(float t) { time = t; }
    void setPoints(vector<Point> p) { points = p; }
    void getCatmullRomPoint(float t, float* p0, float* p1, float* p2, float* p3, float* pos, float* deriv);
    void getGlobalCatmullRomPoint(float gt, float* pos, float* deriv);
    void renderCatmullRomCurve(float r, float g, float b);
};

#endif
