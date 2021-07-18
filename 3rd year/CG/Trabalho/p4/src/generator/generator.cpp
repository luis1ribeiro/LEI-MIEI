#include "generator.h"
#include <vector>


void cone (float r, float h ,int sl, int st, string f){
    float a = 0;
    float da = (2*M_PI) / sl;
    float dt = h/st;
    float alt = -h/2;
    float nr=r;
    float nr2;
    float diametro = r * 2;
    float texB = diametro / (h + diametro);
    float rtb = texB / 2;
    float texH = h / (h + diametro);
    float textureS = texH / st;
    float textureR = 1 / sl;

    ofstream file("./../models/"+f);

    //base
    for (int i=0;i<sl;i++){
        file << r * sin(a) << "," << alt << "," << r * cos(a) << endl;
        file << 0 << "," << -1 << "," << 0 << endl;
        file << rtb + rtb * cos(a) << "," <<  rtb + rtb * sin(a)  << endl;


        file << 0 << "," << alt << "," << 0 << endl;
        file << 0 << "," << -1 << "," << 0 << endl;
        file << rtb << "," << rtb << endl;


        file << r * sin(a+da) << "," << alt << "," << r * cos(a+da) << endl;
        file << 0 << "," << -1 << "," << 0 << endl;
        file << rtb + rtb * cos(a + da) << "," <<  rtb + rtb * sin(a + da)  << endl;

        a+=da;
    }

    //laterais
    for (int j=st;j>0;j--){
        nr2 = nr - (nr / j);    
        a=0;
        for (int i=0;i<sl;i++){
            // Triangulo 1
            file << nr * sin(a) << "," << alt << "," << nr * cos(a) << endl;
            file << sin(a) << "," << dt <<  "," << cos(a) << endl;
            file << i * textureR << "," << texB + j * textureS << endl;

            file << nr * sin(a+da) << "," << alt << "," << nr * cos(a+da) << endl;
            file << sin(a+da) << "," << dt << "," << cos(a+da) << endl;
            file << (i+1) * textureR << "," << texB + j * textureS << endl;

            file << nr2 * sin(a+da) << "," << alt+dt << "," << nr2 * cos(a+da) << endl;
            file << sin(a+da) << "," << dt << "," << cos(a+da) << endl;
            file << (i+1) * textureR << "," << texB + (j+1) * textureS << endl;

            // Triangulo 2

            file << nr2 * sin(a+da) << "," << alt+dt << "," << nr2 * cos(a+da) << endl;
            file << sin(a+da) << "," << dt << "," << cos(a+da) << endl;
            file << (i + 1) * textureR << "," << texB + (j + 1) * textureS << endl;

            file << nr2 * sin(a) << "," << alt+dt << "," << nr2 * cos(a) << endl;
            file << sin(a) << "," << dt << "," << cos(a) << endl;
            file << i * textureR << "," << texB + (j + 1) * textureS << endl;

            file << nr * sin(a) << "," << alt << "," << nr * cos(a) << endl;
            file << sin(a) << "," << dt << "," << cos(a) << endl;
            file << i * textureR << "," << texB + j * textureS << endl;

            a+=da;
        }
        alt+=dt;
        nr=nr2;
    }
}



