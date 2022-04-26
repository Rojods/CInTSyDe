package template.nonRTOS.actor

import forsyde.io.java.core.Vertex

import java.util.Set
import utils.Name

import static extension utils.Query.getSDFChannelName
import static extension utils.Query.getChannelName
import utils.Global

class actorhelp {
	def static String actorParameterSignature(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts){
		'''
			«FOR port: inputPorts  SEPARATOR "," AFTER " "»
				circularFIFO_«vertex.getSDFChannelName(port,Global.model)»* channel_«port»_ptr, const size_t «port»_rate
			«ENDFOR»
			«IF inputPorts.size()==0 »
				«FOR port: outputPorts  SEPARATOR "," AFTER " "»
					circularFIFO_«vertex.getSDFChannelName(port,Global.model)»* channel_«port»_ptr,const size_t «port»_rate
				«ENDFOR»
			«ELSE»
				«FOR port: outputPorts  SEPARATOR " " AFTER " "»
					,circularFIFO_«vertex.getSDFChannelName(port,Global.model)»* channel_«port»_ptr,const size_t «port»_rate
				«ENDFOR»				
			«ENDIF»
		'''
	}
	
	def static String func_pointer(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts) {
		'''
			void (*func_«Name.name(vertex)»_combinator)(
				«FOR port: inputPorts  SEPARATOR "," AFTER " "»
					token_«vertex.getSDFChannelName(port,Global.model)» *, size_t 
				«ENDFOR»
				«IF inputPorts.size()==0 »
					«FOR port: outputPorts  SEPARATOR "," AFTER " "»
						token_«vertex.getSDFChannelName(port,Global.model)» *, size_t 
					«ENDFOR»
				«ELSE»
					«FOR port: outputPorts  SEPARATOR " " AFTER " "»
						,token_«vertex.getSDFChannelName(port,Global.model)» *, size_t 
					«ENDFOR»
			«ENDIF»
			)			
		'''
	}	
	def static String funcParameter(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts) {
		'''
		«FOR port: inputPorts  SEPARATOR "," AFTER " "»«port»,«port»_rate«ENDFOR»«IF inputPorts.size()==0 »«FOR port: outputPorts  SEPARATOR "," AFTER " "»«port»,«port»_rate«ENDFOR»«ELSE»«FOR port: outputPorts  SEPARATOR " " AFTER " "», «port»,«port»_rate«ENDFOR»«ENDIF»'''
	}
	
	def static funcParameterSignature(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts) {
		
		'''
				
			«FOR port: inputPorts  SEPARATOR "," AFTER " "»
				token_«vertex.getSDFChannelName(port,Global.model)» «port»[] , const size_t «port»_rate
			«ENDFOR»
			«IF inputPorts.size()==0 »
				«FOR port: outputPorts  SEPARATOR "," AFTER " "»
					token_«vertex.getSDFChannelName(port,Global.model)» «port»[],const size_t «port»_rate
				«ENDFOR»
			«ELSE»
				«FOR port: outputPorts  SEPARATOR " " AFTER " "»
					,token_«vertex.getSDFChannelName(port,Global.model)»  «port»[],const size_t «port»_rate
				«ENDFOR»				
			«ENDIF»
		'''
	}

}
