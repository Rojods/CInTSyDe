#include "subsystem_tile0.h"
#include "../datatype/datatype_definition.h"
#include "../circular_fifo_lib/circular_fifo_lib.h"
#include <cheap_s.h>


void subsystem_tile0(){
	while(1){
	actor_p1();
	actor_p2();
	actor_p1();
	actor_p2();
	actor_p3();
	}
}	

int init_tile0(){
	

	xil_printf("tile initialization starts\n");
	
/* extern */
	extern int ZeroValue1;
	extern int ZeroValue2;
	extern cheap fifo_admin_s5;
	extern volatile UInt32 * const fifo_data_s5;
	extern size_t buffer_s5_size;
	extern size_t token_s5_size;
	extern UInt32 buffer_s6[];
	extern size_t buffer_s6_size;
	extern circular_fifo fifo_s6;
	extern UInt32 buffer_s_in[];
	extern size_t buffer_s_in_size;
	extern circular_fifo fifo_s_in;
	extern UInt32 buffer_s1[];
	extern size_t buffer_s1_size;
	extern circular_fifo fifo_s1;
	extern cheap fifo_admin_s2;
	extern volatile UInt32 * const fifo_data_s2;
	extern size_t  buffer_s2_size;
	extern size_t token_s2_size;
	extern UInt32 buffer_s3[];
	extern size_t buffer_s3_size;
	extern circular_fifo fifo_s3;

/* Create the channels*/
	init_fifo(&fifo_s6,buffer_s6,buffer_s6_size, sizeof(UInt32));
	init_fifo(&fifo_s_in,buffer_s_in,buffer_s_in_size, sizeof(UInt32));
	init_fifo(&fifo_s1,buffer_s1,buffer_s1_size, sizeof(UInt32));
	if (cheap_init_r (fifo_admin_s2, (void *) fifo_data_s2, buffer_s2_size, token_s2_size) == NULL) {
		//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
		return 1;
	}				
	init_fifo(&fifo_s3,buffer_s3,buffer_s3_size, sizeof(UInt32));

/* SDF Delays */
	write_fifo(&fifo_s6,&ZeroValue1,1);
	write_fifo(&fifo_s6,&ZeroValue2,1);

/*wait util all other fifos are created*/
	while (cheap_get_buffer_capacity (fifo_admin_s5) == 0); 

	xil_printf("tile initialization ends\n");				
	return 0;	
}
