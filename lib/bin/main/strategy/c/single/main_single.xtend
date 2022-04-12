package strategy.c.single

import strategy.Strategy

import forsyde.io.java.core.ForSyDeSystemGraph
import util.Save
/**
 * 
 */
class main_single implements Strategy{
	ForSyDeSystemGraph model
	String root
	String system = "default"
	new(ForSyDeSystemGraph model){
		this.model=model
		//this.root = save
	}
	override create() {
		Save.save(root+"/main_"+system+".c",createSource())

	}
	
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createSource(){
		'''
		#include<stdlib.h>
		#include <stdio.h>
		#include "./include/system_«system».h"
		
		
		
		int main(){
			system_«system»();
		}
		'''
	}
	def setSystemName(String s){
		this.system=s
	}
}