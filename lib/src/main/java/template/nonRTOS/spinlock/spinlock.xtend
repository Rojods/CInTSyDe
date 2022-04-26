package template.nonRTOS.spinlock

import utils.Save

import template.Template
import generator.generator
class spinlock implements Template{
	override create() {
		
		Save.save(generator.root+"/inc/spinlock.h",inc());
		Save.save(generator.root+"/src/spinlock.c",src());
	}
	
	def String inc(){
		'''
			#ifndef SPINLOCK_H_
			#define SPINLOCK_H_
			typedef struct{
			volatile	int flag;
			}spinlock;
			
			void spinlock_get(spinlock* lock);
			void spinlock_release(spinlock* lock);
			#endif
		'''
		
	}
	def String src(){
		
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
}

