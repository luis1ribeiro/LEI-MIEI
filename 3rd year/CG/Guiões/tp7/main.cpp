#include<stdio.h>
#include<stdlib.h>

#define _USE_MATH_DEFINES
#include <math.h>
#include <vector>
#include <iostream>

#include <IL/il.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

using namespace std;

bool started = false;     /* control variable to check if the trees were draw */
float** positions = NULL; /* array that holds all the tree positions */
int numberOfTrees;        /* self explanatory */
float rr = 0, rb = 0;       /* rotate red and rotate blue */

int imageWidth,imageHeight;
unsigned int t;
ILubyte *imageData;
GLuint buffers[1];


float px=0,py=0,pz=0,lx=0,ly=0,lz=0,upx=0,upy=1,upz=0;
float eyeh = 5;

float alfa = 0;

void changeSize(int w, int h) {

    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if(h == 0)
        h = 1;

    // compute window's aspect ratio
    float ratio = w * 1.0 / h;

    // Reset the coordinate system before modifying
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);

    // Set the correct perspective
    gluPerspective(45,ratio,1,1000);

    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}

float h(int i, int j){
    float height = imageData[i*imageWidth+j];
    return height*(60.0/255.0);
}

float hf(int px, int pz){
    px += imageWidth/2;
    pz += imageHeight/2;
    float x1 = floor(px);
    float x2 = x1 + 1;
    float z1 = floor(pz);
    float z2 = z1 + 1;
    float fz = pz - z1;
    float fx = px - x1;
    float h_x1_z  = h(x1,z1) * (1-fz) + h(x1,z2) * fz;
    float h_x2_z  = h(x2,z1) * (1-fz) + h(x2,z2) * fz;
    return h_x1_z * (1 - fx) + h_x2_z * fx;
}

