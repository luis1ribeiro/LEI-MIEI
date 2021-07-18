#include "engine.h"

int timebase = 0;
int frame = 0;
char s[30];

void spherical2Cartesian() {

    camX = radius * cos(bet) * sin(alfa);
    camY = radius * sin(bet);
    camZ = radius * cos(bet) * cos(alfa);
}


void changeSize(int w, int h) {

    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if(h == 0)
        h = 1;

    // compute window's aspect ratio
    float ratio = w * 1.0 / h;

    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load Identity Matrix
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set perspective
    gluPerspective(45.0f ,ratio, 1.0f ,1000.0f);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void drawAxis(){
    glBegin(GL_LINES);
    // X - red
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f( 100.0f, 0.0f, 0.0f);
    // Y - green
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 100.0f, 0.0f);
    // Z - blue
    glColor3f(0.0f, 0.0f, 1.0f);
    glVertex3f(0.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 0.0f,  100.0f);
    glEnd();
}




void drawGroup(Group g) {
    glPushMatrix();
    Transformation t = g.getTransformation();
    vector<string> order = t.getOrder();
    for (int i = 0; i < order.size(); i++) {
        if (order[i] == "translation")
            t.getTranslation().transform();
        if (order[i]=="rotation")
            t.getRotation().transform();
        if (order[i]=="scale")
            t.getScale().transform();
        //cout << order[i] << endl;
    }
    g.drawVBOs();
    vector<Group> subgroups = g.getSubGroups();
    for (int j = 0; ((long unsigned int)j) < subgroups.size(); j++) {
        Group sg = subgroups[j];
        drawGroup(sg);
    }
    glPopMatrix();
}


void drawScene(){
  int i;
  for(i=0;((long unsigned int)i)<groups.size();i++){
    drawGroup(groups[i]);
  }
}


