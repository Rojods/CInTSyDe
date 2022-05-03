#include <stdio.h>
#include "datatype_definition.h"
Array6OfDoubleType buffer[2];
int size = 3;
circular_fifo_Array6OfDoubleType fifo;
#define PRINT(arr)  { for(int i=0;i <6 ; ++i){\
                        printf("%f ",arr[i]);\
                            }\
                    printf("\n");}


// circular_fifo_Array6OfDoubleType fifo_GrayScaleToGetPx;
// spinlock spinlock_GrayScaleToGetPx={.flag=0};	
int main(){
    init_channel_Array6OfDoubleType(&fifo,buffer,size);
    Array6OfDoubleType a={1,2,3,4,5,6};
    Array6OfDoubleType b={7,8,9,10,11,12};
    Array6OfDoubleType c;
    Array6OfDoubleType d;
    write_non_blocking_Array6OfDoubleType(&fifo,a);
    write_non_blocking_Array6OfDoubleType(&fifo,b);


    read_non_blocking_Array6OfDoubleType(&fifo,&c);
    read_non_blocking_Array6OfDoubleType(&fifo,&d);

    PRINT(c);
    PRINT(d);
    return 0;
}


		

