package template.baremetal

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import template.templateInterface.InitTemplate

@FileTypeAnno(type=FileType.C_SOURCE)
class SpinLockTemplateSrc implements InitTemplate{
	
	override create() {
		'''
		#include "../inc/spinlock.h"
		
		#if defined(WINDOWS)
		#define ATOMIC_TEST_AND_SET   _InterlockedExchange
		#endif
		 
		#if defined(LINUX)|| defined(ARM)
		#define ATOMIC_TEST_AND_SET  __sync_lock_test_and_set
		#endif
		
«««		#if defined(CORTEX_M4)
«««		
«««		__asm uint32 __test_and_set_on_cortex_m4(uint32* addr,int value){
«««			push {r4-r5}
«««			
«««			ldrex  r4 ,[r0]
«««			strex r5, r1, [r0]
«««			mov r0,r4
«««			pop {r4-r5}
«««			mov pc,lr
«««			
«««		}
«««		#endif
		
«««		#define ATOMIC_TEST_AND_SET __test_and_set_on_cortex_m4
«««		#endif
		void spinlock_get(spinlock* lock){
			while(ATOMIC_TEST_AND_SET(&lock->flag,1)==1){
				
			}
		}
		void spinlock_release(spinlock* lock){
			ATOMIC_TEST_AND_SET(&lock->flag,0);
		}	
		'''
	}
	
	override getFileName() {
		return "spinlock"
	}
}