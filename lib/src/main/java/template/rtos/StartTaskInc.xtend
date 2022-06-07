package template.rtos

import template.templateInterface.InitTemplate



class StartTaskInc implements InitTemplate{
	
	override create() {
		'''
		#ifndef  SUBSYSTEM_H_
		#define  SUBSYSTEM_H_
		void init_subsystem();
		#endif
		'''
	}
	
	override savePath() {
		return "/start_task.h"
	}
	
}