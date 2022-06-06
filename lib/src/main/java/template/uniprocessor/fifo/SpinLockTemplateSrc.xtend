package template.uniprocessor.fifo

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_SOURCE)
class SpinLockTemplateSrc implements InitTemplate{
	
	override create() {
		'''
			#include "spinlock.h"
				#if defined(WINDOWS)
				#define ATOMIC_TEST_AND_SET   _InterlockedExchange
				#endif
				 
				#if defined(LINUX)|| defined(ARM)
				#define ATOMIC_TEST_AND_SET  __sync_lock_test_and_set
				#endif
				
				void spinlock_get(spinlock* lock){
				//	while(ATOMIC_TEST_AND_SET(&lock->flag,1)==1){
						
				//	}
				}
				void spinlock_release(spinlock* lock){
				//	ATOMIC_TEST_AND_SET(&lock->flag,0);
				}	
			
		'''
	}
	
	override savePath() {
		return "/circular_fifo_lib/spinlock.c"
	}
}
