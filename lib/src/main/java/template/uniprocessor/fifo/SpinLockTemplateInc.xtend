package template.uniprocessor.fifo

import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_INCLUDE)
class SpinLockTemplateInc implements InitTemplate{
	
	override create() {
		'''
			#ifndef SPINLOCK_H_
			#define SPINLOCK_H_
			
				#define ARM
				typedef struct{
				volatile	int flag;
				}spinlock;
				
				void spinlock_get(spinlock* lock);
				void spinlock_release(spinlock* lock);
			
			
			#endif
		'''
	}
	
	override savePath() {
		return "/circular_fifo_lib/spinlock.h"
	}
	
}
