#include "../inc/circular_fifo_lib.h"
volatile Channel sig Not Connected To Any Actor! buffer_sig[2];
int channel_sig_size=1;
/*
	Because of circular fifo, the 
	buffer_size=channel_size+1
*/
int buffer_sig_size = 2;
circular_fifo_Channel sig Not Connected To Any Actor! fifo_sig;
spinlock spinlock_sig={.flag=0};

