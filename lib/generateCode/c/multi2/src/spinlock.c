#include "../inc/spinlock.h"

	#if defined(WINDOWS)
	#define ATOMIC_TEST_AND_SET   _InterlockedExchange
	#endif
	 
	#if defined(LINUX)|| defined(ARM)
	#define ATOMIC_TEST_AND_SET  __sync_lock_test_and_set
	#endif
	
	void spinlock_get(spinlock* lock){
		while(ATOMIC_TEST_AND_SET(&lock->flag,1)==1){
			
		}
	}
	void spinlock_release(spinlock* lock){
		ATOMIC_TEST_AND_SET(&lock->flag,0);
	}	