void drawTerrain() {
    glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
    glVertexPointer(3,GL_FLOAT,0,0);
    glPushMatrix();
    for (int i=0;i<imageHeight-1;i++){
        glDrawArrays(GL_TRIANGLE_STRIP,imageWidth*2*i,imageWidth*2);
    }
    glPopMatrix();
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
    /* srand(time(NULL)); */
    srand(0);
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
        glTranslatef(positions[i][0], hf(positions[i][1],positions[i][0]), positions[i][1]);
        glRotatef(-90,1.0,0,0);
        glutSolidCone(1,3,8,20);
        glPopMatrix();

        /* Green part of the tree aka leaves */
        glPushMatrix();
        glColor3f(0.137255,0.556863,0.137255); /* Green in hex */
        glTranslatef(positions[i][0], hf(positions[i][1],positions[i][0])+2, positions[i][1]);
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


void drawScene(){
    glDisableClientState(GL_VERTEX_ARRAY);
    glPushMatrix();
    drawCenter();
    drawBlueTeapots(8);
    drawRedTeapots(16);
    drawTrees();
    glPopMatrix();
    rr += 0.2;
    rb -= 0.2;
    glEnableClientState(GL_VERTEX_ARRAY);
}

void renderScene(void) {

    glClearColor(0.0f,0.0f,0.0f,0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glLoadIdentity();
    py = eyeh + hf (pz,px);
    lx = px + sin(alfa);
    ly = py;
    lz = pz + cos(alfa);
    gluLookAt(px, py, pz,
            lx,ly,lz,
            upx,upy,upz);

    glColor3f(0.3,0.7,0);
    drawTerrain();

    drawScene();
    // End of frame
    glutSwapBuffers();
}

void moveForward(float k){
    float dx = lx - px;
    float dz = lz - pz;
    px += k * dx;
    pz += k * dz;
    if (px>imageWidth/2 || px<-imageWidth/2) px -= k * dx;
    if (pz>imageHeight/2 || px<-imageHeight/2) pz -= k * dz;
}

void moveBackwards(float k){
    float dx = lx - px;
    float dz = lz - pz;
    px -= k * dx;
    pz -= k * dz;
    if (px>imageWidth/2 || px<-imageWidth/2) px += k * dx;
    if (pz>imageHeight/2 || px<-imageHeight/2) pz += k * dz;
}

void moveLeft (float k){
    float dx = lx - px;
    float dy = 0;
    float dz = lz - pz;
    float rx = dy*upz - dz*upy;
    float rz = dx*upy - dy*upx;
    px -= k * rx;
    pz -= k * rz;
    if (px>imageWidth/2 || px<-imageWidth/2) px += k * rx;
    if (pz>imageHeight/2 || px<-imageHeight/2) pz += k * rz;
}

void moveRight (float k){
    float dx = lx - px;
    float dy = 0;
    float dz = lz - pz;
    float rx = dy*upz - dz*upy;
    float rz = dx*upy - dy*upx;
    px += k * rx;
    pz += k * rz;
    if (px>imageWidth/2 || px<-imageWidth/2) px -= k * rx;
    if (pz>imageHeight/2 || px<-imageHeight/2) pz -= k * rz;
}

void processKeys (unsigned char key, int xx, int yy){
    switch(key){
        case 'w':
            moveForward(1);
            break;
        case 'W':
            moveForward(1);
            break;
        case 's':
            moveBackwards(1);
            break;
        case 'S':
            moveBackwards(1);
            break;
        case 'a':
            moveLeft(1);
            break;
        case 'A':
            moveLeft(1);
            break;
        case 'd':
            moveRight(1);
            break;
        case 'D':
            moveRight(1);
            break;
    }
    glutPostRedisplay();
}



void processSpecialKeys(int  key, int xx, int yy) {
    switch (key) {
        case GLUT_KEY_RIGHT:
            alfa -= 0.05; break;

        case GLUT_KEY_LEFT:
            alfa += 0.05; break;
    }
    glutPostRedisplay();
}


void generatePoints(){
    ilGenImages(1,&t);
    ilBindImage(t);
    ilLoadImage((ILstring)"terreno.jpg");
    ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);
    imageWidth = ilGetInteger(IL_IMAGE_WIDTH);
    imageHeight = ilGetInteger(IL_IMAGE_HEIGHT);
    imageData=ilGetData();


    int size = imageWidth*2*3*(imageHeight-1);
    float* vertexB = (float*) malloc(sizeof(float)*size);
    int v=0;

    float rx = (imageHeight-1)/2.0;
    float rz = (imageWidth-1)/2.0;

    for (float i=0;i<imageHeight-1;i++){
        for (float j=0;j<imageWidth;j++){
            vertexB[v++]=j-rx;
            vertexB[v++]=h(i,j);
            vertexB[v++]=i-rz;

            vertexB[v++]=j-rx;
            vertexB[v++]=h(i+1,j);
            vertexB[v++]=i+1-rz;
        }
    }

    glGenBuffers(1, buffers);
    glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
    glBufferData(GL_ARRAY_BUFFER,size*sizeof(float), vertexB, GL_STATIC_DRAW);
    free(vertexB);
}



void init() {
    glewInit();

    printf("Vendor: %s\n", glGetString(GL_VENDOR));
    printf("Renderer: %s\n", glGetString(GL_RENDERER));
    printf("Version: %s\n", glGetString(GL_VERSION));

    glEnableClientState(GL_VERTEX_ARRAY);
    ilInit();

    generatePoints();

    // 	OpenGL settings
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_BLEND);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_NORMALIZE);
}


int main(int argc, char **argv) {

    // init GLUT and the window
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
    glutInitWindowPosition(100,100);
    glutInitWindowSize(320,320);
    glutCreateWindow("CG@DI-UM");


    // Required callback registry
    glutDisplayFunc(renderScene);
    glutIdleFunc(renderScene);
    glutReshapeFunc(changeSize);

    // Callback registration for keyboard processing
    glutKeyboardFunc(processKeys);
    glutSpecialFunc(processSpecialKeys);

    init();

    // enter GLUT's main cycle
    glutMainLoop();

    return 0;
}
