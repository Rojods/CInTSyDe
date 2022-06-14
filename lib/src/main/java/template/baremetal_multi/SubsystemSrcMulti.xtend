package template.baremetal_multi

import template.templateInterface.SubsystemTemplate
import utils.Name
import generator.Generator
import forsyde.io.java.typed.viewers.values.IntegerValue
import java.util.stream.Collectors
import utils.Query
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.HashMap
import java.util.ArrayList
import processingModule.Schedule
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.EdgeTrait
import java.util.List
import java.util.TreeSet
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.Trait

class SubsystemSrcMulti implements SubsystemTemplate {

	Vertex tile
	Vertex order
	
	override create(Vertex tile) {
		var List<Vertex> slots =new ArrayList
		this.tile = tile
		var model = Generator.model
		this.order=findOrder(model,tile)
		var ports = new TreeSet(order.getPorts())
		ports.remove("contained")
		for (String portname : ports) {
			var actor = VertexAcessor.getNamedPort(
										Generator.model,
										order,
										portname,
										VertexTrait.MOC_SDF_SDFACTOR
										).orElse(null)
			slots.add(actor)			
		}
		
		println(tile.getIdentifier())
		println(order.getIdentifier())
		'''
			#include "subsystem_«tile.getIdentifier()».h"
			#include "../datatype/datatype_definition.h"
			#include "../circular_fifo_lib/circular_fifo_lib.h"
			#include <cheap_s.h>
			
			
			void subsystem_«tile.getIdentifier()»(){
				while(1){
			«FOR actor : slots SEPARATOR "" AFTER ""»
				«var tmp =1»
					«IF actor!==null»
						actor_«Name.name(actor)»();
					«ENDIF»
			«ENDFOR»
				}
			}	
			
			int init_«tile.getIdentifier()»(){
				
			
				xil_printf("tile initialization starts\n");
	
			/* extern */
				«extern(model,tile)»
			
			/* Create the channels*/
				«createChannel(model,tile)»
			
			/* SDF Delays */
				«sdfdelay(model,tile)»
			
			/*wait util all other fifos are created*/
				«wait(model,tile)»
			
				xil_printf("tile initialization ends\n");				
				return 0;	
			}
		'''
	}

	def String extern(ForSyDeSystemGraph model, Vertex tile) {
		var String ret = "";
		var channelSet = Generator.model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(
			Collectors.toSet())

		var integerValues = model.vertexSet().stream().filter([v|IntegerValue.conforms(v)]).map([ v |
			IntegerValue.safeCast(v).get()
		]).collect(Collectors.toSet())

		for (IntegerValue value : integerValues) {
			ret += '''
				extern int «value.getIdentifier()»;
			'''
		}

		for (Vertex channel : channelSet) {
			var channelname = channel.getIdentifier()
			var pair = Query.findSrcAndSnkTileOfChannel(model, channel)
			var type = Query.findSDFChannelDataType(Generator.model, channel)
			if (pair.src !== null && pair.snk !== null) {
				if (pair.src == tile && pair.snk == tile) {
					ret += '''
						«IF Generator.fifoType==1»	
							extern «type» buffer_«channelname»[];
							extern int buffer_«channelname»_size;
							extern circular_fifo_«type» fifo_«channelname»;
						«ENDIF»
						«IF Generator.fifoType==2»	
							extern «type» buffer_«channelname»[];
							extern size_t buffer_«channelname»_size;
							extern circular_fifo fifo_«channelname»;
						«ENDIF»						
					'''
				}

				if (pair.src == tile && pair.snk !== tile) {
					ret += '''
						extern cheap fifo_admin_«channelname»;
						extern volatile «type» * const fifo_data_«channelname»;
						extern size_t  buffer_«channelname»_size;
						extern size_t token_«channelname»_size;
					'''
				}

				if (pair.src !== tile && pair.snk == tile) {
					ret += '''
						extern cheap fifo_admin_«channelname»;
						extern volatile «type» * const fifo_data_«channelname»;
						extern size_t buffer_«channelname»_size;
						extern size_t token_«channelname»_size;
					'''
				}

			} else {
				if (pair.src === null) {
					if (pair.snk == tile) {
						ret += '''
							«IF Generator.fifoType==1»	
								extern «type» buffer_«channelname»[];
								extern int buffer_«channelname»_size;
								extern circular_fifo_«type» fifo_«channelname»;
							«ENDIF»
							«IF Generator.fifoType==2»	
								extern «type» buffer_«channelname»[];
								extern size_t buffer_«channelname»_size;
								extern circular_fifo fifo_«channelname»;
							«ENDIF»							
							'''
					}
				}

				if (pair.snk === null) {
					if (pair.src == tile) {
						ret += '''
							«IF Generator.fifoType==1»	
								extern «type» buffer_«channelname»[];
								extern int buffer_«channelname»_size;
								extern circular_fifo_«type» fifo_«channelname»;
							«ENDIF»
							«IF Generator.fifoType==2»	
								extern «type» buffer_«channelname»[];
								extern size_t buffer_«channelname»_size;
								extern circular_fifo fifo_«channelname»;
							«ENDIF»	
							'''
					}
				}

			}

		}
		return ret
	}