void renderScene(void) {

    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    spherical2Cartesian();
    gluLookAt(camX,camY,camZ,
            0,0,0,
            0.0f,1.0f,0.0f);
    

    for (int i = 0; i < luzes.size(); i++) {
        Light l = luzes[i];
        if (l.getType() == "POINT") {
            float pos[4];
            pos[0] = l.getPosX();
            pos[1] = l.getPosY();
            pos[2] = l.getPosZ();
            pos[3] = 1.0f;
            switch (i){
                case 0:
                    glLightfv(GL_LIGHT0, GL_POSITION, pos);
                    break;
                case 1:
                    glLightfv(GL_LIGHT1, GL_POSITION, pos);
                    break;
                case 2:
                    glLightfv(GL_LIGHT2, GL_POSITION, pos);
                    break;
                case 3:
                    glLightfv(GL_LIGHT3, GL_POSITION, pos);
                    break;
                case 4:
                    glLightfv(GL_LIGHT4, GL_POSITION, pos);
                    break;
                case 5:
                    glLightfv(GL_LIGHT5, GL_POSITION, pos);
                    break;
                case 6:
                    glLightfv(GL_LIGHT6, GL_POSITION, pos);
                    break;
                case 7:
                    glLightfv(GL_LIGHT7, GL_POSITION, pos);
                    break;
            }
            //glLightfv(GL_LIGHT0, GL_POSITION, pos);
            //cout << "POINT LIGHT TURNED ON" << " " << pos[0] << " " << pos[1] << " " << pos[2] << " " << pos[3] << endl;
        }
        if (l.getType() == "DIRECTIONAL") {
            float dir[4];
            dir[0] = l.getX();
            dir[1] = l.getY();
            dir[2] = l.getZ();
            dir[3] = 0.0f;
            switch (i) {
                case 0:
                    glLightfv(GL_LIGHT0, GL_POSITION, dir);
                    break;
                case 1:
                    glLightfv(GL_LIGHT1, GL_POSITION, dir);
                    break;
                case 2:
                    glLightfv(GL_LIGHT2, GL_POSITION, dir);
                    break;
                case 3:
                    glLightfv(GL_LIGHT3, GL_POSITION, dir);
                    break;
                case 4:
                    glLightfv(GL_LIGHT4, GL_POSITION, dir);
                    break;
                case 5:
                    glLightfv(GL_LIGHT5, GL_POSITION, dir);
                    break;
                case 6:
                    glLightfv(GL_LIGHT6, GL_POSITION, dir);
                    break;
                case 7:
                    glLightfv(GL_LIGHT7, GL_POSITION, dir);
                    break;
            }
            //glLightfv(GL_LIGHT0, GL_POSITION, dir);
            //cout << "DIRECTIONAL LIGHT TURNED ON " << endl;
        }
        if (l.getType() == "SPOT") {
            float pos[4];
            pos[0] = l.getPosX();
            pos[1] = l.getPosY();
            pos[2] = l.getPosZ();
            pos[3] = 1.0f;
            float dir[3];
            dir[0] = l.getX();
            dir[1] = l.getY();
            dir[2] = l.getZ();
            float angle = l.getAngle();
            if ((angle >= 0 && angle <= 90) || angle == 180) {
                switch (i) {
                    case 0:
                        glLightfv(GL_LIGHT0, GL_POSITION, pos);
                        glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, angle);
                        break;
                    case 1:
                        glLightfv(GL_LIGHT1, GL_POSITION, pos);
                        glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, angle);
                        break;
                    case 2:
                        glLightfv(GL_LIGHT2, GL_POSITION, pos);
                        glLightfv(GL_LIGHT2, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT2, GL_SPOT_CUTOFF, angle);
                        break;
                    case 3:
                        glLightfv(GL_LIGHT3, GL_POSITION, pos);
                        glLightfv(GL_LIGHT3, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT3, GL_SPOT_CUTOFF, angle);
                        break;
                    case 4:
                        glLightfv(GL_LIGHT4, GL_POSITION, pos);
                        glLightfv(GL_LIGHT4, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT4, GL_SPOT_CUTOFF, angle);
                        break;
                    case 5:
                        glLightfv(GL_LIGHT5, GL_POSITION, pos);
                        glLightfv(GL_LIGHT5, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT5, GL_SPOT_CUTOFF, angle);
                        break;
                    case 6:
                        glLightfv(GL_LIGHT6, GL_POSITION, pos);
                        glLightfv(GL_LIGHT6, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT6, GL_SPOT_CUTOFF, angle);
                        break;
                    case 7:
                        glLightfv(GL_LIGHT7, GL_POSITION, pos);
                        glLightfv(GL_LIGHT7, GL_SPOT_DIRECTION, dir);
                        glLightf(GL_LIGHT7, GL_SPOT_CUTOFF, angle);
                        break;
                }           
            }
            //cout << "SPOT LIGHT TURNED ON" << endl;
        }
    }


    glPolygonMode(GL_FRONT, type);
    // put drawing instructions here

    //drawAxis();

    drawScene();
    // End of frame

    int timet;
    frame++;
    timet = glutGet(GLUT_ELAPSED_TIME);
    if (timet - timebase > 1000) {
        sprintf(s, "FPS:%4.2f", frame * 1000.0 / (timet - timebase));
        timebase = timet;
        frame = 0;
        glutSetWindowTitle(s);
    }

    glutSwapBuffers();
}

void processKeys(unsigned char c, int xx, int yy) {
    switch(c){
        case '1':
            type = GL_LINE;
            break;
        case '2':
            type = GL_POINT;
            break;
        case '3':
            type = GL_FILL;
            break;
        case '-':
            radius+=1.5f;
            break;
        case '+':
            radius-=1.5f;
            if (radius < 1.5f)
                radius = 1.5f;
            break;
        case 'r':
        case 'R':
            radius = 200.0f;
            alfa = M_PI/4;
            bet = M_PI/4;
            type=GL_LINE;
            break;
    }

    glutPostRedisplay();
}

void processSpecialKeys(int key, int xx, int yy) {

    switch (key) {

        case GLUT_KEY_RIGHT:
            alfa -= 0.1; break;

        case GLUT_KEY_LEFT:
            alfa += 0.1; break;

        case GLUT_KEY_UP:
            bet += 0.1f;
            if (bet > 1.5f)
                bet = 1.5f;
            break;

        case GLUT_KEY_DOWN:
            bet -= 0.1f;
            if (bet < -1.5f)
                bet = -1.5f;
            break;
    }
    glutPostRedisplay();
}


