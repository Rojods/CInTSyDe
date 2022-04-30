#include <stdio.h>
typedef double DoubleType;
typedef DoubleType Array6OfDoubleType[6];
typedef  DoubleType*  ArrayXOfDoubleType;
typedef ArrayXOfDoubleType* ArrayXOfArrayXDoubleType;
#define MONTH 12
void foo(double b, DoubleType* ptr){
    printf("--->%p\n",ptr);
    *ptr=b;

}

void f(const int * p){

}

void f2(int* p){

}
void f3(int a,int b,int arr[a][b]){

}

int main(){
    int zippo[4][2]={{2,4}, {6,8}, {1,3}, {5, 7}};
    printf("%p\n", zippo[0]);
    printf("%p\n", zippo[1]);

    int * p2;
    p2= *(zippo+2);
    f3(4,2,zippo);
    // int (*p)[2];
    // p=zippo;
    // printf("%p   , %p\n",p,p+1);
    // printf("%p\n",*p);
    // const int power[8]={1,2,3,4,5,6,7,8};
    // printf("%d\n",sizeof power);
    // f(power);
    // //f2(power);
    // int (*p)[2];
    // const int* pc1 = power;
    // //int * const pc2 = power;
    // Array6OfDoubleType a;
    // // for(int i=0;i<6;++i){
    // //     a[i] = i;   
    // // }

    // // for(int i=0;i<6;++i){
    // //     printf("%f\n",*(a+i));  
    // // }
    // for(int i=0;i<6;++i){
    //     DoubleType* p=a;
    //     //      (((innerType*)memory)+i)
    //     foo(i,  (a+i) );
    // }
    // printf("=========\n");
    // for(int i=0;i<6;++i){
    //     // DoubleType* p=a;
    //     // printf("%f\n",p[i]);
        
    //     printf("%f\n",a[i]);
    // }
    return 0;
}


typedef struct {
    int* buf;
    int size;
    int front;
    int rear;
}channel_int;