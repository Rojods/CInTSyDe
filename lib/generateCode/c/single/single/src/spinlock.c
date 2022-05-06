#include "../inc/spinlock.h"
void spinlock_get(spinlock* lock){
	while(__sync_lock_test_and_set(&lock->flag,1)==1){
		
	}
}
void spinlock_release(spinlock* lock){
	__sync_lock_test_and_set(&lock->flag,0);
}	
