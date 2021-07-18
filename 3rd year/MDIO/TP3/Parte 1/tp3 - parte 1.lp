/* Objective function */
min:tf ;

/* Variable bounds */
arco_i0: t0 >= ti + 0 ;
arco_i6: t6 >= ti + 0 ;
arco_01: t1 >= t0 + 4 ;
arco_02: t2 >= t0 + 4 ;
arco_03: t3 >= t0 + 4 ;
arco_12: t2 >= t1 + 6 ;
arco_23: t3 >= t2 + 7 ;
arco_67: t7 >= t6 + 5 ;
arco_78: t8 >= t7 + 6 ;
arco_72: t2 >= t7 + 6 ;
arco_89: t9 >= t8 + 4 ;
arco_83: t3 >= t8 + 4 ;
arco_610: t10 >= t6 + 5 ;
arco_108: t8 >= t10 + 8 ;
arco_119: t9 >= t11 + 7 ;
arco_1011: t11 >= t10 + 8;
arco_3f: tf >= t3 + 2 ;
arco_9f: tf >= t9 + 2 ;
arco_8f: tf >= t8 + 4;

t1 + 6 <= t7 + 1000000 - 1000000y17;
t7 + 6 <= t1 + 1000000y17;

t1 + 6 <= t10 + 1000000 - 1000000y110;
t10 + 8 <= t1 + 1000000y110;

t7 + 6 <= t10 + 1000000 - 1000000y710;
t10 + 8 <= t7 + 1000000y710;

bin y17,y110,y710;