vector<vector<Point>> readFile (string f){
    string l;
    string sl;
    vector<Point> pontos;
    vector<Point> normais;
    vector<Point> texturas;
    vector<vector<Point>> result;
    int i;

    ifstream file("./../models/"+f);

    if (file.is_open()){
        while(getline(file,l)){
            // vertice
            Point p = Point();
            i=l.find(",");
            sl = l.substr(0,i);
            p.setX(atof(sl.c_str()));
            l.erase(0,i+1);

            i=l.find(",");
            sl=l.substr(0,i);
            p.setY(atof(sl.c_str()));
            l.erase(0,i+1);

            i=l.find(",");
            sl=l.substr(0,i);
            p.setZ(atof(sl.c_str()));
            l.erase(0,i+1);

            pontos.push_back(p);

            // normal do vertice
            getline(file, l);

            i = l.find(",");
            sl = l.substr(0, i);
            p.setX(atof(sl.c_str()));
            l.erase(0, i + 1);

            i = l.find(",");
            sl = l.substr(0, i);
            p.setY(atof(sl.c_str()));
            l.erase(0, i + 1);

            i = l.find(",");
            sl = l.substr(0, i);
            p.setZ(atof(sl.c_str()));
            l.erase(0, i + 1);

            normais.push_back(p);

            // texturas do vertice
            getline(file, l);

            i = l.find(",");
            sl = l.substr(0, i);
            p.setX(atof(sl.c_str()));
            l.erase(0, i + 1);

            i = l.find(",");
            sl = l.substr(0, i);
            p.setY(atof(sl.c_str()));
            l.erase(0, i + 1);

            p.setZ(0);

            texturas.push_back(p);
        }
        file.close();
        result.push_back(pontos);
        result.push_back(normais);
        result.push_back(texturas);
    }
    else{
        cout << "Erro ao ler o ficheiro " << f << endl;
    }
    return result;
}


int loadTexture(std::string s) {

    unsigned int t, tw, th;
    unsigned char* texData;
    unsigned int texID;

    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
    ilGenImages(1, &t);
    ilBindImage(t);
    ilLoadImage((ILstring)s.c_str());
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    glGenTextures(1, &texID);

    glBindTexture(GL_TEXTURE_2D, texID);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, tw, th, 0, GL_RGBA, GL_UNSIGNED_BYTE, texData);
    glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);

    return texID;

}


void menu (){
    cout << endl;
    cout << "#########################" << endl;
    cout << "Menu Engine" << endl;
    cout << "1: Change draw mode to lines" << endl;
    cout << "2: Change draw mode to points" << endl;
    cout << "3: Change draw mode to fill" << endl;
    cout << "+: Zoom the camera" << endl;
    cout << "-: Unzoom the camera" << endl;
    cout << "r: Restart the camera settings" << endl;
    cout << "Press arrow keys to move the camera" << endl;
    cout << "#########################" << endl;
}



Translation parseTranslate(xml_node<>* translate_node){
    Translation t = Translation();
    if (translate_node) {
        xml_attribute<>* tx = translate_node->first_attribute("X");
        xml_attribute<>* ty = translate_node->first_attribute("Y");
        xml_attribute<>* tz = translate_node->first_attribute("Z");
        if (tx) t.setX(atof(tx->value()));
        if (ty) t.setY(atof(ty->value()));
        if (tz) t.setZ(atof(tz->value()));
        if (tx || ty || tz) {
            //cout << t.getX() << "," << t.getY() << "," << t.getZ() << endl; return t;
        }
        xml_attribute<>* tt = translate_node->first_attribute("time");
        if (tt) {
            t.setTime(atof(tt->value()));
            vector<Point> points;
            for (xml_node<>* point_node = translate_node->first_node("point"); point_node; point_node = point_node->next_sibling()) {
                Point p = Point();
                xml_attribute<>* tx = point_node->first_attribute("X");
                xml_attribute<>* ty = point_node->first_attribute("Y");
                xml_attribute<>* tz = point_node->first_attribute("Z");
                if (tx) p.setX(atof(tx->value()));
                if (ty) p.setY(atof(ty->value()));
                if (tz) p.setZ(atof(tz->value()));
                points.push_back(p);
               // cout << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
            }
            t.setPoints(points);
        }
    }
   // cout << t.getTime() << " TIME " << endl; return t;
    return t;
}



