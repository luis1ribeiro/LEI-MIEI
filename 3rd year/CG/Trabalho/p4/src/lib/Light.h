#ifndef Light_H
#define Light_H

#include<string>

using namespace std;

class Light {
  private:
    float x;
    float y;
    float z;
    float posx;
    float posy;
    float posz;
    float angle;
    string type;

  public:
    Light();
    Light(float x, float y, float z, float posx, float posy, float posz, float angle, string type);
    float getX() {return x;}
    float getY() {return y;}
    float getZ() {return z;}
    float getPosX() { return posx; }
    float getPosY() { return posy; }
    float getPosZ() { return posz; }
    float getAngle() { return angle; }
    string getType() { return type; }
    void setX(float x) {this->x=x;}
    void setY(float y) {this->y=y;}
    void setZ(float z) {this->z=z;}
    void setPosX(float x) { this->posx = x; }
    void setPosY(float y) { this->posy = y; }
    void setPosZ(float z) { this->posz = z; }
    void setAngle(float a) { angle = a; }
    void setType(string t) { this->type = t; }
};

#endif
