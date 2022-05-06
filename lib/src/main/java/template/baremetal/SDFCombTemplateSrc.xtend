package template.baremetal

import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import template.templateInterface.ActorTemplate

import utils.Query
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFCombViewer
import forsyde.io.java.typed.viewers.impl.Executable
import forsyde.io.java.typed.viewers.typing.TypedOperation
import java.util.TreeSet
import java.util.List
import java.util.Collections
import forsyde.io.java.typed.viewers.moc.MoCElem
import forsyde.io.java.typed.viewers.impl.DataBlock
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrc implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet

	override create(Vertex actor) {
		val model = Generator.model
		// implActorSet = (new SDFCombViewer(actor)).getCombFunctionsPort(Generator.model)
		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions",
			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		this.inputSDFChannelSet = Query.findInputSDFChannels(model, actor)
		this.outputSDFChannelSet = Query.findOutputSDFChannels(model, actor)
		var Set<Vertex> datablock
		datablock = Query.findAllExternalDataBlocks(model, SDFComb.safeCast(actor).get())
		'''
			«var name = actor.getIdentifier()»
			/* Includes-------------------------- */
			#include "../inc/config.h"
			#include "../inc/datatype_definition.h"
			#include "../inc/circular_fifo_lib.h"
			#include "../inc/sdfcomb_«name».h"
			#include "../inc/extern_datablock.h"
			
			
			/*
			========================================
				Declare Extern Channal Variables
			========================================
			*/
			«extern()»
			/*
			========================================
				Actor Function
			========================================
			*/			
			void actor_«name»(){
			«««				#if defined(TESTING)
«««				«IF name=="GrayScale"»
«««				HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,1);
«««				«ELSEIF name=="getPx" »
«««				HAL_GPIO_WritePin(GPIOC,GPIO_PIN_9,1);
«««				«ELSEIF name=="Gx" »
«««				HAL_GPIO_WritePin(GPIOC,GPIO_PIN_8,1);
«««				«ELSEIF name=="Gy" »
«««				HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,1);
«««				«ELSEIF name=="Abs" »
«««				HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,1);
«««				«ENDIF»
«««				#endif
				
				/* Initilize Memory */
				«initMemory(model,actor)»
			/* Read From Input Port  */
			«IF Generator.TESTING==1&&Generator.PC==1»
				printf("%s\n","read");
			«ENDIF»
			int ret=0;
			«read(model,actor)»
			
			«IF datablock.size()!=0»
				/* Get lock of outside system channel */
				«FOR data:datablock»
					#if «data.getIdentifier().toUpperCase()»_BLOCKING==1
					extern spinlock spinlock_«data.getIdentifier()»;
					spinlock_get(&spinlock_«data.getIdentifier()»);
					#endif
				«ENDFOR»
			«ENDIF»
			
			/* Inline Code           */
			«IF Generator.TESTING==1&&Generator.PC==1»
				printf("%s\n","inline code");
			«ENDIF»
			«getInlineCode()»
			
				/* Write To Output Ports */
				«IF Generator.TESTING==1&&Generator.PC==1»
					printf("%s\n","write");
				«ENDIF»
				«write(actor)»
				«FOR data : datablock»
					#if «data.getIdentifier().toUpperCase()»_BLOCKING==1
					spinlock_release(&spinlock_«data.getIdentifier()»);
					#endif
				«ENDFOR»
				«IF Generator.TESTING==1&&Generator.NUCLEO==1»
				«IF name=="GrayScale"»
					HAL_Delay(1000);
					HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,0);
				«ELSEIF name=="getPx" »
					HAL_Delay(1000);
					HAL_GPIO_WritePin(GPIOC,GPIO_PIN_9,0);					
				«ELSEIF name=="Gx" »
					HAL_Delay(1000);
					HAL_GPIO_WritePin(GPIOC,GPIO_PIN_8,0);	
				«ELSEIF name=="Gy" »
					HAL_Delay(1000);
					HAL_GPIO_WritePin(GPIOC,GPIO_PIN_6,0);		
				«ELSEIF name=="Abs" »	
					HAL_Delay(1000);
					HAL_GPIO_WritePin(GPIOA,GPIO_PIN_5,0);
				«ENDIF»	
			«ENDIF»
			}
		'''
	}

	def String extern() {
		var Set<Vertex> record = new HashSet
		'''
			/* Input FIFO */
			«FOR sdf : this.inputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !record.contains(sdf)»
					extern circular_fifo_«Query.findSDFChannelDataType(Generator.model,sdf)» fifo_«sdf.getIdentifier()»;
					extern spinlock spinlock_«sdf.getIdentifier()»;
					«var tmp=record.add(sdf)»
				«ENDIF»
			«ENDFOR»
			/* Output FIFO */
			«FOR sdf : this.outputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !record.contains(sdf)»
					extern circular_fifo_«Query.findSDFChannelDataType(Generator.model,sdf)» fifo_«sdf.getIdentifier()»;
					extern spinlock spinlock_«sdf.getIdentifier()»;
					«var tmp=record.add(sdf)»
				«ENDIF»
			«ENDFOR»		
		'''
	}

	def String initMemory(ForSyDeSystemGraph model, Vertex actor) {

		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""

		for (String impl : impls) {
			var actorimpl = model.queryVertex(impl).get()
			var Set<String> ports = new HashSet
			ports.addAll(Query.findImplInputPorts(actorimpl))
			ports.addAll(Query.findImplOutputPorts(actorimpl))
			for (String port : ports) {
				var datatype = Query.findImplPortDataType(model, actorimpl, port)
				if (!variableNameRecord.contains(port)) {
					if (Query.isSystemChannel(model, actorimpl, port) === null) {
						ret += '''
							«datatype» «port»; 
						'''
					} else {
						ret += '''
							«datatype» «port» = «Query.isSystemChannel(model,actorimpl,port)»; 
						'''
					}
					variableNameRecord.add(port)
				}
			}
		}
		return ret
	}

	def String read(ForSyDeSystemGraph model, Vertex actor) {
		var Set<Vertex> impls = SDFComb.safeCast(actor).get().getCombFunctionsPort(model).stream().map([ e |
			e.getViewedVertex()
		]).collect(Collectors.toSet())
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (Vertex impl : impls) {

			var inputPorts = TypedOperation.safeCast(impl).get().getInputPorts()
			for (String port : inputPorts) {
				if (!variableNameRecord.contains(port) && Query.isSystemChannel(model, impl, port) === null) {
					// var datatype = Query.findImplPortDataType(model, impl, port)
					var actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, impl, port)
					var sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					var datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get())
					// println(actor.getIdentifier()+" -->   "+actorPortName)
					// println(actor)
					var consumption = SDFComb.safeCast(actor).get().getConsumption().get(actorPortName)

					if (consumption == 1) {
						ret += '''
							#if «sdfchannelName.toUpperCase()»_BLOCKING==0
							ret=read_non_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»);
							if(ret==-1){
								printf("fifo_«sdfchannelName» read error\n");
							}
							
							#else
							read_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»,&spinlock_«sdfchannelName»);
							#endif
							
						'''
					} else {
						ret += '''
							for(int i=0;i<«consumption»;++i){
								
								#if «sdfchannelName.toUpperCase()»_BLOCKING==0
								ret=read_non_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»[i]);
								if(ret==-1){
									printf("fifo_«sdfchannelName» read error\n");
								}
								#else
								read_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»[i],&spinlock_«sdfchannelName»);
								#endif
							}
							
						'''
					}
					variableNameRecord.add(port)
				}
			}

		}
		return ret;
	}

	def String write(Vertex actor) {
		var model = Generator.model
		var impls = Query.findCombFuntionVertex(model, actor)
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (String impl : impls) {
			var actorimpl = Query.findVertexByName(model, impl)
			var outputPortSet = Query.findImplOutputPorts(actorimpl)
			for (String outport : outputPortSet) {

				if (!variableNameRecord.contains(outport)) {
					// var datatype = Query.findImplPortDataType(model, actorimpl, outport)
					var actorPortName = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, outport)
					var sdfchannelName = Query.findOutputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					var datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get())
					try {
						var production = SDFComb.enforce(actor).getProduction().get(actorPortName)
						if (production == 1) {
							ret += '''
								#if «sdfchannelName.toUpperCase()»_BLOCKING==0
								write_non_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»);
								#else
								write_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»,&spinlock_«sdfchannelName»);
								#endif
														
							'''
						} else {
							ret += '''
								for(int i=0;i<«production»;++i){
									#if «sdfchannelName.toUpperCase()»_BLOCKING==0
									write_non_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»[i]);
									#else
									write_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»[i],&spinlock_«sdfchannelName»);
									#endif
								}
								
							'''
						}
						variableNameRecord.add(outport)
					} catch (Exception e) {
						println("In actor " + actor.getIdentifier() + " port " + outport + " no production")
						return "error " + outport + ";"
					}

				}
			}
		}
		return ret
	}

	private def String getInlineCode() {

		'''
			«FOR impl : implActorSet SEPARATOR "" AFTER ""»
				/* in combFunction «impl.getIdentifier()» */
				«Query.getInlineCode(impl)»
			«ENDFOR»		
		'''

	}

	def String actorParameter(ForSyDeSystemGraph model, Vertex actor) {
		var Set<String> portSet = new HashSet(actor.getPorts())
		portSet.remove("combFunctions")
		portSet.remove("combinator")
		var List<String> portList = new ArrayList(portSet)
		Collections.sort(portList)

		var String ret = ""
		for (var int i = 0; i < portList.size(); i = i + 1) {
			if (i == 0) {
				ret += "   " + portList.get(i) + "_port"
			} else {
				ret += "," + portList.get(i) + "_port"
			}
		}

		return ret

	}

}