Rotation parseRotate(xml_node<>* rotate_node){
    Rotation r = Rotation();
    if (rotate_node){
      xml_attribute<>* ra = rotate_node -> first_attribute("angle");
      xml_attribute<>* rt = rotate_node -> first_attribute("time");
      xml_attribute<>* rx = rotate_node -> first_attribute("axisX");
      xml_attribute<>* ry = rotate_node -> first_attribute("axisY");
      xml_attribute<>* rz = rotate_node -> first_attribute("axisZ");
      if (ra) r.setAngle(atof(ra->value()));
      if (rt) r.setTime(atof(rt->value()));
      if (rx) r.setX(atof(rx->value()));
      if (ry) r.setY(atof(ry->value()));
      if (rz) r.setZ(atof(rz->value()));
    }
   // cout << r.getAngle() << "," << r.getX() << "," << r.getY() << "," << r.getZ() << endl;
    return r;
}



Scale parseScale(xml_node<>* scale_node){
    Scale s = Scale();
    if (scale_node){
      xml_attribute<>* sx = scale_node -> first_attribute("X");
      xml_attribute<>* sy = scale_node -> first_attribute("Y");
      xml_attribute<>* sz = scale_node -> first_attribute("Z");
      if (sx) s.setX(atof(sx->value()));
      if (sy) s.setY(atof(sy->value()));
      if (sz) s.setZ(atof(sz->value()));
    }
    //cout << s.getX() << "," << s.getY() << "," << s.getZ() << endl;
    return s;
}



vector<Models> parseModels(xml_node<>* models_node) {
    vector<Models> models;
    vector<Point> pontos;
    vector<Point> normais;
    vector<Point> texturas;
    for (xml_node<>* model_node = models_node->first_node("model"); model_node; model_node = model_node->next_sibling()) {
        Models model = Models();
        xml_attribute<>* file = model_node->first_attribute("file");
        if (file) {
            vector<vector<Point>> inf = readFile(file->value());
            vector<Point> auxp = inf[0];
            vector<Point> auxn = inf[1];
            vector<Point> auxt = inf[2];
            pontos.insert(pontos.end(), auxp.begin(), auxp.end());
            normais.insert(normais.end(), auxn.begin(), auxn.end());
            texturas.insert(texturas.end(), auxt.begin(), auxt.end());
        }
        xml_attribute<>* text = model_node->first_attribute("texture");
        if (text) {
            string path = "./../textures/" + (string)text->value();
            model.setTexID(loadTexture(path));
        }

        MaterialLight mtl = MaterialLight();
        xml_attribute<>* diffR = model_node->first_attribute("diffR");
        xml_attribute<>* diffG = model_node->first_attribute("diffG");
        xml_attribute<>* diffB = model_node->first_attribute("diffB");
        xml_attribute<>* ambiR = model_node->first_attribute("ambiR");
        xml_attribute<>* ambiG = model_node->first_attribute("ambiG");
        xml_attribute<>* ambiB = model_node->first_attribute("ambiB");
        xml_attribute<>* specR = model_node->first_attribute("specR");
        xml_attribute<>* specG = model_node->first_attribute("specG");
        xml_attribute<>* specB = model_node->first_attribute("specB");
        xml_attribute<>* emisR = model_node->first_attribute("emisR");
        xml_attribute<>* emisG = model_node->first_attribute("emisG");
        xml_attribute<>* emisB = model_node->first_attribute("emisB");
        xml_attribute<>* shin = model_node->first_attribute("shin");
        if (diffR && diffG && diffB) {
            mtl.setDifR(atof(diffR->value()));
            mtl.setDifG(atof(diffG->value()));
            mtl.setDifB(atof(diffB->value()));
        }
        if (ambiR && ambiG && ambiB) {
            mtl.setAmbR(atof(ambiR->value()));
            mtl.setAmbG(atof(ambiG->value()));
            mtl.setAmbB(atof(ambiB->value()));
        }
        if (specR && specG && specB) {
            mtl.setSpeR(atof(specR->value()));
            mtl.setSpeG(atof(specG->value()));
            mtl.setSpeB(atof(specB->value()));
        }
        if (emisR && emisG && emisB) {
            mtl.setEmiR(atof(emisR->value()));
            mtl.setEmiG(atof(emisG->value()));
            mtl.setEmiB(atof(emisB->value()));
        }
        if (shin){
            mtl.setShi(atof(shin->value()));
        }

        model.setMaterialLight(mtl);
        model.setPoints(pontos);
        model.setNormais(normais);
        model.setTexturas(texturas);
        models.push_back(model);
    }
    return models;
}