void torus (float ri, float re, int st, int sl, string f){
  float a = 0;
  float da = (2*M_PI) / st;
  float b = 0;
  float db = (2*M_PI) / sl;
  float rc = (re - ri) / 2;
  float rd,rds;
  float x_circ_text = 0;
  float y_circ_text = 0;
  float z_circ_text = 0;
  float textureS = 1.0f / st;
  float textureR = 1.0f / sl;

  ofstream file("./../models/"+f);

  for (int i=0;i<sl;i++){
    b = i * db;

    for (int j=0;j<st;j++){
      a = j * da;
      rd = ri + rc - rc * cos(a);
      rds = ri + rc - rc * cos(a+da);
      
      //Triangulo 1

      file << rd * sin(b) << "," << rc * sin(a) << "," << rd * cos(b) << endl;
      file << sin(b) << "," << sin(a) << "," << cos(b) << endl;
      file << i * textureR << "," << j * textureS << endl;

      file << rds * sin(b) << "," << rc * sin(a+da) << "," << rds * cos(b) << endl;
      file << sin(b) << "," << sin(a + da) << "," << cos(b) << endl;
      file << i * textureR << "," << (j+1) * textureS << endl;

      file << rds * sin(b+db) << "," << rc * sin(a+da) << "," << rds * cos(b+db) << endl;
      file << sin(b + db) << "," << sin(a + da) << "," << cos(b + db) << endl;
      file << (i+1) * textureR << "," << (j+1) * textureS << endl;

      // Triangulo 2

      file << rds * sin(b+db) << "," << rc * sin(a+da) << "," << rds * cos(b+db) << endl;
      file << sin(b + db) << "," << sin(a + da) << "," << cos(b + db) << endl;
      file << (i+1) * textureR << "," << (j+1) * textureS << endl;

      file << rd * sin(b+db) << "," << rc * sin(a) << "," << rd * cos(b+db) << endl;
      file << sin(b + db) << "," << sin(a) << "," << cos(b + db) << endl;
      file << (i+1) * textureR << "," << j * textureS << endl;

      file << rd * sin(b) << "," << rc * sin(a) << "," << rd * cos(b) << endl;
      file << sin(b) << "," << sin(a) << "," << cos(b) << endl;
      file << i * textureR << "," << j * textureS << endl;
    }
  }
}


void sphere (float r, int sl, int st, string f){
    float a = 0;
    float da = (2*M_PI) / sl;
    float b = (-M_PI)/2;
    float db = (M_PI) /st;
    float nr=r*cos(b);
    float nr2=nr;
    float textureS = 1.0f / st;
    float textureR = 1.0f / sl;

    ofstream file("./../models/"+f);

    //parte de cima
    for (int j=0;j<st;j++){
        nr2 = r * cos(b+db);
        a=0;
        for (int i=0;i<sl;i++){
            // Triangulo 1
            file << nr * sin(a) << "," << r * sin(b) << "," << nr * cos(a) << endl;
            file << sin(a) << "," << sin(b) << "," << cos(a) << endl;
            file << i * textureR << "," << j * textureS << endl;

            file << nr * sin(a+da) << "," << r * sin (b) << "," << nr * cos(a+da) << endl;
            file << sin(a + da) << "," << sin(b) << "," << cos(a + da)  << endl;
            file << (i+1) * textureR << "," << j * textureS << endl;

            file << nr2 * sin(a+da) << "," << r * sin (b+db) << "," << nr2 * cos(a+da) << endl;
            file << sin(a + da)  << "," << sin(b + db) << "," << cos(a + da)  << endl;
            file << (i+1) * textureR << "," << (j+1) * textureS << endl;

            // Triangulo 2
            file << nr2 * sin(a+da) << "," << r * sin (b+db) << "," << nr2 * cos(a+da) << endl;
            file <<  sin(a + da)  << "," << sin(b + db) << "," << cos(a + da)  << endl;
            file << (i + 1) * textureR << "," << (j + 1) * textureS << endl;
            
            file << nr2 * sin(a) << "," << r * sin(b+db) << "," << nr2 * cos(a) << endl;
            file << sin(a) << "," << sin(b + db) << "," << cos(a)  << endl;
            file << i * textureR << "," << (j+1) * textureS << endl;
            
            file << nr * sin(a) << "," << r * sin(b) << "," << nr * cos(a) << endl;
            file << sin(a)  << "," << sin(b) << "," <<  cos(a)  << endl;
            file << i * textureR << "," << j * textureS << endl;
            a+=da;
        }
        b+=db;
        nr=nr2;
    }
}



