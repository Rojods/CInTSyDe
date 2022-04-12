package factory.c

import factory.Factory

import forsyde.io.java.core.ForSyDeSystemGraph
import strategy.Strategy
import java.io.File
import java.util.Set
import java.util.HashSet

class CFactory implements Factory{
	String name
	String saveRootDir=null	
	Set<Strategy> set=new HashSet
	
	
	
	new(String saveRootDir){
		this.saveRootDir=saveRootDir
		
		var include=saveRootDir+"/include"
		var src=saveRootDir+"/source"
		
		var f1 = new File(include)
		if(!f1.exists()){
			f1.mkdirs()		
		}	
		
		f1 = new File(src)
		if(!f1.exists()){
			f1.mkdirs()		
		}
	}
	
	override create() {
		
		for(Strategy s:set){
			s.setSaveRoot(saveRootDir)
			s.create()
		}
					
	}	
	override add(Strategy strategy) {
		set.add(strategy)
	}	

	

	
}