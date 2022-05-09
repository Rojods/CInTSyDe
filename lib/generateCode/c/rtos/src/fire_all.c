#include "../inc/fire_all.h"
#include "../inc/config.h"
#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"		
void fire_all(){
	#if FREERTOS==1
	vTaskStartScheduler();
	#endif
}