void plane (float dimx, float dimz, string f){
    float x = dimx/2;
    float z = dimz/2;

    ofstream file("./../models/"+f);

    /* Primeiro triangulo CIMA */
    file << -x << "," << "0" << "," << z << endl;
    file << 0 << "," << 1 << "," << 0 << endl;
    file << 0 << "," << 0 << endl;

    file << x << "," << "0" << "," << z << endl;
    file << 0 << "," << 1 << "," << 0 << endl;
    file << 1 << "," << 0 << endl;

    file << x << "," << "0" << "," << -z << endl;
    file << 0 << "," << 1 << "," << 0 << endl;
    file << 1 << "," << 1 << endl;

    /* Segundo triangulo CIMA */
    file << x << "," << "0" << "," << -z << endl;
    file << 0 << "," << 1 << "," << 0 << endl;
    file << 1 << "," << 1 << endl;

    file << -x << "," << "0" << "," << -z << endl;
    file << 0 << "," << 1 << "," << 0 << endl;
    file << 0 << "," << 1 << endl;

    file << -x << "," << "0" << "," << z << endl;
    file << 0 << "," << 1 << "," << 0 << endl;
    file << 0 << "," << 0 << endl;

    /* Primeiro triangulo BAIXO */
    file << x << "," << "0" << "," << -z << endl;
    file << 0 << "," << -1 << "," << 0 << endl;
    file << 1 << "," << 1 << endl;

    file << x << "," << "0" << "," << z << endl;
    file << 0 << "," << -1 << "," << 0 << endl;
    file << 1 << "," << 0 << endl;

    file << -x << "," << "0" << "," << z << endl;
    file << 0 << "," << -1 << "," << 0 << endl;
    file << 0 << "," << 0 << endl;

    /* Segundo triangulo BAIXO */
    file << -x << "," << "0" << "," << z << endl;
    file << 0 << "," << -1 << "," << 0 << endl;
    file << 0 << "," << 0 << endl;

    file << -x << "," << "0" << "," << -z << endl;
    file << 0 << "," << -1 << "," << 0 << endl;
    file << 0 << "," << 1 << endl;

    file << x << "," << "0" << "," << -z << endl;
    file << 0 << "," << -1 << "," << 0 << endl;
    file << 1 << "," << 1 << endl;

    file.close();
}

void box_front_back(float x, float y, float z, float xdif, float ydif, float zdif, ofstream& file, int sl){
    const float _x = x;
    const float _z = z;
    const float _y = y;
    float texR = 1.0f / sl;
    for(int i=0; i<sl; i++){
        for(int j=0; j<sl; j++){
            // front
            // t1
            file << x << "," << y << "," <<  z << endl;
            file << 0 << "," << 0 << "," << 1 << endl;
            file << (sl -j) * texR << "," << (sl-i) * texR << endl;
            

            file << x - xdif << "," << y - ydif << "," << z << endl;
            file << 0 << "," << 0 << "," << 1 << endl;
            file << (sl - (j+1)) * texR << "," << (sl - (i+1)) * texR << endl;
            
            file << x << "," << y - ydif << "," << z << endl;
            file << 0 << "," << 0 << "," << 1 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            // t2
            file << x << "," << y << "," << z << endl;
            file << 0 << "," << 0 << "," << 1 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            file << x - xdif << "," << y << "," << z << endl;
            file << 0 << "," << 0 << "," << 1 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            file << x - xdif << "," << y - ydif << "," << z << endl;
            file << 0 << "," << 0 << "," << 1 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            //back
            // t1
            file << x << "," << y - ydif << "," << -z << endl;
            file << 0 << "," << 0 << "," << -1 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x - xdif << "," << y - ydif << "," << -z << endl;
            file << 0 << "," << 0 << "," << -1 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x << "," << y << "," <<  -z << endl;
            file << 0 << "," << 0 << "," << -1 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            // t2
            file << x - xdif << "," << y - ydif << "," << -z << endl;
            file << 0 << "," << 0 << "," << -1 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x - xdif << "," << y << "," << -z << endl;
            file << 0 << "," << 0 << "," << -1 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            file << x << "," << y << "," << -z << endl;
            file << 0 << "," << 0 << "," << -1 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            x -= xdif;
        }
        y -= ydif;
        x = _x;
    }
}

