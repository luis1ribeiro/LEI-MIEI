#ifndef GROUP_H
#define GROUP_H

#include <vector>
#include "Point.h"
#include <string>
#include "Models.h"
#include "Translation.h"
#include "Rotation.h"
#include "Scale.h"
#include "Transformation.h"

using namespace std;

class Group{
  private:
    Transformation t;
    vector<Group> subg;
    vector<Models> models;

  public:
    Group ();
    Group (Transformation t, vector<Group> sg, vector<Models> models);
    Transformation getTransformation() {return t;}
    vector<Group> getSubGroups() { return subg; }
    vector<Models> getModels() { return models; }
    void setTransformation(Transformation t) {this->t=t;}
    void setSubGroups(vector<Group> sg){subg=sg;}
    void setModels(vector<Models> m) { models = m; }
    void generateVBOs();
    void drawVBOs();
};

#endif
