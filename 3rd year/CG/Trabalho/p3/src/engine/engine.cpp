#include "engine.h"

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

void buildRotMatrix(float* x, float* y, float* z, float* m) {

    m[0] = x[0]; m[1] = x[1]; m[2] = x[2]; m[3] = 0;
    m[4] = y[0]; m[5] = y[1]; m[6] = y[2]; m[7] = 0;
    m[8] = z[0]; m[9] = z[1]; m[10] = z[2]; m[11] = 0;
    m[12] = 0; m[13] = 0; m[14] = 0; m[15] = 1;
}


void cross(float* a, float* b, float* res) {

    res[0] = a[1] * b[2] - a[2] * b[1];
    res[1] = a[2] * b[0] - a[0] * b[2];
    res[2] = a[0] * b[1] - a[1] * b[0];
}


void normalize(float* a) {

    float l = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    a[0] = a[0] / l;
    a[1] = a[1] / l;
    a[2] = a[2] / l;
}

void drawGroup(Group g){
  glPushMatrix();
  Color c = g.getTransformation().getColor();
  Translation t = g.getTransformation().getTranslation();
  Rotation r = g.getTransformation().getRotation();
  Scale s = g.getTransformation().getScale();
  if (r.getAngle() == 0 && r.getTime()!=0) {
      float time = (glutGet(GLUT_ELAPSED_TIME)/1000) * 360 / r.getTime();
      glRotatef(time, r.getX(), r.getY(), r.getZ());
  }
  else{
      glRotatef(r.getAngle(), r.getX(), r.getY(), r.getZ());
  }
  if (t.getTime() == 0 && t.getPoints().size() == 0) {
      glTranslatef(t.getX(), t.getY(), t.getZ());
  }
  else{
      float deriv[3];
      float pos[3];
      static float y[3] = { 0,1,0 };
      float time = (glutGet(GLUT_ELAPSED_TIME)/1000) / t.getTime();
      t.renderCatmullRomCurve(c.getR(), c.getG(), c.getB());
      t.getGlobalCatmullRomPoint(time, pos, deriv);
      glTranslatef(pos[0], pos[1], pos[2]);
      float* x, * z, * m;
      z = (float*)malloc(sizeof(float) * 3);
      m = (float*)malloc(sizeof(float) * 16);
      x = deriv;
      cross(x, y, z);
      cross(z, x, y);
      normalize(x);
      normalize(y);
      normalize(z);
      buildRotMatrix(x, y, z, m);
      glMultMatrixf(m);
  }
  glScalef(s.getX(),s.getY(),s.getZ());
  vector<Point> pontos = g.getPoints();
  glColor3f(c.getR(),c.getG(),c.getB());
  g.drawVBO();
  vector<Group> subgroups = g.getSubGroups();
  for(int j=0;((long unsigned int)j)<subgroups.size();j++){
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

    // put the geometric transformations here
    glPolygonMode(GL_FRONT_AND_BACK,type);
    // put drawing instructions here

    //drawAxis();

    drawScene();
    // End of frame
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


vector<Point> readFile (string f){
    string l;
    string sl;
    vector<Point> pontos;
    int i;

    ifstream file("./../models/"+f);

    if (file.is_open()){
        while(getline(file,l)){
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
        }
        file.close();
    }
    else{
        cout << "Erro ao ler o ficheiro " << f << endl;
    }
    return pontos;
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
        if (tx || ty || tz) return t;
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
            }
            t.setPoints(points);
        }
    }
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
    return s;
}

Color parseColor(xml_node<>* color_node){
    Color c = Color();
    if (color_node){
      xml_attribute<>* cr = color_node -> first_attribute("R");
      xml_attribute<>* cg = color_node -> first_attribute("G");
      xml_attribute<>* cb = color_node -> first_attribute("B");
      if (cr) c.setR(atof(cr->value()));
      if (cg) c.setG(atof(cg->value()));
      if (cb) c.setB(atof(cb->value()));
    }
    return c;
}

Group parseGroup(xml_node<>* group_node){
    Group g = Group();
    Transformation t = Transformation();
    vector<Group> subg;
    vector<Point> pontos;
    xml_node<> * translate_node = group_node->first_node("translate");
    t.setTranslation(parseTranslate(translate_node));
    xml_node<> * rotate_node = group_node->first_node("rotate");
    t.setRotation(parseRotate(rotate_node));
    xml_node<> * scale_node = group_node->first_node("scale");
    t.setScale(parseScale(scale_node));
    xml_node<> * color_node = group_node->first_node("color");
    t.setColor(parseColor(color_node));
    g.setTransformation(t);
    for (xml_node<>* model_node = group_node->first_node("model");model_node;model_node=model_node->next_sibling()){
      xml_attribute<>* file = model_node -> first_attribute("file");
      if (file){
        vector<Point> aux = readFile(file->value());
        pontos.insert(pontos.end(),aux.begin(),aux.end());
      }
    }
    g.setPoints(pontos);
    for (xml_node<>* subg_node = group_node->first_node("group");subg_node;subg_node=subg_node->next_sibling()){
      subg.push_back(parseGroup(subg_node));
    }
    g.setSubGroups(subg);
    return g;
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
    for (xml_node<> * group_node = root_node->first_node("group"); group_node; group_node = group_node->next_sibling()){
      groups.push_back(parseGroup(group_node));
    }
    cout << "Ficheiro config.xml lido com sucesso";
}



Group generateGroupVBO(Group g) {
    g.generateVBO();
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
}


int main(int argc, char **argv) {

    lerXML();
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
    generateVBO();

    // enter GLUT's main cycle
    glutMainLoop();

   // return 0;
    return 1;
}

