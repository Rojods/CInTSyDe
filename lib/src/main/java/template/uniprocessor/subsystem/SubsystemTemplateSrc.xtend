package template.uniprocessor.subsystem

import template.templateInterface.SubsystemTemplate

import java.util.stream.Collectors
import generator.Generator
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.HashMap
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import utils.Query
import forsyde.io.java.typed.viewers.values.IntegerValue
import processingModule.Schedule
import java.util.ArrayList
import forsyde.io.java.core.Vertex
import java.util.TreeMap
import java.util.Set

class SubsystemTemplateSrc implements SubsystemTemplate {
	
	Set<Vertex> sdfactorSet
	
	TreeMap<Integer, Vertex>  uniprocessorSchedule
	
	override savePath() {
		return "/tile/subsystem.c"
	}

	override String create(Vertex tile) {
		var model = Generator.model
		this.sdfactorSet = model.vertexSet().stream().filter([v|SDFActor.conforms(v)]).collect(Collectors.toSet())
		var integerValues = model.vertexSet().stream().filter([v|IntegerValue.conforms(v)]).map([ v |
			IntegerValue.safeCast(v).get()
		]).collect(Collectors.toSet())
		
	
		createUniprocessorSchedule()		
		
		'''
			#include "subsystem.h"
			#include <stdio.h>
			«FOR v : sdfactorSet»
				#include "./sdfactor/sdfactor_«v.getIdentifier()».h"
			«ENDFOR»
			#include "../datatype/datatype_definition.h"
			#include "../circular_fifo_lib/circular_fifo_lib.h"
			/*
			==============================================
				Subsystem Function
			==============================================
			*/	
			int subsystem(){
				while(1){
					«FOR set : uniprocessorSchedule.entrySet() SEPARATOR "" AFTER ""»
					actor_«set.getValue().getIdentifier()»();
					«ENDFOR»						
				}

			}
			
			
				/*
				*********************************************************
				Initialize All the Channels
				Should be called before subsystem_single_uniprocessor()
				*********************************************************
				*/
			int init_subsystem(){
				/* Extern Variables */
				«FOR value : integerValues»
					extern int «value.getIdentifier()»;
				«ENDFOR»	
				
				«externChannel()»		
				
				/* initialize the channels*/
					«FOR channel : Generator.sdfchannelSet»
					«var sdfname=channel.getIdentifier()»
							«IF Generator.fifoType==1»	
						init_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfname»,buffer_«sdfname»,buffer_«sdfname»_size);
						«ENDIF»
						«IF Generator.fifoType==2»
							init_fifo(&fifo_«sdfname»,buffer_«sdfname»,buffer_«sdfname»_size, sizeof(«Query.findSDFChannelDataType(Generator.model,channel)»));
							«ENDIF»
						«ENDFOR»		
						
						«FOR channel : Generator.sdfchannelSet»
							«var sdfchannel=SDFChannel.safeCast(channel).get()»
							«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
							«var delayValueList=inithelp(sdfchannel) »
							«FOR delay:delayValueList»
								«IF Generator.fifoType==1»
									write_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfchannel.getIdentifier()»,&«delay»,1);
								«ENDIF»
								«IF Generator.fifoType==2»
									write_fifo(&fifo_«sdfchannel.getIdentifier()»,(void*)&«delay»,1);
								«ENDIF»								
							«ENDFOR»
							«ENDIF»
«««							«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
«««								«var b = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>) »
«««									«FOR k:b.keySet()»
«««										«IF Generator.fifoType==1»
«««										write_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfchannel.getIdentifier()»,&«k»,1);
«««										«ENDIF»
«««										«IF Generator.fifoType==2»
«««											write_fifo(&fifo_«sdfchannel.getIdentifier()»,(void*)&«k»,1);
«««										«ENDIF»	
«««									«ENDFOR»
«««							«ENDIF»
							
							
						«ENDFOR»
			return 0;
			}		
							
							
		'''
	}

	def inithelp(SDFChannel sdfchannel) {
		var numOfInitialToken = sdfchannel.getNumOfInitialTokens()
		var delays = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").
			unwrap() as HashMap<String, Integer>)

		var delayValueList = new ArrayList<String>()
		for(var int i=0;i<numOfInitialToken;i=i+1){
			delayValueList.add("")
		}

		
		for (String k : delays.keySet()) {
			println("->"+delays.get(k))
			delayValueList.set(delays.get(k), k)
		}
		return delayValueList
	}

	def String externChannel() {

		'''
			«FOR channel : Generator.sdfchannelSet»
				«var sdfname=channel.getIdentifier()»
				«var type = Query.findSDFChannelDataType(Generator.model,channel)»
				/* extern sdfchannel «sdfname»*/
				«IF Generator.fifoType==1»	
					extern «type» buffer_«sdfname»[];
					extern int buffer_«sdfname»_size;
					extern circular_fifo_«type» fifo_«sdfname»;
				«ENDIF»
				«IF Generator.fifoType==2»	
					extern «type» buffer_«sdfname»[];
					extern size_t buffer_«sdfname»_size;
					extern circular_fifo fifo_«sdfname»;
				«ENDIF»
			
			«ENDFOR»
		'''
	}
	
	
	def void createUniprocessorSchedule(){
		this.uniprocessorSchedule = new TreeMap
		//var Set<Integer> tmp = new HashSet;
		
		for(Vertex actor: this.sdfactorSet){
			
			var tmp=getFiringSlot(actor)
			for(var int i =0; i < tmp.size; i =i+1){
				this.uniprocessorSchedule.put(tmp.get(i),actor)
			}

		}

	}	
	private def ArrayList<Integer>  getFiringSlot(Vertex actor){
		var firingSlots=actor.getProperties().get("firingSlots")
		if(firingSlots!==null){
			
			var slot = firingSlots.unwrap() as ArrayList<Integer>
			//return slot.get(0)
			return slot;
		}
		return null;
	}
}
