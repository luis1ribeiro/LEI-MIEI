#include "Translation.h"

Translation::Translation(){
  x=0.0;
  y=0.0;
  z=0.0;
  time = 0;
  points = vector<Point>(0);
}

// Translation Constructor
Translation::Translation(float x, float y, float z){
  this->x=x;
  this->y=y;
  this->z=z;
  time = -1;
}

Translation::Translation(float t, vector<Point> p) {
    time = t;
    points = p;
}


void Translation::getCatmullRomPoint(float t, float* p0, float* p1, float* p2, float* p3, float* pos, float* deriv) {

    // catmull-rom matrix
    float m[4][4] = { {-0.5f,  1.5f, -1.5f,  0.5f},
                        { 1.0f, -2.5f,  2.0f, -0.5f},
                        {-0.5f,  0.0f,  0.5f,  0.0f},
                        { 0.0f,  1.0f,  0.0f,  0.0f} };


    // Compute A = M * P
    float a[4][3];
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 3; j++) {
            a[i][j] = m[i][0] * p0[j] + m[i][1] * p1[j] + m[i][2] * p2[j] + m[i][3] * p3[j];
        }
    }

    // Compute pos = T * A
    float mt[4] = { pow(t,3),pow(t,2),t,1 };
    for (int i = 0; i < 3; i++) {
        pos[i] = 0;
        for (int j = 0; j < 4; j++) {
            pos[i] += mt[j] * a[j][i];
        }
    }

    // Compute deriv = T' * A
    float d[4] = { 3 * pow(t,2),2 * t,1,0 };
    for (int i = 0; i < 3; i++) {
        deriv[i] = 0;
        for (int j = 0; j < 4; j++) {
            deriv[i] += d[j] * a[j][i];
        }
    }
}


void Translation::getGlobalCatmullRomPoint(float gt, float* pos, float* deriv) {

    int size = points.size();
    float t = gt * points.size(); // this is the real global t
    int index = floor(t);  // which segment
    t = t - index; // where within  the segment

    // indices store the points
    int indices[4];
    indices[0] = (index + size -1) % size;
    indices[1] = (indices[0] + 1) % size;
    indices[2] = (indices[1] + 1) % size;
    indices[3] = (indices[2] + 1) % size;

    float p0[3] = { points[indices[0]].getX(), points[indices[0]].getY(), points[indices[0]].getZ() };
    float p1[3] = { points[indices[1]].getX(), points[indices[1]].getY(), points[indices[1]].getZ() };
    float p2[3] = { points[indices[2]].getX(), points[indices[2]].getY(), points[indices[2]].getZ() };
    float p3[3] = { points[indices[3]].getX(), points[indices[3]].getY(), points[indices[3]].getZ() };

    getCatmullRomPoint(t, p0, p1, p2, p3, pos, deriv);
}

void Translation::renderCatmullRomCurve(float r, float g, float b) {

    // draw curve using line segments with GL_LINE_LOOP
    glColor3f(r, g, b);
    glBegin(GL_LINE_LOOP);

    for (int i = 0; i < 100; i++) {
        float pos[3];
        float deriv[3];
        getGlobalCatmullRomPoint(i / 100.f, pos, deriv);
        glVertex3f(pos[0], pos[1], pos[2]);
    }
    glEnd();
}

void cross(float* a, float* b, float* res) {

    res[0] = a[1] * b[2] - a[2] * b[1];
    res[1] = a[2] * b[0] - a[0] * b[2];
    res[2] = a[0] * b[1] - a[1] * b[0];
}


void normalize(float* a) {

    float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0] / l;
    a[1] = a[1] / l;
    a[2] = a[2] / l;
}

void buildRotMatrix(float* x, float* y, float* z, float* m) {

    m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
    m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
    m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
    m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}


void Translation::transform() {
    if (time == 0 && points.size() == 0) {
        glTranslatef(x, y, z);
    }
    else {
        float deriv[3];
        float pos[3];
        static float y[3] = { 0,1,0 };
        float t = (glutGet(GLUT_ELAPSED_TIME) / 1000) / time;
        //renderCatmullRomCurve(1.0f, 1.0f, 1.0f);
        getGlobalCatmullRomPoint(t, pos, deriv);
        glTranslatef(pos[0], pos[1], pos[2]);
        float* x, * z, * m;
        z = (float*)malloc(sizeof(float) * 3);
        m = (float*)malloc(sizeof(float) * 16);
        x = deriv;
        //cout << y[0] << "," << y[1] << "," << y[2] << endl;
        cross(x, y, z);
        cross(z, x, y);
        normalize(x);
        normalize(y);
        normalize(z);
        buildRotMatrix(x, y, z, m);
        glMultMatrixf(m);
    }
}
