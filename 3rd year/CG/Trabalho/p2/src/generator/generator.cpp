#include "generator.h"

void cone (float r, float h ,int sl, int st, string f){
    float a = 0;
    float da = (2*M_PI) / sl;
    float dt = h/st;
    float alt = -h/2;
    float nr=r;
    float nr2;

    ofstream file("./../models/"+f);

    //base
    for (int i=0;i<sl;i++){
        file << r * sin(a) << "," << alt << "," << r * cos(a) << endl;
        file << 0 << "," << alt << "," << 0 << endl;
        file << r * sin(a+da) << "," << alt << "," << r * cos(a+da) << endl;
        a+=da;
    }

    //laterais
    for (int j=st;j>0;j--){
        nr2 = nr - (nr / j);
        a=0;
        for (int i=0;i<sl;i++){
            file << nr * sin(a) << "," << alt << "," << nr * cos(a) << endl;
            file << nr * sin(a+da) << "," << alt << "," << nr * cos(a+da) << endl;
            file << nr2 * sin(a+da) << "," << alt+dt << "," << nr2 * cos(a+da) << endl;

            file << nr2 * sin(a+da) << "," << alt+dt << "," << nr2 * cos(a+da) << endl;
            file << nr2 * sin(a) << "," << alt+dt << "," << nr2 * cos(a) << endl;
            file << nr * sin(a) << "," << alt << "," << nr * cos(a) << endl;
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

  ofstream file("./../models/"+f);

  for (int i=0;i<sl;i++){
    b = i * db;

    for (int j=0;j<st;j++){
      a = j * da;
      rd = ri + rc - rc * cos(a);
      rds = ri + rc - rc * cos(a+da);

      file << rd * sin(b) << "," << rc * sin(a) << "," << rd * cos(b) << endl;
      file << rds * sin(b) << "," << rc * sin(a+da) << "," << rds * cos(b) << endl;
      file << rds * sin(b+db) << "," << rc * sin(a+da) << "," << rds * cos(b+db) << endl;

      file << rds * sin(b+db) << "," << rc * sin(a+da) << "," << rds * cos(b+db) << endl;
      file << rd * sin(b+db) << "," << rc * sin(a) << "," << rd * cos(b+db) << endl;
      file << rd * sin(b) << "," << rc * sin(a) << "," << rd * cos(b) << endl;
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

    ofstream file("./../models/"+f);

    //parte de cima
    for (int j=0;j<st;j++){
        nr2 = r * cos(b+db);
        a=0;
        for (int i=0;i<sl;i++){
            file << nr * sin(a) << "," << r * sin(b) << "," << nr * cos(a) << endl;
            file << nr * sin(a+da) << "," << r * sin (b) << "," << nr * cos(a+da) << endl;
            file << nr2 * sin(a+da) << "," << r * sin (b+db) << "," << nr2 * cos(a+da) << endl;

            file << nr2 * sin(a+da) << "," << r * sin (b+db) << "," << nr2 * cos(a+da) << endl;
            file << nr2 * sin(a) << "," << r * sin(b+db) << "," << nr2 * cos(a) << endl;
            file << nr * sin(a) << "," << r * sin(b) << "," << nr * cos(a) << endl;
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
    file << x << "," << "0" << "," << z << endl;
    file << x << "," << "0" << "," << -z << endl;

    /* Segundo triangulo CIMA */
    file << x << "," << "0" << "," << -z << endl;
    file << -x << "," << "0" << "," << -z << endl;
    file << -x << "," << "0" << "," << z << endl;

    /* Primeiro triangulo BAIXO */
    file << x << "," << "0" << "," << -z << endl;
    file << x << "," << "0" << "," << z << endl;
    file << -x << "," << "0" << "," << z << endl;

    /* Segundo triangulo BAIXO */
    file << -x << "," << "0" << "," << z << endl;
    file << -x << "," << "0" << "," << -z << endl;
    file << x << "," << "0" << "," << -z << endl;

    file.close();
}

void box_front_back(float x, float y, float z, float xdif, float ydif, float zdif, ofstream& file, int sl){
    const float _x = x;
    const float _z = z;
    const float _y = y;
    for(int i=0; i<sl; i++){
        for(int j=0; j<sl; j++){
            file << x << "," << y << "," <<  z << endl;
            file << x - xdif << "," << y - ydif << "," << z << endl;
            file << x << "," << y - ydif << "," << z << endl;

            file << x << "," << y << "," << z << endl;
            file << x - xdif << "," << y << "," << z << endl;
            file << x - xdif << "," << y - ydif << "," << z << endl;

            file << x << "," << y - ydif << "," << -z << endl;
            file << x - xdif << "," << y - ydif << "," << -z << endl;
            file << x << "," << y << "," <<  -z << endl;

            file << x - xdif << "," << y - ydif << "," << -z << endl;
            file << x - xdif << "," << y << "," << -z << endl;
            file << x << "," << y << "," << -z << endl;

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
    for(int i=0; i<sl; i++){
        for(int j=0; j<sl; j++){
            file << x << "," << y << "," << z-zdif << endl;
            file << x << "," << y-ydif << "," << z << endl;
            file << x << "," << y-ydif << "," << z-zdif << endl;

            file << x << "," << y << "," << z-zdif << endl;
            file << x << "," << y << "," << z << endl;
            file << x << "," << y-ydif << "," << z << endl;

            file << -x << "," << y-ydif << "," << z-zdif << endl;
            file << -x << "," << y-ydif << "," << z << endl;
            file << -x << "," << y << "," << z-zdif << endl;

            file << -x << "," << y-ydif << "," << z << endl;
            file << -x << "," << y << "," << z << endl;
            file << -x << "," << y << "," << z-zdif << endl;

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
    for(int i=0; i<sl; i++){
        for(int j=0; j<sl; j++){
            file << x << "," << y << "," << z << endl;
            file << x << "," << y << "," << z-zdif << endl;
            file << x-xdif << "," << y << "," << z << endl;

            file << x << "," << y << "," << z-zdif << endl;
            file << x-xdif << "," << y << "," << z-zdif << endl;
            file << x-xdif << "," << y << "," << z << endl;

            file << x-xdif << "," << -y << "," << z << endl;
            file << x << "," << -y << "," << z-zdif << endl;
            file << x << "," << -y << "," << z << endl;

            file << x-xdif << "," << -y << "," << z << endl;
            file << x-xdif << "," << -y << "," << z-zdif << endl;
            file << x << "," << -y << "," << z-zdif << endl;

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
    printf("Comando invalido\n");
    return 0;
}
