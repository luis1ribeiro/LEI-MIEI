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


void drawScene(){
    int i=0;
    while(((long unsigned int)i)<pontos.size()){
        glColor3f(0.439216f,0.858824f,0.858824f);
        glVertex3f(pontos[i].x,pontos[i].y,pontos[i].z);
        glVertex3f(pontos[i+1].x,pontos[i+1].y,pontos[i+1].z);
        glVertex3f(pontos[i+2].x,pontos[i+2].y,pontos[i+2].z);
        glColor3f(0.35f,0.35f,0.67f);
        glVertex3f(pontos[i+3].x,pontos[i+3].y,pontos[i+3].z);
        glVertex3f(pontos[i+4].x,pontos[i+4].y,pontos[i+4].z);
        glVertex3f(pontos[i+5].x,pontos[i+5].y,pontos[i+5].z);
        i+=6;
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

    drawAxis();
    glBegin(GL_TRIANGLES);
    drawScene();
    glEnd();
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
            radius+=0.1f;
            break;
        case '+':
            radius-=0.1f;
            if (radius < 0.1f)
                radius = 0.1f;
            break;
        case 'r':
        case 'R':
            radius = 10.0f;
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


void readFile (string f){
    string l;
    string sl;
    Ponto p;
    int i;

    ifstream file(f);

    if (file.is_open()){
        while(getline(file,l)){
            i=l.find(",");
            sl = l.substr(0,i);
            p.x=atof(sl.c_str());
            l.erase(0,i+1);

            i=l.find(",");
            sl=l.substr(0,i);
            p.y=atof(sl.c_str());
            l.erase(0,i+1);

            i=l.find(",");
            sl=l.substr(0,i);
            p.z=atof(sl.c_str());
            l.erase(0,i+1);

            pontos.push_back(p);
        }
        file.close();
    }
    else{
        cout << "Erro ao ler o ficheiro " << f << endl;
    }
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


void lerXML (){
    xml_document<> doc;
    xml_node<> *root_node;
    string fich;
    ifstream file ("./../config.xml");
    vector<char> buffer((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
    buffer.push_back('\0');
    doc.parse<0>(&buffer[0]);
    root_node = doc.first_node("scene");
    for (xml_node<> * model_node = root_node->first_node("model"); model_node; model_node = model_node->next_sibling()){
        fich = "./../models/" + (string)model_node->first_attribute("file")->value();
        readFile(fich);
        cout << "Ficheiro " << fich << " lido com sucesso" << endl;
    }
    cout << "Ficheiro config.xml lido com sucesso";
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
    glutReshapeFunc(changeSize);


    // put here the registration of the keyboard callbacks
    glutSpecialFunc(processSpecialKeys);
    glutKeyboardFunc(processKeys);

    //  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

    // enter GLUT's main cycle
    glutMainLoop();

    return 1;
}

