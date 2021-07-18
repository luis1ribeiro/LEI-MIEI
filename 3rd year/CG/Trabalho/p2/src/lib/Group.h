#include <vector>
#include "Transformation.h"
#include "Point.h"
using namespace std;

#ifndef GROUP_H
#define GROUP_H

class Group{
  private:
    Transformation t;
    vector<Group> subg;
    vector<Point> points;

  public:
    Group ();
    Group (Transformation t, vector<Group> sg, vector<Point> p);
    Transformation getTransformation() {return t;}
    vector<Group> getSubGroups() {return subg;}
    vector<Point> getPoints() {return points;}
    void setTransformation(Transformation t) {this->t=t;}
    void setSubGroups(vector<Group> sg){subg=sg;}
    void setPoints(vector<Point> p){points=p;}
};

#endif
