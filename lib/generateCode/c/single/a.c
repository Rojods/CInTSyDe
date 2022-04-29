#include <stdio.h>
typedef double DoubleType;
typedef DoubleType Array6OfDoubleType[6];
typedef  DoubleType*  ArrayXOfDoubleType;
typedef ArrayXOfDoubleType* ArrayXOfArrayXDoubleType;

void foo(double b, DoubleType* ptr){
    printf("--->%p\n",ptr);
    *ptr=b;

}

int main(){


    Array6OfDoubleType a;
    // for(int i=0;i<6;++i){
    //     a[i] = i;   
    // }

    // for(int i=0;i<6;++i){
    //     printf("%f\n",*(a+i));  
    // }
    for(int i=0;i<6;++i){
        DoubleType* p=a;
        //      (((innerType*)memory)+i)
        foo(i,  (a+i) );
    }
    printf("=========\n");
    for(int i=0;i<6;++i){
        // DoubleType* p=a;
        // printf("%f\n",p[i]);
        
        printf("%f\n",a[i]);
    }
    return 0;
}


typedef struct {
    int* buf;
    int size;
    int front;
    int rear;
}channel_int;