	def String createChannel(ForSyDeSystemGraph model, Vertex tile) {
		var String ret = "";
		var channelSet = Generator.model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(
			Collectors.toSet())
		for (Vertex channel : channelSet) {
			var channelname = channel.getIdentifier()
			var pair = Query.findSrcAndSnkTileOfChannel(model, channel)
			if (pair.src !== null && pair.snk !== null) {
				if (pair.src == tile && pair.snk == tile) {
					// fifo src and snk on this tile
					ret += '''
						«IF Generator.fifoType==1»	
							init_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size);
						«ENDIF»
						«IF Generator.fifoType==2»
							init_fifo(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size, sizeof(«Query.findSDFChannelDataType(Generator.model,channel)»));
						«ENDIF»						
					'''
				}

				if (pair.src == tile && pair.snk !== tile) {
					ret += '''
						if (cheap_init_r (fifo_admin_«channelname», (void *) fifo_data_«channelname», buffer_«channelname»_size, token_«channelname»_size) == NULL) {
							//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
							return 1;
						}				
					'''
				}

			} else {
				// one of the src and snk is null
				if (pair.src === null) {
					if (pair.snk == tile) {
						ret += '''
							«IF Generator.fifoType==1»	
								init_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size);
							«ENDIF»
							«IF Generator.fifoType==2»
								init_fifo(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size, sizeof(«Query.findSDFChannelDataType(Generator.model,channel)»));
							«ENDIF»							
						'''
					}
				}

				if (pair.snk === null) {
					if (pair.src == tile) {
						ret += '''
							«IF Generator.fifoType==1»	
								init_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size);
							«ENDIF»
							«IF Generator.fifoType==2»
								init_fifo(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size, sizeof(«Query.findSDFChannelDataType(Generator.model,channel)»));
							«ENDIF»	
						'''
					}
				}

			}
		}

		return ret;
	}