Group parseGroup(xml_node<>* group_node) {
    Group g = Group();
    vector<string> order;
    Transformation t;
    vector<Group> subg;
    bool r=false, s=false, trans=false, m=false;
    //cout << "Parsing Group" << endl;
    for (xml_node<>* node = group_node->first_node(); node; node = node->next_sibling()) {
        //cout << node->name() << endl;
        if ((string)node->name() == "translate") {
            if (!trans) {
                t.setTranslation(parseTranslate(node));
                order.push_back("translation");
                trans = true;
            }
            else {
                cout << "Translation already defined" << endl;
            }
        }
        if ((string)node->name() == "rotate") {
            if (!r) {
                t.setRotation(parseRotate(node));
                order.push_back("rotation");
                r = true;
            }
            else {
                cout << "Rotation already defined" << endl;
            }
        }
        if ((string)node->name() == "scale") {
            if (!s) {
                t.setScale(parseScale(node));
                order.push_back("scale");
                s = true;
            }
            else {
                cout << "Scale already defined" << endl;
            }
        }
        if ((string)node->name() == "models") {
            if (!m) {
                g.setModels(parseModels(node));
                m = true;
            }
            else {
                cout << "Models already defined" << endl;
            }
        }
        if (trans && r && s && m) break;
    }
    t.setOrder(order);
    g.setTransformation(t);
    for (xml_node<>* subg_node = group_node->first_node("group"); subg_node; subg_node = subg_node->next_sibling()) {
        subg.push_back(parseGroup(subg_node));
    }
    g.setSubGroups(subg);
    return g;
}

void createLight(Light l) {
    int n = luzes.size();
    if (n > 7) return;
    GLfloat dark[4] = { 0.2, 0.2, 0.2, 1.0 };
    GLfloat white[4] = { 1.0, 1.0, 1.0, 1.0 };
    switch (n) {
        case 0:
            glEnable(GL_LIGHT0);
            // light colors
            glLightfv(GL_LIGHT0, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT0, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT0, GL_SPECULAR, white);
            break;
        case 1:
            glEnable(GL_LIGHT1);
            // light colors
            glLightfv(GL_LIGHT1, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT1, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT1, GL_SPECULAR, white);
            break;
        case 2:
            glEnable(GL_LIGHT2);
            // light colors
            glLightfv(GL_LIGHT2, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT2, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT2, GL_SPECULAR, white);
            break;
        case 3:
            glEnable(GL_LIGHT3);
            // light colors
            glLightfv(GL_LIGHT3, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT3, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT3, GL_SPECULAR, white);
            break;
        case 4:
            glEnable(GL_LIGHT4);
            // light colors
            glLightfv(GL_LIGHT4, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT4, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT4, GL_SPECULAR, white);
            break;
        case 5:
            glEnable(GL_LIGHT5);
            // light colors
            glLightfv(GL_LIGHT5, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT5, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT5, GL_SPECULAR, white);
            break;
        case 6:
            glEnable(GL_LIGHT6);
            // light colors
            glLightfv(GL_LIGHT6, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT6, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT6, GL_SPECULAR, white);
            break;
        case 7:
            glEnable(GL_LIGHT7);
            // light colors
            glLightfv(GL_LIGHT7, GL_AMBIENT, dark);
            glLightfv(GL_LIGHT7, GL_DIFFUSE, white);
            glLightfv(GL_LIGHT7, GL_SPECULAR, white);
            break;
    }
    luzes.push_back(l);
}


