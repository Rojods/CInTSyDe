#include "../inc/subsystem.h"
#include <stdio.h>
#include "../inc/sdfcomb_Gx.h"
#include "../inc/sdfcomb_Abs.h"
#include "../inc/sdfcomb_Gy.h"
#include "../inc/sdfcomb_GrayScale.h"
#include "../inc/sdfcomb_getPx.h"
/*
==============================================
	Extern Variables are decalred in the 
	subsystem_include_help.h
==============================================
*/
/*
==============================================
	Subsystem Function
==============================================
*/	
int fire_subsystem_single_uniprocessor(){
		printf("%s\n","enter GrayScale");
			actor_GrayScale();
		printf("%s\n","enter getPx");
			actor_getPx();
		printf("%s\n","enter Gx");
			actor_Gx();
		printf("%s\n","enter Gy");
			actor_Gy();
		printf("%s\n","enter Abs");
			actor_Abs();
}

