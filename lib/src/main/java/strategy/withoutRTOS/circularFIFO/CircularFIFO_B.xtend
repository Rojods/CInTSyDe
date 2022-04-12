package strategy.withoutRTOS.circularFIFO

import strategy.Strategy
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import util.Save
import util.Name
import forsyde.io.java.core.Vertex

class CircularFIFO_B implements Strategy {
	ForSyDeSystemGraph model
	String root
	new(ForSyDeSystemGraph model){
		this.model=model
		//this.root = save
	}	
	
	

	def String inc(){
	
		'''
		#ifndef                   CHANNEL_H_
		#define                   CHANNEL_H_
		#include "spinlock.h"
			typedef struct __attrribute__((packed))
			{ 
				void* buffer;
				size_t front;
				size_t rear;
				size_t fifo_size;
				
			}FIFO_circular_<tokenSize>;		
		void init(FIFO_circular_<tokenSize>* fifo,void* buffer,size_t fifo_size);
		int read_<tokenSize>_block(FIFO_circular_<tokenSize>* src,void* dst,int tokenSize,spinlock* lock);			
				
		#endif		
		'''
	}
	def String src(Vertex vertex){
		'''
		#include "../include/channel.h"
		void init(FIFO_circular_<tokenSize>* fifo,void* buffer,size_t fifo_size){
			fifo->buffer=buffer;
			fifo->fifo_size=fifo_size+1;
			front=fifo->buffer;
			rear=fifo->buffer;
		}

		
		'''
	}
	
	override create() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override setSaveRoot(String root) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
}