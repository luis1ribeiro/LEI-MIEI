#include "Transformation.h"

Transformation::Transformation() {
	r = Rotation();
	t = Translation();
	s = Scale();
	order = vector<string>();
}

Transformation::Transformation(Rotation rot, Translation tra, Scale sca, vector<string> o) {
	r = rot;
	t = tra;
	s = sca;
	order = vector<string>(o);
}