void box_left_right(float x, float y, float z, float xdif, float ydif, float zdif, ofstream& file, int sl){
    const float _x = x;
    const float _z = z;
    const float _y = y;
    float texR = 1.0f / sl;
    for(int i=0; i<sl; i++){
        for(int j=0; j<sl; j++){
            // right
            // t1
            file << x << "," << y << "," << z-zdif << endl;
            file << 1 << "," << 0 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            file << x << "," << y-ydif << "," << z << endl;
            file << 1 << "," << 0 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x << "," << y-ydif << "," << z-zdif << endl;
            file << 1 << "," << 0 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            // t2
            file << x << "," << y << "," << z-zdif << endl;
            file << 1 << "," << 0 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            file << x << "," << y << "," << z << endl;
            file << 1 << "," << 0 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            file << x << "," << y-ydif << "," << z << endl;
            file << 1 << "," << 0 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            // left
            // t1
            file << -x << "," << y-ydif << "," << z-zdif << endl;
            file << -1 << "," << 0 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << -x << "," << y-ydif << "," << z << endl;
            file << -1 << "," << 0 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << -x << "," << y << "," << z-zdif << endl;
            file << -1 << "," << 0 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            // t2
            file << -x << "," << y-ydif << "," << z << endl;
            file << -1 << "," << 0 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << -x << "," << y << "," << z << endl;
            file << -1 << "," << 0 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            file << -x << "," << y << "," << z-zdif << endl;
            file << -1 << "," << 0 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            z -= zdif;
        }
        y -= ydif;
        z = _z;
    }
}

void box_top_bottom(float x, float y, float z, float xdif, float ydif, float zdif, ofstream& file, int sl){
    const float _x = x;
    const float _z = z;
    const float _y = y;
    float texR = 1.0f / sl;
    for(int i=0; i<sl; i++){
        for(int j=0; j<sl; j++){
            // top
            // t1
            file << x << "," << y << "," << z << endl;
            file << 0 << "," << 1 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            file << x << "," << y << "," << z-zdif << endl;
            file << 0 << "," << 1 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x-xdif << "," << y << "," << z << endl;
            file << 0 << "," << 1 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            // t2
            file << x << "," << y << "," << z-zdif << endl;
            file << 0 << "," << 1 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x-xdif << "," << y << "," << z-zdif << endl;
            file << 0 << "," << 1 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x-xdif << "," << y << "," << z << endl;
            file << 0 << "," << 1 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            // bottom
            // t1
            file << x-xdif << "," << -y << "," << z << endl;
            file << 0 << "," << -1 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            file << x << "," << -y << "," << z-zdif << endl;
            file << 0 << "," << -1 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x << "," << -y << "," << z << endl;
            file << 0 << "," << -1 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - i) * texR << endl;

            // t2
            file << x-xdif << "," << -y << "," << z << endl;
            file << 0 << "," << -1 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - i) * texR << endl;

            file << x-xdif << "," << -y << "," << z-zdif << endl;
            file << 0 << "," << -1 << "," << 0 << endl;
            file << (sl - (j + 1)) * texR << "," << (sl - (i + 1)) * texR << endl;

            file << x << "," << -y << "," << z-zdif << endl;
            file << 0 << "," << -1 << "," << 0 << endl;
            file << (sl - j) * texR << "," << (sl - (i + 1)) * texR << endl;

            x -= xdif;
        }
        z -= zdif;
        x = _x;
    }
}

