#define _USE_MATH_DEFINES
#include <cmath>
#include "./../rapidxml/rapidxml.hpp"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <fstream>
#include <string>
#include <vector>
#include <sys/types.h>
#include <string.h>
#include "./../lib/Point.h"
#include "./../lib/Translation.h"
#include "./../lib/Rotation.h"
#include "./../lib/Scale.h"
#include "./../lib/Color.h"
#include "./../lib/Transformation.h"
#include "./../lib/Group.h"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif


using namespace std;
using namespace rapidxml;

vector<Group> groups;

int type = GL_FILL;
float alfa = M_PI/4, bet = M_PI/4, radius = 200.0f;
float camX, camY, camZ;
