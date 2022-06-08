#include <stdio.h>
#include "./single/tile/subsystem.h"
#include "./single/datatype/datatype_definition.h"
#include "./single/circular_fifo_lib/circular_fifo_lib.h"

#define X 5
#define Y 5
UInt16 dimX_global=X;
UInt16 dimY_global=Y;
double a[Y][X];
double *b[Y];

double c[Y][X];
double *d[Y];
ArrayXOfArrayXOfDoubleType system_img_source_global;
ArrayXOfArrayXOfDoubleType system_img_sink_global;
int main(){
    int e=0;
    for(int i=0;i<Y;++i){
        for(int j=0;j<X;++j){
            a[i][j]=e;
            ++e;
            c[i][j]=0;
        }
    }

    for(int i=0;i<Y;++i){
          b[i]=a+i;
    }
    system_img_source_global=b;

    for(int i=0;i<Y;++i){
          d[i]=c+i;
    }    


    system_img_sink_global=d;

        init_subsystem();

        
        subsystem();
    
    printf("\n");
    for(int i=0;i<Y;++i){
        for(int j=0;j<X;++j){
            printf("%f, ", c[i][j]);
        }
        printf("\n");
    }
    return 0;
}