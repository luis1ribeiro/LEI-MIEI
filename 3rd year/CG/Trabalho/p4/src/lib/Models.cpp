#include "Models.h"
#include <iostream>


Models::Models() {
    points = vector<Point>();
    normais = vector<Point>();
    texturas = vector<Point>();
    mtl = MaterialLight();
    n = 0;
}

void Models::generateVBO() {
    cout << "Generating VBOs for model" << endl;
    int i;
    n = 0;
    int tc = 0;
    int tn = 0;
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);
    glEnableClientState(GL_TEXTURE_COORD_ARRAY);

    float* v = (float*)malloc(sizeof(float) * points.size() * 3);
    float* nor = (float*)malloc(sizeof(float) * normais.size() * 3);
    float* t = (float*)malloc(sizeof(float) * texturas.size() * 2);

    for (i = 0; i < points.size(); i++) {
        nor[tn++] = normais[i].getX();
        t[tc++] = texturas[i].getX();
        v[n++] = points[i].getX();

        nor[tn++] = normais[i].getY();
        t[tc++] = texturas[i].getY();
        v[n++] = points[i].getY();

        nor[tn++] = normais[i].getZ();
        v[n++] = points[i].getZ();
    }

    glGenBuffers(1, &vertex);
    glBindBuffer(GL_ARRAY_BUFFER, vertex);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * points.size() * 3, v, GL_STATIC_DRAW);

    glGenBuffers(1, &normal);
    glBindBuffer(GL_ARRAY_BUFFER, normal);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * normais.size() * 3, nor, GL_STATIC_DRAW);
    glGenBuffers(1, &texCoords);
    glBindBuffer(GL_ARRAY_BUFFER, texCoords);
    glBufferData(GL_ARRAY_BUFFER, sizeof(float) * 2 * texturas.size(), t, GL_STATIC_DRAW);
    free(v);
}

void Models::drawVBO() {
    float defaultDif[4] = { 0.8, 0.8, 0.8, 1.0 };
    float defaultAmb[4] = { 0.2, 0.2, 0.2, 1.0 };
    float defaultEmi[4] = { 0.0,0.0,0.0,1.0 };
    float defaultSpe[4] = { 0.0,0.0,0.0,1.0 };
    float defaultShi [1] = { 0.0};

    glBindTexture(GL_TEXTURE_2D, texID);

    glBindBuffer(GL_ARRAY_BUFFER, vertex);
    glVertexPointer(3, GL_FLOAT, 0, 0);

    glBindBuffer(GL_ARRAY_BUFFER, normal);
    glNormalPointer(GL_FLOAT, 0, 0);

    glBindBuffer(GL_ARRAY_BUFFER, texCoords);
    glTexCoordPointer(2, GL_FLOAT, 0, 0);

    if (mtl.getDifR() >= 0 && mtl.getDifG() >= 0 && mtl.getDifB() >= 0 && mtl.getDifR() <= 1 && mtl.getDifG() <= 1 && mtl.getDifB() <= 1) {
        float mat_dif[4];
        mat_dif[0] = mtl.getDifR();
        mat_dif[1] = mtl.getDifG();
        mat_dif[2] = mtl.getDifB();
        mat_dif[3] = 1.0f;
        glMaterialfv(GL_FRONT, GL_DIFFUSE, mat_dif);
    }

    if (mtl.getAmbR() >= 0 && mtl.getAmbG() >= 0 && mtl.getAmbB() >= 0 && mtl.getAmbR() <= 1 && mtl.getAmbG() <= 1 && mtl.getAmbB() <= 1) {
        float mat_amb[4];
        mat_amb[0] = mtl.getAmbR();
        mat_amb[1] = mtl.getAmbG();
        mat_amb[2] = mtl.getAmbB();
        mat_amb[3] = 1.0f;
        glMaterialfv(GL_FRONT, GL_AMBIENT, mat_amb);
    }

    if (mtl.getEmiR() >= 0 && mtl.getEmiG() >= 0 && mtl.getEmiB() >= 0 && mtl.getEmiR() <= 1 && mtl.getEmiG() <= 1 && mtl.getEmiB() <= 1) {
        float mat_emi[4];
        mat_emi[0] = mtl.getEmiR();
        mat_emi[1] = mtl.getEmiG();
        mat_emi[2] = mtl.getEmiB();
        mat_emi[3] = 1.0f;
        glMaterialfv(GL_FRONT, GL_EMISSION, mat_emi);
    }

    if (mtl.getSpeR() >= 0 && mtl.getSpeG() >= 0 && mtl.getSpeB() >= 0 && mtl.getSpeR() <= 1 && mtl.getSpeG() <= 1 && mtl.getSpeB() <= 1) {
        float mat_spe[4];
        mat_spe[0] = mtl.getSpeR();
        mat_spe[1] = mtl.getSpeG();
        mat_spe[2] = mtl.getSpeB();
        mat_spe[3] = 1.0f;
        glMaterialfv(GL_FRONT, GL_SPECULAR, mat_spe);
    }

    if (mtl.getShi()>=0 && mtl.getShi() <= 128) {
        float shi = mtl.getShi();
        glMaterialfv(GL_FRONT, GL_SHININESS, &shi);
    }


    glDrawArrays(GL_TRIANGLES, 0, n);

    glMaterialfv(GL_FRONT, GL_DIFFUSE, defaultDif);
    glMaterialfv(GL_FRONT, GL_AMBIENT, defaultAmb);
    glMaterialfv(GL_FRONT, GL_EMISSION, defaultEmi); 
    glMaterialfv(GL_FRONT, GL_SPECULAR, defaultSpe);
    glMaterialfv(GL_FRONT, GL_SHININESS, defaultShi);
    glBindTexture(GL_TEXTURE_2D, 0);
}