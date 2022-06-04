package template.uniprocessor.actor

import fileAnnotation.FileType

import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import forsyde.io.java.typed.viewers.typing.TypedDataBlockViewer
import forsyde.io.java.typed.viewers.typing.TypedOperation
import generator.Generator
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.stream.Collectors
import template.templateInterface.ActorTemplate
import utils.Query

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFActorSrc implements ActorTemplate {
	Set<Vertex> implActorSet
	Set<Vertex> inputSDFChannelSet
	Set<Vertex> outputSDFChannelSet

	override create(Vertex actor) {
		val model = Generator.model

//		implActorSet = VertexAcessor.getMultipleNamedPort(Generator.model, actor, "combFunctions",
//			VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexPortDirection.OUTGOING)
		implActorSet = SDFActor.safeCast(actor).get().getCombFunctionsPort(model).stream().map([v|v.getViewedVertex()]).
			collect(Collectors.toSet())
		this.inputSDFChannelSet = Query.findInputSDFChannels(model, actor)
		this.outputSDFChannelSet = Query.findOutputSDFChannels(model, actor)
		var Set<Vertex> datablock
		datablock = Query.findAllExternalDataBlocks(model, SDFActor.safeCast(actor).get())
		var ret1 =new StringBuilder
		var ret2 =new StringBuilder
		initMemory(model,actor,ret1,ret2)
		'''
			«var name = actor.getIdentifier()»
			/* Includes */
			#include "../inc/config.h"
			#include "../inc/datatype_definition.h"
			#include "../inc/circular_fifo_lib.h"
			#include "../inc/sdfcomb_«name».h"
			
			/*
			========================================
			Declare Extern Channal Variables
			========================================
			*/
			«extern()»
			/*
			========================================
				Declare Extern Global Variables
			========================================
			*/			
				«FOR d : datablock»
			extern «findType(model,d)» «d.getIdentifier()»;
				«ENDFOR»
				
			/*
			========================================
				Actor Function
			========================================
			*/	
			/*  initialize memory*/
«««						«initMemory(model,actor)»	
			«ret1»	
			void actor_«name»(){
			
«««			/*  initialize memory*/
«««			«initMemory(model,actor)»
				«ret2»
				/* Read From Input Port  */
				int ret=0;
				«read(model,actor)»
			
				
				/* Inline Code           */
				«getInlineCode()»
				
				/* Write To Output Ports */
				«write(actor)»
			
			}
		'''
	}

	def String extern() {
		var Set<Vertex> record = new HashSet
		'''
			/* Input FIFO */
			«FOR sdf : this.inputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !record.contains(sdf)»
					«IF Generator.fifoType==1»
					extern circular_fifo_«Query.findSDFChannelDataType(Generator.model,sdf)» fifo_«sdf.getIdentifier()»;
					extern spinlock spinlock_«sdf.getIdentifier()»;	
					«ENDIF»		
					«IF Generator.fifoType==2»
					extern circular_fifo fifo_«sdf.getIdentifier()»;
					extern spinlock spinlock_«sdf.getIdentifier()»;	
					«ENDIF»
					«var tmp=record.add(sdf)»
					
				«ENDIF»
			«ENDFOR»
			/* Output FIFO */
			«FOR sdf : this.outputSDFChannelSet SEPARATOR "" AFTER ""»
				«IF !record.contains(sdf)»
					«IF Generator.fifoType==1»
					extern circular_fifo_«Query.findSDFChannelDataType(Generator.model,sdf)» fifo_«sdf.getIdentifier()»;
					extern spinlock spinlock_«sdf.getIdentifier()»;
					«ENDIF»
					«IF Generator.fifoType==2»
					extern circular_fifo fifo_«sdf.getIdentifier()»;
					extern spinlock spinlock_«sdf.getIdentifier()»;
					«ENDIF»
					«var tmp=record.add(sdf)»
				«ENDIF»
			«ENDFOR»		
		'''
	}

	def String initMemory(ForSyDeSystemGraph model, Vertex actor, StringBuilder ret1, StringBuilder ret2) {

		var impls = Query.findCombFuntionVertex(model, actor)

		var Set<String> variableNameRecord = new HashSet
		var String ret = ""

		for (String impl : impls) {
			println("-->" + impl)
			var actorimpl = model.queryVertex(impl).get()
			var Set<String> ports = new HashSet

			if (Query.findImplInputPorts(actorimpl) !== null) {
				ports.addAll(Query.findImplInputPorts(actorimpl))
			}

			if (Query.findImplOutputPorts(actorimpl) !== null) {
				ports.addAll(Query.findImplOutputPorts(actorimpl))
			}
			//println("-->" + ports)
			if (ports.isEmpty()) {
				ret+='''
				The inputPorts or outputPorts Property is not specified in «impl»
				'''
			} else {
				for (String port : ports) {
					
					var datatype = Query.findImplPortDataType(model, actorimpl, port)
					if (!variableNameRecord.contains(port)) {
						if (Query.isSystemChannel(model, actorimpl, port) === null) {
							ret1.append( '''
							static	«datatype» «port»; 
							''')
							
						} else {
							ret2.append( '''
								«datatype» «port» = «Query.isSystemChannel(model,actorimpl,port)»; 
							''')
						}
						variableNameRecord.add(port)
					}
				}
			}

		}
		return ret
	}

	def String read(ForSyDeSystemGraph model, Vertex actor) {
		var Set<Vertex> impls = SDFActor.safeCast(actor).get().getCombFunctionsPort(model).stream().map([ e |
			e.getViewedVertex()
		]).collect(Collectors.toSet())
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (Vertex impl : impls) {
			println(impl)
			var inputPorts = TypedOperation.safeCast(impl).get().getInputPorts()
			//var inputPorts = Query.findImplInputPorts(impl)	
			if (inputPorts !== null) {
				for (String port : inputPorts) {
					//println("port-->"+ port)
					if (!variableNameRecord.contains(port) && Query.isSystemChannel(model, impl, port) === null) {

						var actorPortName = Query.findActorPortConnectedToImplInputPort(model, actor, impl, port)
						var sdfchannelName = Query.findInputSDFChannelConnectedToActorPort(model, actor, actorPortName)
						var datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get())

						var consumption = SDFActor.safeCast(actor).get().getConsumption().get(actorPortName)
						if (consumption === null) {
							ret += '''
								Consumption in «actor.getIdentifier()» Not Specified!
							'''
						} else if (consumption == 1) {
							ret += '''
								«IF Generator.fifoType==1»
								#if «sdfchannelName.toUpperCase()»_BLOCKING==0
								ret=read_non_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»);
								if(ret==-1){
									//printf("fifo_«sdfchannelName» read error\n");
								}
								
								#else
								read_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»,&spinlock_«sdfchannelName»);
								#endif
								«ENDIF»
								«IF Generator.fifoType==2»
								{
									void* tmp_addr;
									read_non_blocking(&fifo_«sdfchannelName»,&tmp_addr);
									«port»= *((«datatype» *)tmp_addr);
								}
								«ENDIF»
								
							'''
						} else {
							ret += '''
								for(int i=0;i<«consumption»;++i){
									«IF Generator.fifoType==1»
									#if «sdfchannelName.toUpperCase()»_BLOCKING==0
									ret=read_non_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»[i]);
									if(ret==-1){
										printf("fifo_«sdfchannelName» read error\n");
									}
									#else
									read_blocking_«datatype»(&fifo_«sdfchannelName»,&«port»[i],&spinlock_«sdfchannelName»);
									#endif
									«ENDIF»
									«IF Generator.fifoType==2»
									void* tmp_addr;
									read_non_blocking(&fifo_«sdfchannelName»,&tmp_addr);
									«port»[i]= *((«datatype» *)tmp_addr);
									«ENDIF»
								}
								
							'''
						}
						variableNameRecord.add(port)
					}
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
					var String actorPortName
					var String sdfchannelName
					var String datatype

					actorPortName = Query.findActorPortConnectedToImplOutputPort(model, actor, actorimpl, outport)
					sdfchannelName = Query.findOutputSDFChannelConnectedToActorPort(model, actor, actorPortName)
					try {
						datatype = Query.findSDFChannelDataType(model, model.queryVertex(sdfchannelName).get())
					} catch (Exception e) {
						datatype = "<" + sdfchannelName + " DataType Not Found>"
					}

					var production = SDFActor.enforce(actor).getProduction().get(actorPortName)
					if (production === null) {
						ret += '''
							Production in «actor.getIdentifier()» Is Not Specified!
						'''
					} else if (production == 1) {
						ret += '''
							«IF Generator.fifoType==1»
							#if «sdfchannelName.toUpperCase()»_BLOCKING==0
							write_non_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»);
							#else
							write_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»,&spinlock_«sdfchannelName»);
							#endif
							«ENDIF»
							«IF Generator.fifoType==2»
							write_non_blocking(&fifo_«sdfchannelName»,(void*)&«outport»);
							«ENDIF»						
						'''
					} else {
						ret += '''
							for(int i=0;i<«production»;++i){
								«IF Generator.fifoType==1»
								#if «sdfchannelName.toUpperCase()»_BLOCKING==0
								write_non_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»[i]);
								#else
								write_blocking_«datatype»(&fifo_«sdfchannelName»,«outport»[i],&spinlock_«sdfchannelName»);
								#endif
								«ENDIF»
								«IF Generator.fifoType==2»
								write_non_blocking(&fifo_«sdfchannelName»,(void*)&«outport»[i]);		
								«ENDIF»							
							}
							
						'''
					}

					variableNameRecord.add(outport)

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

	private def String findType(ForSyDeSystemGraph model, Vertex datablock) {
		var a = (new TypedDataBlockViewer(datablock)).getDataTypePort(model)
		if (!a.isPresent()) {
			return null
		}
		return a.get().getIdentifier()
	}
}
