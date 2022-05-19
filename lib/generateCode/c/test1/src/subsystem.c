#include "../inc/subsystem.h"
#include "../inc/sdfcomb_vertex_b.h"
#include "../inc/sdfcomb_vertex_a.h"
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
		printf("%s\n","enter vertex_a");
			actor_vertex_a();
		printf("%s\n","enter vertex_b");
			actor_vertex_b();
}

