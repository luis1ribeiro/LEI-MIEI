#ifndef Models_H
#define Models_H


#include "Point.h"
#include <string>
#include "MaterialLight.h"

#include <vector>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#endif

using namespace std;

class Models
{
private:
    vector<Point> points;
    vector<Point> normais;
    vector<Point> texturas;
    GLuint vertex;
    GLuint normal;
    GLuint texCoords;
    GLuint texID;
    MaterialLight mtl;
    int n;

public:
    Models();
    vector<Point> getPoints() { return points; }
    vector<Point> getNormais() { return normais; }
    vector<Point> getTexturas() { return texturas; }
    GLuint getTexID() { return texID; }
    MaterialLight getMaterialLight() { return mtl; }
    void setPoints(vector<Point> p) { points = vector<Point>(p); }
    void setNormais(vector<Point> n) { normais = vector<Point>(n); }
    void setTexturas(vector<Point> t) { texturas = vector<Point>(t); }
    void setTexID(GLuint t) { texID = t; }
    void setMaterialLight(MaterialLight m) { mtl=m; }
    void generateVBO();
    void drawVBO();
};

#endif

