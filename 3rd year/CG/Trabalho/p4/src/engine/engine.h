#include <stdlib.h>
#define _USE_MATH_DEFINES
#include "./../rapidxml/rapidxml.hpp"
#include "./../lib/Light.h"
#include <iostream>
#include <math.h>
#include <stdio.h>
#include <fstream>
#include <string>
#include <vector>
//#include <dirent.h>
#include <sys/types.h>
#include <string.h>
#include <IL/il.h>
#include "./../lib/Group.h"

using namespace std;
using namespace rapidxml;

vector<Group> groups;
vector<Light> luzes;

int type = GL_FILL;
float alfa = M_PI/4, bet = M_PI/4, radius = 200.0f;
float camX, camY, camZ;

