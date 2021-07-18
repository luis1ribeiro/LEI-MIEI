#include "Group.h"

Group::Group(){
  t= Transformation();
  subg= vector<Group> ();
  models = vector<Models>();
}

Group::Group(Transformation t, vector<Group> sg, vector<Models> models) {
    this->t = t;
    subg = sg;
    this->models = models;
}

void Group::generateVBOs() {
    for (int i = 0; i < models.size(); i++) {
        models[i].generateVBO();
    }
}

void Group::drawVBOs() {
    for (int i = 0; i < models.size(); i++) {
        models[i].drawVBO();
    }
}