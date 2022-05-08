#include "../inc/extern_datablock.h"
#include "FreeRTOS.h"
#include "semphr.h"
/*
=====================================================
		Blocking or Non Blokcing Read Write
=====================================================
*/
SemaphoreHandle_t datablock_sem_system_img_source_global;
SemaphoreHandle_t datablock_sem_system_img_sink_global;
SemaphoreHandle_t datablock_sem_dimX_global;
SemaphoreHandle_t datablock_sem_outputImage;
SemaphoreHandle_t datablock_sem_dimY_global;
SemaphoreHandle_t datablock_sem_inputImage;

/*
=====================================================
		Counting Smeaphore
=====================================================
*/				
SemaphoreHandle_t count_sem_system_img_source_global;
SemaphoreHandle_t count_sem_system_img_sink_global;
SemaphoreHandle_t count_sem_dimX_global;
SemaphoreHandle_t count_sem_outputImage;
SemaphoreHandle_t count_sem_dimY_global;
SemaphoreHandle_t count_sem_inputImage;
