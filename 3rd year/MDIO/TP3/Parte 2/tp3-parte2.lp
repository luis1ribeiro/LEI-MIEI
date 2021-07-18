/* Objective function */
min: 200r0 + 600r1 + 1000r2 + 200r3 + 180r6 + 0r7 + 200r8 + 0r9 + 1000r10 + 600r11 + 100z0 + 300z1 + 500z2 + 100z3 + 90z6 + 0z7 + 100z8 + 0z9 + 500z10 + 300z11;

/* Variable bounds */
tf <=19;

arco_i0: t0 >= ti + 0;
arco_i6: t6 >= ti + 0;
arco_01: t1 >= t0-r0-z0 + 4;
arco_02: t2 >= t0-r0-z0 + 4 ;
arco_03: t3 >= t0-r0-z0 + 4 ;
arco_12: t2 >= t1-r1-z1 + 6 ;
arco_23: t3 >= t2-r2-z2 + 7 ;
arco_67: t7 >= t6-r6-z6 + 5 ;
arco_78: t8 >= t7-r7-z7 + 6 ;
arco_72: t2 >= t7-r7-z7 + 6 ;
arco_89: t9 >= t8-r8-z8 + 4 ;
arco_83: t3 >= t8-r8-z8 + 4 ;
arco_610: t10 >= t6-r6-z6 + 5 ;
arco_108: t8 >= t10-r10-z10 + 8 ;
arco_119: t9 >= t11-r11-z11 + 7 ;
arco_1011: t11 >= t10-r10-z10 + 8;
arco_3f: tf >= t3-r3-z3 + 2 ;
arco_9f: tf >= t9-r9-z9 + 2 ;
arco_8f: tf >= t8-r8-z8 + 4;


r0<= 0.5;
r1<= 1;
r2<= 3;
r6<= 1;
r7<= 0;
r8<= 0.5;
r9<= 0;
r10<= 0.5;
r11<= 1;


0.5y0 <= r0;
1y1   <= r1;
3y2   <= r2;
0.5y3 <= r3;
1y6   <= r6;
0y7   <= r7;
0.5y8 <= r8;
0y9   <= r9;
0.5y10<= r10;
1y11  <= r11;


z0 <= 0.5*y0;
z1 <= 1*y1;
z2 <= 1*y2;
z3 <= 0.5*y3;
z6 <= 1*y6;
z7 <= 0*y7;
z8 <= 0.5*y8;
z9 <= 0*y9;
z10<= 0.5*y10;
z11<= 1*y11;

bin y0,y1,y2,y3,y6,y7,y8,y9,y10,y11;