void box (float dimx, float dimz, float dimy, int sl, string f){
    sl++;
    float x = dimx/2;
    float y = dimy/2;
    float z = dimz/2;

    ofstream file("./../models/"+f);

    float xdif = dimx/sl;
    float ydif = dimy/sl;
    float zdif = dimz/sl;

    /* Parte da frente e atras */
    box_front_back(x,y,z,xdif,ydif,zdif,file,sl);

    /* Laterais */
    box_left_right(x,y,z,xdif,ydif,zdif,file,sl);

    /* Topo e base */
    box_top_bottom(x,y,z,xdif,ydif,zdif,file,sl);
}

void multMatrixVector(float* m, float* v, float* res) {

    for (int j = 0; j < 4; ++j) {
        res[j] = 0;
        for (int k = 0; k < 4; ++k) {
            res[j] += v[k] * m[j * 4 + k];
        }
    }

}


Point bezier_formula(vector<Point> p, float u, float v) {
    float M[4][4] = { { -1, 3, -3, 1}, { 3, -6, 3, 0},{ -3, 3, 0, 0 }, { 1, 0, 0, 0}};

    float U[4] = { u * u * u, u * u, u, 1 };
    float V[4] = { v * v * v, v * v, v, 1 };

    // U * M
    float UM[4];
    for (int i = 0; i < 4; i++) {
        UM[i] = 0;
        for (int j = 0; j < 4; j++) {
            UM[i] += U[j] * M[i][j];
        }
    }

    // UM * P
    float UMP[3][4];
    for (int i = 0; i < 4; i++) {
        UMP[0][i] = UM[0] * p[4 * i].getX() + UM[1] * p[4 * i + 1].getX() + UM[2] * p[4 * i + 2].getX() + UM[3] * p[4 * i + 3].getX();
        UMP[1][i] = UM[0] * p[4 * i].getY() + UM[1] * p[4 * i + 1].getY() + UM[2] * p[4 * i + 2].getY() + UM[3] * p[4 * i + 3].getY();
        UMP[2][i] = UM[0] * p[4 * i].getZ() + UM[1] * p[4 * i + 1].getZ() + UM[2] * p[4 * i + 2].getZ() + UM[3] * p[4 * i + 3].getZ();
    }

    // UMP * M
    float UMPM[3][4];
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 4; j++) {
            UMPM[i][j] = UMP[i][0] * M[0][j] + UMP[i][1] * M[1][j] + UMP[i][2] * M[2][j] + UMP[i][3] * M[3][j];
        }
    }

    // UMPM * V

    float UMPMV[3];
    for (int i = 0; i < 3; i++) {
            UMPMV[i] = UMPM[i][0] * V[0] + UMPM[i][1] * V[1] + UMPM[i][2] * V[2] + UMPM[i][3] * V[3];
    }

    return Point(UMPMV[0], UMPMV[1], UMPMV[2]);
}

Point tangent_vectorU(vector<Point> p, float u, float v) {
    float M[4][4] = { { -1, 3, -3, 1}, { 3, -6, 3, 0},{ -3, 3, 0, 0 }, { 1, 0, 0, 0} };

    float U[4] = { 3 * u * u, 2 * u, 1, 0 };
    float V[4] = { v * v * v, v * v, v, 1 };

    // U * M
    float UM[4];
    for (int i = 0; i < 4; i++) {
        UM[i] = 0;
        for (int j = 0; j < 4; j++) {
            UM[i] += U[j] * M[i][j];
        }
    }

    // UM * P
    float UMP[3][4];
    for (int i = 0; i < 4; i++) {
        UMP[0][i] = UM[0] * p[4 * i].getX() + UM[1] * p[4 * i + 1].getX() + UM[2] * p[4 * i + 2].getX() + UM[3] * p[4 * i + 3].getX();
        UMP[1][i] = UM[0] * p[4 * i].getY() + UM[1] * p[4 * i + 1].getY() + UM[2] * p[4 * i + 2].getY() + UM[3] * p[4 * i + 3].getY();
        UMP[2][i] = UM[0] * p[4 * i].getZ() + UM[1] * p[4 * i + 1].getZ() + UM[2] * p[4 * i + 2].getZ() + UM[3] * p[4 * i + 3].getZ();
    }

    // UMP * M
    float UMPM[3][4];
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 4; j++) {
            UMPM[i][j] = UMP[i][0] * M[0][j] + UMP[i][1] * M[1][j] + UMP[i][2] * M[2][j] + UMP[i][3] * M[3][j];
        }
    }

    // UMPM * V

    float UMPMV[3];
    for (int i = 0; i < 3; i++) {
        UMPMV[i] = UMPM[i][0] * V[0] + UMPM[i][1] * V[1] + UMPM[i][2] * V[2] + UMPM[i][3] * V[3];
    }

    //cout << UMPMV[0] << "," << UMPMV[1] << "," << UMPMV[2] << endl;
    return Point(UMPMV[0], UMPMV[1], UMPMV[2]);
}

