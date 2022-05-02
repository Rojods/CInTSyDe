#include "../inc/circular_fifo_lib.h"
Array6OfDoubleType buffer_gysig[7];
circular_fifo_Array6OfDoubleType fifo_gysig;
spinlock spinlock_gysig={.flag=0};