void parseLights(xml_node<>* lights_node){
    for (xml_node<>* light_node = lights_node->first_node("light"); light_node; light_node = light_node->next_sibling()) {
        Light l = Light();
        xml_attribute<>* x = light_node->first_attribute("X");
        xml_attribute<>* y = light_node->first_attribute("Y");
        xml_attribute<>* z = light_node->first_attribute("Z");
        xml_attribute<>* posx = light_node->first_attribute("posX");
        xml_attribute<>* posy = light_node->first_attribute("posY");
        xml_attribute<>* posz = light_node->first_attribute("posZ");
        xml_attribute<>* type = light_node->first_attribute("type");
        xml_attribute<>* angle = light_node->first_attribute("angle");
        if (x) l.setX(atof(x->value()));
        if (y) l.setY(atof(y->value()));
        if (z) l.setZ(atof(z->value()));
        if (posx) l.setPosX(atof(posx->value()));
        if (posy) l.setPosY(atof(posy->value()));
        if (posz) l.setPosZ(atof(posz->value()));
        if (angle) l.setAngle(atof(angle->value()));
        if (type) l.setType(type->value());
        else {
            cout << "Nenhum tipo definido para a luz" << endl;
            return;
        }
        createLight(l);
    }
}


void lerXML (){
    xml_document<> doc;
    xml_node<> *root_node;
    string fich;
    ifstream file ("./../config.xml");
    vector<char> buffer((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
    buffer.push_back('\0');
    doc.parse<0>(&buffer[0]);
    root_node = doc.first_node("scene");
    xml_node<>* lights_node = root_node->first_node("lights");
    if (lights_node) parseLights(lights_node);
    for (xml_node<> * group_node = root_node->first_node("group"); group_node; group_node = group_node->next_sibling()){
      groups.push_back(parseGroup(group_node));
    }
    cout << "Ficheiro config.xml lido com sucesso" << endl;
    for (int i = 0; i < luzes.size(); i++) {
        Light l = luzes[i];
        if (l.getType() == "DIRECTIONAL") cout << l.getType() << " " << l.getX() << " " << l.getY() << " " << l.getZ() << endl;
        if (l.getType() == "POINT") cout << l.getType() << " " << l.getPosX() << " " << l.getPosY() << " " << l.getPosZ() << endl;
        if (l.getType() == "SPOT") cout << l.getType() << " " << l.getX() << " " << l.getY() << " " << l.getZ() << " POINT: " << l.getPosX() << " " << l.getPosY() << " " << l.getPosZ() << " A: " << l.getAngle() << endl ;
    }
}



Group generateGroupVBO(Group g) {
    g.generateVBOs();
    vector<Group> sg = g.getSubGroups();
    for (int i = 0; i < sg.size(); i++) {
        sg[i]=generateGroupVBO(sg[i]);
    }
    g.setSubGroups(sg);
    return g;
}


void generateVBO() {
    for (size_t i = 0; i < groups.size(); i++) {
        groups[i]=generateGroupVBO(groups[i]);
    }
    cout << "All vbos generated" << endl;
}


int main(int argc, char **argv) {

    menu();

    // init GLUT and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
    glutInitWindowPosition(100,100);
    glutInitWindowSize(800,800);
    glutCreateWindow("Grupo38@CG");

    // Required callback registry
    glutDisplayFunc(renderScene);
    glutIdleFunc(renderScene);
    glutReshapeFunc(changeSize);


    // put here the registration of the keyboard callbacks
    glutSpecialFunc(processSpecialKeys);
    glutKeyboardFunc(processKeys);

    //  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glewInit();
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_LIGHTING);

    lerXML();
    generateVBO();

    // enter GLUT's main cycle
    glutMainLoop();

   // return 0;
    return 1;
}

