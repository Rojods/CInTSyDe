#include "./single/inc/circular_fifo_lib.h"
#include "./single/inc/config.h"
#include "./single/inc/subsystem.h"
#define X 10
#define Y 10
UInt16 dimX_global=X;
UInt16 dimY_global=Y;
double a[Y][X];
double *b[Y];

double c[Y][X];
double *d[Y];
ArrayXOfArrayXOfDoubleType system_img_source_global;
ArrayXOfArrayXOfDoubleType system_img_sink_global;
int main(){
    initChannels();
    for(int i=0;i<Y;++i){
        for(int j=0;j<X;++j){
            a[i][j]=j;
            
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
    //  for(int i=0;i <10;++i){
        subsystem_single_uniprocessor();
    // }
    
    printf("\n");
    for(int i=0;i<Y;++i){
        for(int j=0;j<X;++j){
            printf("%f, ", c[i][j]);
        }
        printf("\n");
    }
    return 0;
}