Point tangent_vectorV(vector<Point> p, float u, float v) {
    float M[4][4] = { { -1, 3, -3, 1}, { 3, -6, 3, 0},{ -3, 3, 0, 0 }, { 1, 0, 0, 0} };

    float U[4] = { u * u * u, u * u, u, 1 };
    float V[4] = { 3 * v * v, 2 * v, 1, 0 };

    // U * M
    float UM[4];
    for (int i = 0; i < 4; i++) {
        UM[i] = 0;
        for (int j = 0; j < 4; j++) {
            UM[i] += U[j] * M[i][j];
        }
    }

    // UM * P
    float UMP[3][4];
    for (int i = 0; i < 4; i++) {
        UMP[0][i] = UM[0] * p[4 * i].getX() + UM[1] * p[4 * i + 1].getX() + UM[2] * p[4 * i + 2].getX() + UM[3] * p[4 * i + 3].getX();
        UMP[1][i] = UM[0] * p[4 * i].getY() + UM[1] * p[4 * i + 1].getY() + UM[2] * p[4 * i + 2].getY() + UM[3] * p[4 * i + 3].getY();
        UMP[2][i] = UM[0] * p[4 * i].getZ() + UM[1] * p[4 * i + 1].getZ() + UM[2] * p[4 * i + 2].getZ() + UM[3] * p[4 * i + 3].getZ();
    }

    // UMP * M
    float UMPM[3][4];
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 4; j++) {
            UMPM[i][j] = UMP[i][0] * M[0][j] + UMP[i][1] * M[1][j] + UMP[i][2] * M[2][j] + UMP[i][3] * M[3][j];
        }
    }

    // UMPM * V

    float UMPMV[3];
    for (int i = 0; i < 3; i++) {
        UMPMV[i] = UMPM[i][0] * V[0] + UMPM[i][1] * V[1] + UMPM[i][2] * V[2] + UMPM[i][3] * V[3];
    }
    //cout << UMPMV[0] << "," << UMPMV[1] << "," << UMPMV[2] << endl;
    return Point(UMPMV[0], UMPMV[1], UMPMV[2]);
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


Point bezier_normal(vector<Point> p, float u, float v) {
    Point vectorU = tangent_vectorU(p, u, v);
    Point vectorV = tangent_vectorV(p, u, v);
    float* normal = new float[3];
    float* vu = new float[3];
    float* vv = new float[3];
    
    vu[0] = vectorU.getX();
    vu[1] = vectorU.getY();
    vu[2] = vectorU.getZ();

    vv[0] = vectorV.getX();
    vv[1] = vectorV.getY();
    vv[2] = vectorV.getZ();

   // normalize(vu);
   //normalize(vv);
    cross(vu, vv, normal);
    normalize(normal);

    return Point(normal[0], normal[1], normal[2]);
}


