package template.nonRTOS.subsystem

import template.Template
import utils.Global
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.stream.Collectors
import utils.Name
import generator.generator
import utils.Save

class configInc implements Template{
	
	override create() {
		Save.save(path(),inc())
	}
	def String inc(){
		var sdfchannels=Global.model.vertexSet()
							 .stream()
							 .filter([v|SDFChannel::conforms(v)])
							 .map([v|SDFChannel::enforce(v)])
							 .collect(Collectors.toSet())
		'''
		#ifndef CONFIG_H_
		#define CONFIG_H_
		«FOR cha:sdfchannels SEPARATOR "" AFTER"\n"»
		#define «Name.name(cha).toUpperCase()»_NONBLOCKING 0
		«ENDFOR»
		
		#endif
		'''
	}
	private def path(){
		return generator.root+"/inc/config.h"
	}	
}