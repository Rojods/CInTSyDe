#ifndef EXTERNAL_DATABLOCK_H_
#define EXTERNAL_DATABLOCK_H_

/*
=====================================================
		Blocking or Non Blokcing Read Write
=====================================================
*/
#define SYSTEM_IMG_SOURCE_GLOBAL_BLOCKING 0
#define SYSTEM_IMG_SINK_GLOBAL_BLOCKING 0
#define DIMX_GLOBAL_BLOCKING 0
#define OUTPUTIMAGE_BLOCKING 0
#define DIMY_GLOBAL_BLOCKING 0
#define INPUTIMAGE_BLOCKING 0

/*
=====================================================
		Counting Smeaphore
=====================================================
*/				
#define count_sem_SYSTEM_IMG_SOURCE_GLOBAL_max 1
#define count_sem_SYSTEM_IMG_SOURCE_GLOBAL_init 0

#define count_sem_SYSTEM_IMG_SINK_GLOBAL_max 1
#define count_sem_SYSTEM_IMG_SINK_GLOBAL_init 0

#define count_sem_DIMX_GLOBAL_max 1
#define count_sem_DIMX_GLOBAL_init 0

#define count_sem_OUTPUTIMAGE_max 1
#define count_sem_OUTPUTIMAGE_init 0

#define count_sem_DIMY_GLOBAL_max 1
#define count_sem_DIMY_GLOBAL_init 0

#define count_sem_INPUTIMAGE_max 1
#define count_sem_INPUTIMAGE_init 0

#endif 

