#include <stdio.h>
#include "./single/tile/subsystem.h"
#include "./single/datatype/datatype_definition.h"
#include "./single/circular_fifo_lib/circular_fifo_lib.h"

int main(){
   init_subsystem();

   extern circular_fifo_UInt32 fifo_s_in;
   for(int i=0;i<5;++i){
       int a[4];
       a[0]=4*i+1;
       a[1]=4*i+2;
       a[2]=4*i+3;
       a[3]=4*i+4;
       write_fifo_UInt32(&fifo_s_in,a,4);
   }
    subsystem();
    return 0;
}