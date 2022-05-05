package template.baremetal.multiprocessor

import template.templateInterface.SubsystemTemplate
import generator.Schedule
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import utils.Name
import fileAnnotation.FileTypeAnno
import fileAnnotation.FileType

@FileTypeAnno(type=FileType.C_SOURCE)
class SubsystemTemplateSrcMulti  implements SubsystemTemplate{
	Schedule s
	override create(Schedule schedule) {
		this.s=schedule
		var tile=schedule.tile
		'''
		#include "../inc/subsystem_«s.tile.getIdentifier()».h"
		«FOR channel:schedule.channels SEPARATOR"" AFTER""»
			«var channelName=Name.name(channel)»
			«IF SDFChannel.conforms(channel)»
				extern circularFIFO_«channelName» channel_«channelName»;
				extern token_«channelName» arr_«channelName»[];
				extern int buffersize_«channelName»;
			«ELSE»
				extern circularFIFO_«channelName» channel_«channelName»;
			«ENDIF»
		«ENDFOR»	
		void subsystem_«tile.getIdentifier()»(){
«««			«FOR channel:schedule.channels  SEPARATOR "" AFTER "" »
«««				«var channelName=Name.name(channel)»
«««				«IF SDFChannel.conforms(channel)»
«««				init_circularFIFO_«channelName»(&channel_«channelName»,arr_«channelName»,buffersize_«channelName»);
«««				«ENDIF»
«««			«ENDFOR»			
«««			«subsystemHelp.sdfDelayHelpA(channels)»
			
//			while(1){
				«FOR actor:schedule.slots SEPARATOR "" AFTER "\n"»
				«IF actor!==null»
				actor_«Name.name(actor)»();
				«ENDIF»
				«ENDFOR»		
			
//			}	
			
		}	
		
		void init_«tile.getIdentifier()»(){
			«FOR channel:schedule.channels  SEPARATOR "" AFTER "" »
				«var channelName=Name.name(channel)»
				«IF SDFChannel.conforms(channel)»
				init_circularFIFO_«channelName»(&channel_«channelName»,arr_«channelName»,buffersize_«channelName»);
				«ENDIF»
			«ENDFOR»				
		}
		'''
	}	

	override getFileName() {
		return "subsystem_tile_"+s.tile.getIdentifier()
	}
	

	
}