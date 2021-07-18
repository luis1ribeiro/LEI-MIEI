#include "Light.h"

Light::Light() {
	x = 0;
	y = 0;
	z = 0;
	posx = 0;
	posy = 0;
	posz = 0;
	angle = 0;
	type = "";
}

Light::Light(float x, float y, float z, float posx, float posy, float posz, float a, string t) {
	this->x = x;
	this->y = y;
	this->z = z;
	this->posx = posx;
	this->posy = posy;
	this->posz = posz;
	angle = a;
	type = t;
}
