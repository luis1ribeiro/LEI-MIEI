#include <stdio.h>
#include <stdio.h>

#define _USE_MATH_DEFINES
#include <math.h>
#include <time.h>
#include <cstdlib>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

/* Global variables */
bool started = false;     /* control variable to check if the trees were draw */
float** positions = NULL; /* array that holds all the tree positions */
int numberOfTrees;        /* self explanatory */
float rr = 0, rb = 0;       /* rotate red and rotate blue */

float alfa = 0.0f, beta = 0.5f, radius = 100.0f;
float camX, camY, camZ;


void spherical2Cartesian() {

    camX = radius * cos(beta) * sin(alfa);
    camY = radius * sin(beta);
    camZ = radius * cos(beta) * cos(alfa);
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

void drawCenter (){
    glColor3f(1.0f, 0.0f, 1.0f);
    glutSolidTorus(2,5,10,10);
}

void drawBlueTeapots(int numberTeapots){
    glColor3f(0.0f,0.0f,1.0f);
    glPushMatrix();
    glRotatef(rb, 0.0f, 1.0f, 0.0f);
    int dim=2;
    int radius=15,a=360/numberTeapots;
    for (int i=0;i<numberTeapots;i++){
        glPushMatrix();
        glTranslatef(radius,dim,0);
        glutSolidTeapot(dim);
        glPopMatrix();
        glRotatef(a,0,1,0);
    }
    glPopMatrix();
}

void generateCoordenate(float* x){
    float sinal = rand() % 2;
    *x = (float(rand())/float((RAND_MAX)) * 100); /* [0-100] */
    (*x) = sinal ? (-(*x)) : (*x);
}

bool isInside(float x, float z){
    const float radius = 50;
    return ((float)sqrt(pow(x,2) + pow(z,2))) > radius;
}

float** generateTreesPos(int* n){
    int sinal;
    /* Generate a random number of trees between 100 and 300 */
    srand(time(NULL));
    numberOfTrees = rand() % 200 + 300;
    *n = numberOfTrees;
    float x, z;
    float** _array;
    _array = (float**)malloc(sizeof(float*) * numberOfTrees);
    for (int i=0; i<numberOfTrees; i++){
        _array[i] = (float*)malloc(sizeof(float) * 2);
    }
    for (int i=0; i<numberOfTrees; i++){
        /* Generate random position for the trees */
        generateCoordenate(&x);
        generateCoordenate(&z);
        if (isInside(x,z)){
            /* Add it to the array */
            _array[i][0] = x;
            _array[i][1] = z;
        } else{
            i--;
        }
    }
    return _array;
}

void drawTrees(){
    if (!started){
        positions = generateTreesPos(&numberOfTrees);
        started = true;
    }
    for (int i=0; i<numberOfTrees; i++){
        /* Trunk tree */
        glPushMatrix();
        glColor3f(0.647059,0.164706,0.164706); /* Brown in hex */
        glTranslatef(positions[i][0], 0, positions[i][1]);
        glRotatef(-90,1.0,0,0);
        glutSolidCone(1,3,8,20);
        glPopMatrix();

        /* Green part of the tree aka leaves */
        glPushMatrix();
        glColor3f(0.137255,0.556863,0.137255); /* Green in hex */
        glTranslatef(positions[i][0], 2, positions[i][1]);
        glRotatef(-90,1.0,0,0);
        glutSolidCone(2,5,8,20);
        glPopMatrix();
    }
}

void drawRedTeapots(int numberTeapots){
    glColor3f(1.0f,0.0f,0.0f);
    glPushMatrix();
    glRotatef(rr, 0.0f, 1.0f, 0.0f);
    const int dim=2;
    const float rotationAngle = 90.0f;
    const float radius=35, a = 360.0f / (float)numberTeapots;
    for (int i=0;i<numberTeapots;i++){
        glPushMatrix();
        glTranslatef(radius,dim,0);
        glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);
        glutSolidTeapot(dim);
        glPopMatrix();
        glRotatef(a,0.0f,1.0f,0.0f);
    }
    glPopMatrix();
}

void renderScene(void) {

    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set the camera
    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
            0.0, 0.0, 0.0,
            0.0f, 1.0f, 0.0f);

    glBegin(GL_TRIANGLES);
    glColor3f(0.2f, 0.8f, 0.2f);
    glVertex3f(100.0f, 0, -100.0f);
    glVertex3f(-100.0f, 0, -100.0f);
    glVertex3f(-100.0f, 0, 100.0f);

    glVertex3f(100.0f, 0, -100.0f);
    glVertex3f(-100.0f, 0, 100.0f);
    glVertex3f(100.0f, 0, 100.0f);
    glEnd();

    drawCenter();
    drawBlueTeapots(8);
    drawRedTeapots(16);
    drawTrees();

    rr += 0.2;
    rb -= 0.2;

    // End of frame
    glutSwapBuffers();
}


void processKeys(unsigned char c, int xx, int yy) {

    // put code to process regular keys in here

}


void processSpecialKeys(int key, int xx, int yy) {

    switch (key) {

        case GLUT_KEY_RIGHT:
            alfa -= 0.1; break;

        case GLUT_KEY_LEFT:
            alfa += 0.1; break;

        case GLUT_KEY_UP:
            beta += 0.1f;
            if (beta > 1.5f)
                beta = 1.5f;
            break;

        case GLUT_KEY_DOWN:
            beta -= 0.1f;
            if (beta < -1.5f)
                beta = -1.5f;
            break;

        case GLUT_KEY_PAGE_DOWN: radius -= 1.0f;
                                 if (radius < 1.0f)
                                     radius = 1.0f;
                                 break;

        case GLUT_KEY_PAGE_UP: radius += 1.0f; break;
    }
    spherical2Cartesian();
    glutPostRedisplay();

}


void printInfo() {

    printf("Vendor: %s\n", glGetString(GL_VENDOR));
    printf("Renderer: %s\n", glGetString(GL_RENDERER));
    printf("Version: %s\n", glGetString(GL_VERSION));

    printf("\nUse Arrows to move the camera up/down and left/right\n");
    printf("Home and End control the distance from the camera to the origin");
}


int main(int argc, char **argv) {

    // init GLUT and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
    glutInitWindowPosition(100,100);
    glutInitWindowSize(800,800);
    glutCreateWindow("CG@DI-UM");

    // Required callback registry
    glutDisplayFunc(renderScene);
    glutReshapeFunc(changeSize);
    glutIdleFunc(renderScene);

    // Callback registration for keyboard processing
    glutKeyboardFunc(processKeys);
    glutSpecialFunc(processSpecialKeys);

    //  OpenGL settings
    glEnable(GL_DEPTH_TEST);
    //glEnable(GL_CULL_FACE);

    spherical2Cartesian();

    printInfo();

    // enter GLUT's main cycle
    glutMainLoop();

    return 1;
}
