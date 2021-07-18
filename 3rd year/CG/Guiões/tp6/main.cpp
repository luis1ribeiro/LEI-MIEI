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

int imageWidth,imageHeight;
unsigned int t;
ILubyte *imageData;
GLuint buffers[1];
float* vertexB;


float camX = 00, camY = 30, camZ = 40;
int startX, startY, tracking = 0;

int alpha = 0, beta = 45, r = 50;

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



void drawTerrain() {
    // colocar aqui o código de desnho do terreno usando VBOs com TRIANGLE_STRIPS
    glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
    glVertexPointer(3,GL_FLOAT,0,0);
    int h;
    for (int i=0;i<imageHeight-1;i++){
        glColor3f(0.50,0.50,0.50);
        glDrawArrays(GL_LINE_STRIP,imageWidth*2*i,imageWidth*2);
    }
}



void renderScene(void) {

    float pos[4] = {-1.0, 1.0, 1.0, 0.0};

    glClearColor(0.0f,0.0f,0.0f,0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glLoadIdentity();
    gluLookAt(camX, camY, camZ,
            0.0,0.0,0.0,
            0.0f,1.0f,0.0f);

    drawTerrain();

    // End of frame
    glutSwapBuffers();
}



void processKeys(unsigned char key, int xx, int yy) {

    // put code to process regular keys in here
}



void processMouseButtons(int button, int state, int xx, int yy) {

    if (state == GLUT_DOWN)  {
        startX = xx;
        startY = yy;
        if (button == GLUT_LEFT_BUTTON)
            tracking = 1;
        else if (button == GLUT_RIGHT_BUTTON)
            tracking = 2;
        else
            tracking = 0;
    }
    else if (state == GLUT_UP) {
        if (tracking == 1) {
            alpha += (xx - startX);
            beta += (yy - startY);
        }
        else if (tracking == 2) {

            r -= yy - startY;
            if (r < 3)
                r = 3.0;
        }
        tracking = 0;
    }
}


void processMouseMotion(int xx, int yy) {

    int deltaX, deltaY;
    int alphaAux, betaAux;
    int rAux;

    if (!tracking)
        return;

    deltaX = xx - startX;
    deltaY = yy - startY;

    if (tracking == 1) {


        alphaAux = alpha + deltaX;
        betaAux = beta + deltaY;

        if (betaAux > 85.0)
            betaAux = 85.0;
        else if (betaAux < -85.0)
            betaAux = -85.0;

        rAux = r;
    }
    else if (tracking == 2) {

        alphaAux = alpha;
        betaAux = beta;
        rAux = r - deltaY;
        if (rAux < 3)
            rAux = 3;
    }
    camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
    camY = rAux * 							     sin(betaAux * 3.14 / 180.0);
}

float h(int i, int j){
	float height = imageData[i*imageWidth+j];
	return height*(60.0/255.0);
}

void generatePoints(){
    ilGenImages(1,&t);
    ilBindImage(t);
    ilLoadImage((ILstring)"terreno.jpg");
    ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);
    imageWidth = ilGetInteger(IL_IMAGE_WIDTH);
    imageHeight = ilGetInteger(IL_IMAGE_HEIGHT);
    imageData=ilGetData();

    /* imageWidth = 8; */
    /* imageHeight = 8; */

    int size = imageWidth*2*3*(imageHeight-1);
    vertexB = (float*) malloc(sizeof(float)*size);
    int v=0;

    float rx = (imageHeight-1)/2.0;
    float rz = (imageWidth-1)/2.0;

    for (float i=0;i<imageHeight-1;i++){
        for (float j=0;j<imageWidth;j++){
            vertexB[v++]=j-rx;
            vertexB[v++]=h(i,j);
            vertexB[v++]=i-rz;
            /* cout << "(" << vertexB[v-3] << "," << vertexB[v-2] << "," << vertexB[v-1] << ")" << endl; */

            vertexB[v++]=j-rx;
            vertexB[v++]=h(i+1,j);
            vertexB[v++]=i+1-rz;
            /* cout << "(" << vertexB[v-3] << "," << vertexB[v-2] << "," << vertexB[v-1] << ")" << endl; */
        }
    }

    glGenBuffers(1, buffers);
    glBindBuffer(GL_ARRAY_BUFFER,buffers[0]);
    glBufferData(GL_ARRAY_BUFFER,size*sizeof(float), vertexB, GL_STATIC_DRAW);
}



void init() {
    glEnableClientState(GL_VERTEX_ARRAY);
    ilInit();
    glewInit();

    generatePoints();

    // 	OpenGL settings
    glEnable(GL_DEPTH_TEST);
    //glEnable(GL_CULL_FACE);
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
    glutMouseFunc(processMouseButtons);
    glutMotionFunc(processMouseMotion);

    init();

    // enter GLUT's main cycle
    glutMainLoop();

    return 0;
}

