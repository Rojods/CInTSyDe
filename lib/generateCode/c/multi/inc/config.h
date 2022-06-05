#ifndef CONFIG_H_
#define CONFIG_H_
#include <cheap_s.h>
/*
*************************************************************
	Config Channel Block or Non Block Read Write
*************************************************************
*/
#define ABSY_BLOCKING 0
#define ABSX_BLOCKING 0
#define GRAYSCALETOGETPX_BLOCKING 0
#define GRAYSCALEX_BLOCKING 0
#define GRAYSCALEY_BLOCKING 0

#define GRAYSCALETOABS_ADDR 0x80020000
#define GYSIG_ADDR 0x80020000
#define GXSIG_ADDR 0x80020000
#define ABSYSIG_ADDR 0x80020000
#define ABSXSIG_ADDR 0x80020000
#endif		