	def String sdfdelay(ForSyDeSystemGraph model, Vertex tile) {
		var String ret = ""
		var channelSet = Generator.model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(
			Collectors.toSet())
		for (Vertex channel : channelSet) {
			var sdfchannelName = channel.getIdentifier()
			var pair = Query.findSrcAndSnkTileOfChannel(model, channel)
			var sdfchannel = SDFChannel.safeCast(channel).get()
			var datatype = Query.findSDFChannelDataType(model, channel)

			if (pair.src !== null && pair.snk !== null) {
				if (pair.src == tile && pair.snk == tile) {
					// fifo src and snk on this tile
					if (sdfchannel.getNumOfInitialTokens() !== null && sdfchannel.getNumOfInitialTokens() > 0) {
						var ordering = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").
							unwrap() as HashMap<String, Integer>)
						if (ordering.size() > 0) {
							var initList = help(ordering)
							for (String valueName : initList) {
								ret += '''
									«IF Generator.fifoType==1»	
										write_fifo_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfchannel.getIdentifier()»,&«valueName»,1);
									«ENDIF»
									«IF Generator.fifoType==2»
										write_fifo(&fifo_«sdfchannel.getIdentifier()»,&«valueName»,1);
									«ENDIF»										
								'''
							}
						}
					}
				}

				if (pair.src == tile && pair.snk !== tile) {
					if (sdfchannel.getNumOfInitialTokens() !== null && sdfchannel.getNumOfInitialTokens() > 0) {
						var ordering = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").
							unwrap() as HashMap<String, Integer>)
						if (ordering.size() > 0) {
							var initList = help(ordering)
							ret += '''
								volatile «datatype» *tmp_ptrs[«initList.size()»];
								while ((cheap_claim_spaces (fifo_admin_«sdfchannelName», (volatile void **) &tmp_ptrs[0], «initList.size()»)) < «initList.size()»)
									cheap_release_all_claimed_spaces (fifo_admin_«sdfchannelName»);							
							'''
							var i = 0
							for (String valueName : initList) {
								ret += '''
									*tmp_ptrs[«i»]=«valueName»;
								'''
								i = i + 1
							}

							ret += '''
								cheap_release_tokens (fifo_admin_«sdfchannelName», «initList.size()»);
							'''
						}
					}
				}

			// add 			
			}

		}

		return ret
	}

	def String wait(ForSyDeSystemGraph model, Vertex tile) {
		var String ret = ""
		var channelSet = Generator.model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(
			Collectors.toSet())
		for (Vertex channel : channelSet) {
			var pair = Query.findSrcAndSnkTileOfChannel(model, channel)

			if (pair.snk == tile && pair.src !== null && pair.src !== tile) {
				ret += '''
					while (cheap_get_buffer_capacity (fifo_admin_«channel.getIdentifier()») == 0); 
				'''
			}

		}

		return ret;
	}

	override savePath() {
		return "/" + tile.getIdentifier() + "/subsystem_" + tile.getIdentifier() + ".c"
	}

	def help(HashMap<String, Integer> delays) {

//		var a = new ArrayList<String>(ordering.size())
//		for (String k : ordering.keySet()) {
//			//////////////set?
//			a.add(ordering.get(k), k)
//		}
		var numOfInitialToken = delays.size()
		var delayValueList = new ArrayList<String>()
		for (var int i = 0; i < numOfInitialToken; i = i + 1) {
			delayValueList.add("")
		}

		for (String k : delays.keySet()) {
			//println("->" + delays.get(k))
			delayValueList.set(delays.get(k), k)
		}
		return delayValueList
	// return a
	}
	
	def findOrder(ForSyDeSystemGraph model, Vertex tile){
		var Vertex order = null
		if (order === null) {
			order = helpfindOrder(VertexTrait.PLATFORM_RUNTIME_FIXEDPRIORITYSCHEDULER)
		}

		if (order === null) {
			order = helpfindOrder(VertexTrait.PLATFORM_RUNTIME_ROUNDROBINSCHEDULER)
		}

		if (order === null) {
			order = helpfindOrder(VertexTrait.PLATFORM_RUNTIME_STATICCYCLICSCHEDULER)
		}
		if (order === null) {
			order = helpfindOrder(VertexTrait.PLATFORM_RUNTIME_STATICCYCLICSCHEDULER)
		}
		if (order === null) {
			order = helpfindOrder(VertexTrait.PLATFORM_RUNTIME_TIMETRIGGEREDSCHEDULER)

		}	
		
		
		return order
			
	}
	def helpfindOrder(Trait a){
		return VertexAcessor.getNamedPort(
			Generator.model,
			tile,
			"execution",
			a
		).orElse(null)		
	}
}
