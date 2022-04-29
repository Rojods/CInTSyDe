package template.baremetal

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_SOURCE)
class SpinLockTemplateSrc implements InitTemplate{
	
	override create() {
		'''
		#include "../inc/spinlock.h"
		void spinlock_get(spinlock* lock){
			while(__sync_lock_test_and_set(&lock->flag,1)==1){
				
			}
		}
		void spinlock_release(spinlock* lock){
			__sync_lock_test_and_set(&lock->flag,0);
		}	
		'''
	}
	
	override getFileName() {
		return "spinlock"
	}
}