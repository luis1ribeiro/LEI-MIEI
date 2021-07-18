#ifndef Color_H
#define Color_H

class Color{
  private:
    float r;
    float g;
    float b;

  public:
    Color();
    Color(float r, float g, float b);
    float getR() {return r;}
    float getG() {return g;}
    float getB() {return b;}
    void setR(float r) {this->r=r;}
    void setG(float g) {this->g=g;}
    void setB(float b) {this->b=b;}
};

#endif