void bezier(string path, int lvl, string f) {
    ifstream read(path);
    ofstream file("./../models/" + f);
    string line;

    if (read.is_open()) {
        getline(read, line);
        const int patches = atoi(line.c_str());
        vector<vector<int>> indexes = vector<vector<int>>(patches);

        for (int i = 0; i < patches; i++) {
            getline(read, line);
            indexes[i] = vector<int>(16);
            for (int j = 0; j < 16; j++) {
                int p = line.find(",");
                indexes[i][j] = atoi(line.substr(0, p).c_str());
                line.erase(0, p + 1);
            }
        }

        getline(read, line);
        int nr_points = atoi(line.c_str());
        vector<Point> points = vector<Point>(nr_points);

        for (int i = 0; i < nr_points; i++) {
            getline(read, line);
            float x, y, z;
            int p = line.find(",");
            x = atof(line.substr(0, p).c_str());
            line.erase(0, p + 1);
            p = line.find(",");
            y = atof(line.substr(0, p).c_str());
            line.erase(0, p + 1);
            p = line.find(",");
            z = atof(line.substr(0, p).c_str());
            line.erase(0, p + 1);
            points[i] = Point(x, y, z);
        }

        float tess = (float)1 / (float)lvl;
        for (int n=0;n<patches;n++){
            vector<Point> points_formula = vector<Point>(16);
            for (int k = 0; k < 16; k++) {
                points_formula[k] = points[indexes[n][k]];
            }
            for (int i = 0; i < lvl + 1; i++) {
                for (int j = 0; j < lvl + 1; j++) {
                    float u = i * tess;
                    float v = j * tess;

                    Point p = bezier_formula(points_formula, u, v);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    p = bezier_normal(points_formula, u, v);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    file << u << "," << v << endl;

                    p = bezier_formula(points_formula, u+tess, v);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    p = bezier_normal(points_formula, u + tess, v);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    file << u+tess << "," << v << endl;

                    p = bezier_formula(points_formula, u, v+tess);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    p = bezier_normal(points_formula, u, v+tess);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    file << u << "," << v + tess << endl;

                    p = bezier_formula(points_formula, u, v + tess);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    p = bezier_normal(points_formula, u, v + tess);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    file << u << "," << v + tess << endl;

                    p = bezier_formula(points_formula, u + tess, v);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    p = bezier_normal(points_formula, u+tess,v);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    file << u+tess << "," << v << endl;

                    p = bezier_formula(points_formula, u+tess, v+tess);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    p = bezier_normal(points_formula, u+tess, v + tess);
                    file << p.getX() << "," << p.getY() << "," << p.getZ() << endl;
                    file << u+tess << "," << v + tess << endl;
                }
            }
        }
    }
    file.close();
    read.close();
}



int main (int argc, char** argv){
    if (argc<1){
        printf("Sem argumentos\n");
        return 1;
    }
    if (strcmp(argv[1],"plane")==0 && argc == 5){
        plane(atof(argv[2]),atof(argv[3]),argv[4]);
        return 0;
    }
    if (strcmp(argv[1],"box")==0 && argc == 7){
        box(atof(argv[2]),atof(argv[3]),atof(argv[4]),atoi(argv[5]),argv[6]);
        return 0;
    }
    if (strcmp(argv[1], "cone") == 0 && argc == 7){
        cone(atof(argv[2]), atof(argv[3]), atoi(argv[4]), atoi(argv[5]), argv[6]);
        return 0;
    }
    if (strcmp(argv[1], "sphere") == 0 && argc == 6){
        sphere(atof(argv[2]), atoi(argv[3]), atoi(argv[4]), argv[5]);
        return 0;
    }
    if (strcmp(argv[1],"torus") == 0 && argc == 7){
        torus(atof(argv[2]),atof(argv[3]),atoi(argv[4]),atoi(argv[5]),argv[6]);
        return 0;
    }
    if (strcmp(argv[1], "bezier") == 0 && argc == 5) {
        bezier(argv[2], atoi(argv[3]),argv[4]);
        return 0;
    }
    printf("Comando invalido\n");
    return 0;
}
