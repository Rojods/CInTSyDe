package template.baremetal_multi

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import generator.Schedule
import template.templateInterface.SubsystemTemplate
import utils.Name
import generator.Generator
import forsyde.io.java.typed.viewers.values.IntegerValue
import java.util.stream.Collectors
import utils.Query
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.HashMap
import java.util.ArrayList

@FileTypeAnno(type=FileType.C_SOURCE)
class SubsystemTemplateSrcMulti implements SubsystemTemplate {
	Schedule s

	override create(Schedule schedule) {
		this.s = schedule
		var tile = schedule.tile
		var model = Generator.model
		var integerValues = model.vertexSet().stream().filter([v|IntegerValue.conforms(v)]).map([ v |
			IntegerValue.safeCast(v).get()
		]).collect(Collectors.toSet())
		'''
			#include "../inc/subsystem_«s.tile.getIdentifier()».h"
			#include "../inc/datatype_definition.h"
			#include <cheap_s.h>
			void subsystem_«tile.getIdentifier()»(){
			«FOR actor : schedule.slots SEPARATOR "" AFTER ""»
				«var tmp =1»
					«IF actor!==null»
						actor_«Name.name(actor)»();
					«ENDIF»
			«ENDFOR»
			}	
			
			int init_«tile.getIdentifier()»(){
				xil_printf("tile initialization starts\n");
				«FOR value : integerValues»
				extern int «value.getIdentifier()»;
			«ENDFOR»	
			
			«FOR channel : schedule.outgoingchannels»
				«var sdfname=channel.getIdentifier()»
					«var type = Query.findSDFChannelDataType(Generator.model,channel)»
					«IF Query.isOnOneCoreChannel(model,channel)»
					/* extern sdfchannel «sdfname»*/
					extern «type» buffer_«sdfname»[];
					extern int buffer_«sdfname»_size;
					extern circular_fifo_«type» fifo_«sdfname»;
				«ELSE»
					extern cheap fifo_admin_«sdfname»;
					extern volatile «type» * const fifo_data_«sdfname»;
					extern unsigned int buffer_«sdfname»_size;
					extern unsigned int token_«sdfname»_size;
				«ENDIF»
			«ENDFOR»
			«FOR channel : schedule.incomingchannels»
				«var sdfname=channel.getIdentifier()»
				«var type = Query.findSDFChannelDataType(Generator.model,channel)»
					«IF Query.isOnOneCoreChannel(model,channel)»
					/* extern sdfchannel «sdfname»*/
					extern «type» buffer_«sdfname»[];
					extern int buffer_«sdfname»_size;
					extern circular_fifo_«type» fifo_«sdfname»;
				«ELSE»
					extern cheap fifo_admin_«sdfname»;
					extern volatile «type» * const fifo_data_«sdfname»;
					extern unsigned int buffer_«sdfname»_size;
					extern unsigned int token_«sdfname»_size;
				«ENDIF»
			«ENDFOR»	
			/* Create the channels*/
				«FOR channel : schedule.outgoingchannels»
					«var channelname=channel.getIdentifier()»
					«IF Query.isOnOneCoreChannel(model,channel)»
						init_channel_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«channelname»,buffer_«channelname»,buffer_«channelname»_size);
					«ELSE»
						
						if (cheap_init_r (fifo_admin_«channelname», (void *) fifo_data_«channelname», buffer_«channelname»_size, token_«channelname»_size) == NULL) {
							//xil_printf("%04u/%010u: cheap_init_r failed\n", (uint32_t)(t>>32),(uint32_t)t);
							return 1;
						}				
					«ENDIF»
				«ENDFOR»	
				
				/*Initialize the channel */
					«FOR channel : schedule.outgoingchannels»
			«var sdfchannel=SDFChannel.safeCast(channel).get()»
			«var sdfchannelName=channel.getIdentifier()»
			«var datatype=Query.findSDFChannelDataType(model,channel)»
			«IF sdfchannel.getNumOfInitialTokens()!==null&&sdfchannel.getNumOfInitialTokens()>0»
					«var ordering = (sdfchannel.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>) »
					
					«IF ordering.size()>0»		
					«var initList = help(ordering)»		
						«IF Query.isOnOneCoreChannel(model,channel)»
							«FOR valueName:initList»
								write_non_blocking_«Query.findSDFChannelDataType(Generator.model,channel)»(&fifo_«sdfchannel.getIdentifier()»,«valueName»);
							«ENDFOR»
						«ELSE»
							{
							
								volatile «datatype» *tmp_ptrs[«initList.size()»];
								while ((cheap_claim_spaces (fifo_admin_«sdfchannelName», (volatile void **) &tmp_ptrs[0], «initList.size()»)) < «initList.size()»)
								cheap_release_all_claimed_spaces (fifo_admin_«sdfchannelName»);
								
								«var i=0»
								«FOR value:initList»
									*tmp_ptrs[«i»]=«value»;
									«i=i+1»
								«ENDFOR»
								
								cheap_release_tokens (fifo_admin_«sdfchannelName», «initList.size()»);
							}		
							
							
							«ENDIF»
						«ENDIF»			
					«ENDIF»
				«ENDFOR»	
				
				/* wait util other channels are created*/
				«FOR channel : schedule.incomingchannels»
					while (cheap_get_buffer_capacity (fifo_admin_«channel.getIdentifier()») == 0); 
				«ENDFOR»	
				xil_printf("tile initialization ends\n");				
				return 0;	
			}
		'''
	}

	override getFileName() {
		return "subsystem_tile_" + s.tile.getIdentifier()
	}

	def help(HashMap<String, Integer> ordering) {

		var a = new ArrayList<String>(ordering.size())
		for (String k : ordering.keySet()) {

			a.add(ordering.get(k), k)
		}
		return a
	}
}
