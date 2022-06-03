/*
*******************************************************
    This file contains the function definition for
    token types: «FOR typeVertex : typeVertexSet SEPARATOR ", " AFTER ""»«typeVertex.getIdentifier()»«ENDFOR»
    For each token type, there are five functions:
    init_channel_typeName(...)
    read_non_blocking_typeName(...)
    read_blocking_typeName(...)
    write_non_blocking_typeName(...)
    write_blocking_typeName(...)
*******************************************************
*/
#ifndef CIRCULAR_FIFO_{{typeName}}_H_
#define CIRCULAR_FIFO_{{typeName}}_H_

/*
************************************************************
inject the typedefs required for the fifo
************************************************************
*/
#include "{{pathToTypeDefs}}"

/*
************************************************************
actual FIFO typedefs
************************************************************
*/

typedef struct {
    {{typeName}}*  buffer;
    size_t front;
    size_t rear;
    size_t size;
} fifo_{{typeName}};

void ref_init(fifo_{{typeName}}* fifo_ptr, {{typeName}}* buffer, size_t size) {
    fifo_ptr->front=0;
    fifo_ptr->rear=0;
    fifo_ptr->buffer=buffer;
    fifo_ptr->size=size;
};

void read_non_blocking(const fifo_{{typeName}}& fifo_ptr, {{typeName}}* dst) {
    if(fifo_ptr.front==fifo_ptr.rear){
     //empty
     return;
    }else{
        *dst = fifo_ptr.buffer[fifo_ptr.front];
        fifo_ptr.front = (fifo_ptr.front+1) % fifo_ptr.size;
        return;
    }
};

void write_non_blocking(const fifo_{{typeName}}& fifo_ptr, {{typeName}} src) {
    if ( (fifo_ptr.rear+1) % fifo_ptr-.size == fifo_ptr.front) {
        //full
        return ;
     }else{
        fifo_ptr.buffer[fifo_ptr.rear] = src;
        fifo_ptr.rear = (fifo_ptr.rear+1) % fifo_ptr.size;
        return;
    }
};

#endif
