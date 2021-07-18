#ifndef TRANSFORMATION_H
#define TRANSFORMATION_H

#include <string>
#include "Rotation.h"
#include "Translation.h"
#include "Scale.h"


class Transformation{
private:
	Rotation r;
	Translation t;
	Scale s;
	vector<string> order;

public:
	Transformation();
	Transformation(Rotation r, Translation t, Scale s, vector<string> o);
	Rotation getRotation() { return r;}
	Translation getTranslation() { return t; }
	Scale getScale() { return s; }
	vector<string> getOrder(){ return order; }
	void setRotation(Rotation rot) { r = rot; }
	void setTranslation(Translation tra) { t = tra; }
	void setScale(Scale sca) { s = sca; }
	void setOrder(vector<string> o) { order = o; }
};

#endif

