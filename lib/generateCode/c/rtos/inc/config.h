#ifndef CONFIG_H_
#define CONFIG_H_
#define TESTING
#if defined(TESTING)
#include "main.h"
#endif

/*
************************************************
				Config
************************************************
*/
#define FREERTOS 1
#define UCOS_2  0
#define STARTTASK_STACKSIZE 2048
#define GX_STACKSIZE 2048
#define ABS_STACKSIZE 2048
#define GY_STACKSIZE 2048
#define GRAYSCALE_STACKSIZE 2048
#define GETPX_STACKSIZE 2048
#endif		
