package strategy.withoutRTOS

import strategy.Strategy
import util.Save

class SpinLock implements Strategy{
	var String root;
	override create() {
		
		Save.save(root+"/include/spinlock.h",inc());
		Save.save(root+"/source/spinlock.c",src());
	}
	
	override setSaveRoot(String root) {
		this.root=root;
	}
	def String inc(){
		'''
		#ifndef SPINLOCK_H_
		#define SPINLOCK_H_
		typedef struct{
			int flag;
		}spinlock;
		//void spinlock_init(spinlock* lock);
		void spinlock_get(spinlock* lock);
		void spinlock_release(spinlock* lock);
		#endif
		'''
		
	}
	def String src(){
		
		'''
		#include "../include/spinlock.h"
		//void spinlock_init(spinlock* lock){
		//	
		//}
		void spinlock_get(spinlock* lock){
			while(__sync_lock_test_and_set(&lock->flag,1)==1){
				
			}
		}
		void spinlock_release(spinlock* lock){
			__sync_lock_test_and_set(&lock->flag,0);
		}	
		'''
	}

	
	
}