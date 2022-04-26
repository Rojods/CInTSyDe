 package template.nonRTOS.subsystem

import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule
import generator.generator
import java.util.ArrayList
import java.util.HashSet
import java.util.Set
import java.util.TreeSet
import java.util.stream.Collectors
import template.Template
import utils.Global
import utils.Name
import utils.Save

class subsystemMultiprocessor implements Template{
	new(){

	}		
	override  create(){
		var tiles = Global.model.vertexSet().stream().filter([v|GenericProcessingModule.conforms(v)]).collect(Collectors.toSet())
		tiles.stream().forEach([t|help(getScheduler(t),t)])
	}
	
	
	
	
	def  help(Vertex scheduler,Vertex tile){
		var ports = new TreeSet(scheduler.getPorts())
		ports.remove("contained")
		var ArrayList<Vertex> firingSlots = new ArrayList<Vertex>(ports.size())
		
		var iter = ports.iterator()
		while(iter.hasNext()){
			var String slotPortName= iter.next()
			var optional_actor=VertexAcessor.getNamedPort(Global.model,scheduler,slotPortName,VertexTrait.MOC_SDF_SDFCOMB,VertexPortDirection.OUTGOING)
			if(optional_actor.isPresent()) firingSlots.add(optional_actor.get())
		}		
		
		
		
		var Set<Vertex> channels=new HashSet()
		for(Vertex actor:firingSlots){
			for(String port:actor.getPorts()){
				if(port!="combinator"&&port!="combFunction"){
					var channel = VertexAcessor.getNamedPort(Global.model,actor,port,VertexTrait.MOC_SDF_SDFCHANNEL,VertexPortDirection.BIDIRECTIONAL)
					if(channel.isPresent()){
						channels.add(channel.get())
					}
				}
			}
		}
		Save.save(generator.root+"/src/subsystem_"+tile.getIdentifier()+".c",src(tile,firingSlots,channels))
		Save.save(generator.root+"/inc/subsystem_"+tile.getIdentifier()+".h",inc(tile,firingSlots,channels))
	}
	
	
	def String src(Vertex tile,ArrayList<Vertex> firingSlots,Set<Vertex> channels){
		'''
		#include "../inc/subsystem_«Name.name(tile)».h"
		«FOR channel:channels SEPARATOR"\n" AFTER""»
	«var channelName=Name.name(channel)»
		extern circularFIFO_«channelName» channel_«channelName»;
		extern token_«channelName» arr_«channelName»[];
		extern int buffersize_«channelName»;
		«ENDFOR»
		
		void subsystem_«tile.getIdentifier()»(){
			«FOR channel:channels  SEPARATOR "" AFTER "" »
			«var channelName=Name.name(channel)»
			init_circularFIFO_«channelName»(&channel_«channelName»,arr_«channelName»,buffersize_«channelName»);
			«ENDFOR»			
			«subsystemHelp.sdfDelayHelpA(channels)»
			while(1){
				«FOR actor:firingSlots SEPARATOR "" AFTER "\n"»
				actor_«Name.name(actor)»(«subsystemHelp.actorParameter(actor)»);
				«ENDFOR»		
			
			}	
			
		}
		'''		
	}
	
	
	def String inc(Vertex tile,ArrayList<Vertex> firingSlots,Set<Vertex> channels){
		'''
		#ifndef SUBSYSTEM_«Name.name(tile).toUpperCase()»_H_
		#define SUBSYSTEM_«Name.name(tile).toUpperCase()»_H_
		#include<stdlib.h>
		#include <stdio.h>	
		«FOR actor:firingSlots SEPARATOR " " AFTER ""»
		#include "../inc/sdfcomb_«Name.name(actor)».h"
		«ENDFOR»
		«FOR channel:channels SEPARATOR" " AFTER""»
		#include "../inc/sdfchannel_«Name.name(channel)».h"
		«ENDFOR»
		
		void subsystem_«Name.name(tile)»();
		#endif			
		'''
	}
	
	
	
	def Vertex getScheduler(Vertex tile){
		var order = VertexAcessor.getNamedPort(Global.model,tile,"contained",VertexTrait.PLATFORM_RUNTIME_TIMETRIGGEREDSCHEDULER,VertexPortDirection.OUTGOING)
		var Vertex scheduler;
		if(order.isPresent()){
			scheduler = order.get()			
			return scheduler			
		}else{
			//processing module does not have scheduler
			return null
		}
		
	}	
	
	


}
