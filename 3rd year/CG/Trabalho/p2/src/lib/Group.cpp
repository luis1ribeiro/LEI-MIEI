#include "Group.h"

Group::Group(){
  t= Transformation();
  subg= vector<Group> ();
  points= vector<Point> ();
}

Group::Group(Transformation t, vector<Group> sg, vector<Point> p){
  this->t=t;
  subg=sg;
  points=p;
}
