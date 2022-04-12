package strategy.c.single


import strategy.Strategy
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import util.Name
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import util.Save

class SystemHeader_single implements Strategy {
	ForSyDeSystemGraph model
	String root
	String name = "default"
	new(ForSyDeSystemGraph model){
		this.model=model
	}	
	
	override create() {
		var str = createHeader()
		Save.save(root+"/include/system_"+name+".h",str)
	}
	override setSaveRoot(String root) {
		this.root=root
	}
	
	def String createHeader(){
		var sdfChannelSet = model.vertexSet()
								.stream()
								.filter([v|SDFChannel.conforms(v)])
								.collect(Collectors.toSet())
		var sdfCombSet = model.vertexSet()
								.stream()
								.filter([v|SDFComb.conforms(v)])
								.collect(Collectors.toSet())
		'''
			#ifndef SYSTEM_«name.toUpperCase()»_H_
			#define SYSTEM_«name.toUpperCase()»_H_
			
			«FOR channel :sdfChannelSet SEPARATOR "\n" AFTER"\n"  »
				#include "sdfchannel_«Name.name(channel)».h"
			«ENDFOR»
			
			«FOR sdf :sdfCombSet SEPARATOR "\n" AFTER"\n"  »
				#include "sdfcomb_«Name.name(sdf)».h"
			«ENDFOR»	
			
			//system input and system output
			void system_«name»();
			
			#endif
		'''
	}

	def setSystemName(String name){
		this.name=name
	}	

	
}
