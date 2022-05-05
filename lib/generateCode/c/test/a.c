#include "../single/inc/datatype_definition.h"
#include "../single/inc/circular_fifo_lib.h"
//gcc -o a a.c ../single/src/circular_fifo_lib.c ../single/src/spinlock.c
int main(){


    Array6OfDoubleType a={1,2,3,4,5,6};
    Array6OfDoubleType b={7,8,9,10,11,12};
    Array6OfDoubleType r1,r2;


    circular_fifo_Array6OfDoubleType fifo;
    int channelsize=5;
    int buffersize=channelsize+1;
    Array6OfDoubleType buf[buffersize];
    init_channel_Array6OfDoubleType(&fifo,buf,buffersize);

    
    write_non_blocking_Array6OfDoubleType(&fifo,a);
    write_non_blocking_Array6OfDoubleType(&fifo,a);
    write_non_blocking_Array6OfDoubleType(&fifo,a);
    write_non_blocking_Array6OfDoubleType(&fifo,a);
    write_non_blocking_Array6OfDoubleType(&fifo,a);


    printf("front %d, rear %d\n",fifo.front,fifo.rear);


    read_non_blocking_Array6OfDoubleType(&fifo,&r1);
    for(int i=0;i<6;++i){       
        printf("%f ",r1[i]);
    }
    printf("\n");
    printf("front %d, rear %d",fifo.front,fifo.rear);






}