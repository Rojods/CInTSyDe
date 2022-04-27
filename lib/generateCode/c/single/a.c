#include <stdio.h>
typedef double DoubleType;
typedef DoubleType Array6OfDoubleType[6];
typedef  DoubleType*  ArrayXOfDoubleType;
typedef ArrayXOfDoubleType* ArrayXOfArrayXDoubleType;
int main(){
    Array6OfDoubleType a;
    ArrayXOfDoubleType b=a;
    ArrayXOfArrayXDoubleType c = &b;

    return 0;
}
void actor_name(actorPort name){
    initilize the inline code memory

    read from actor port

    inline code

    write to actor port
}