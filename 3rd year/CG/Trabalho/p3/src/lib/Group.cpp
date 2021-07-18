#include "Group.h"

Group::Group(){
  t= Transformation();
  subg= vector<Group> ();
  points= vector<Point> ();
  n = 0;
}

Group::Group(Transformation t, vector<Group> sg, vector<Point> p, int n){
  this->t=t;
  subg=sg;
  points=p;
  this->n = n;
}

void Group::generateVBO() {
    int i;
    n = 0;
    glEnableClientState(GL_VERTEX_ARRAY);

    float* v = (float*)malloc(sizeof(float) * points.size() * 3);

    for (i = 0; i < points.size(); i++) {
        v[n++] = points[i].getX();
        v[n++] = points[i].getY();
        v[n++] = points[i].getZ();
    }

    glGenBuffers(1, buffer);
    glBindBuffer(GL_ARRAY_BUFFER, buffer[0]);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * points.size() * 3, v, GL_STATIC_DRAW);
    free(v);
}

void Group::drawVBO() {
    glBindBuffer(GL_ARRAY_BUFFER, buffer[0]);
    glVertexPointer(3, GL_FLOAT, 0, 0);
    glDrawArrays(GL_TRIANGLES, 0, n);
}