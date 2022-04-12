package strategy

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import java.util.Set
import static extension util.Query.getSDFChannelName
import util.Name

class SDFCombFather {
	protected ForSyDeSystemGraph model
	protected String root
	new(ForSyDeSystemGraph model){
		this.model=model
	}
	
	def String actorParameterSignature(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts){
		'''
			«FOR port: inputPorts  SEPARATOR "," AFTER " "»
				circularFIFO_«vertex.getSDFChannelName(port,model)» channel_«port», const size_t «port»_rate
			«ENDFOR»
			«IF inputPorts.size()==0 »
				«FOR port: outputPorts  SEPARATOR "," AFTER " "»
					circularFIFO_«vertex.getSDFChannelName(port,model)» channel_«port»,const size_t «port»_rate
				«ENDFOR»
			«ELSE»
				«FOR port: outputPorts  SEPARATOR " " AFTER " "»
					,circularFIFO_«vertex.getSDFChannelName(port,model)» channel_«port»,const size_t «port»_rate
				«ENDFOR»				
			«ENDIF»
			
			«IF inputPorts.size()==0&&outputPorts.size()==0 »
				«func_pointer(vertex,inputPorts,outputPorts)»
			«ELSE»
				,«func_pointer(vertex,inputPorts,outputPorts)»
			«ENDIF»
		'''
	}
	
	def String func_pointer(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts) {
		'''
			void (*func_«Name.name(vertex)»_combinator)(
				«FOR port: inputPorts  SEPARATOR "," AFTER " "»
					token_«vertex.getSDFChannelName(port,model)» *, size_t 
				«ENDFOR»
				«IF inputPorts.size()==0 »
					«FOR port: outputPorts  SEPARATOR "," AFTER " "»
						token_«vertex.getSDFChannelName(port,model)» *, size_t 
					«ENDFOR»
				«ELSE»
					«FOR port: outputPorts  SEPARATOR " " AFTER " "»
						,token_«vertex.getSDFChannelName(port,model)» *, size_t 
					«ENDFOR»
			«ENDIF»
			)			
		'''
	}	
	def String funcParameter(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts) {
		'''
		«FOR port: inputPorts  SEPARATOR "," AFTER " "»«port»,«port»_rate«ENDFOR»«IF inputPorts.size()==0 »«FOR port: outputPorts  SEPARATOR "," AFTER " "»«port»,«port»_rate«ENDFOR»«ELSE»«FOR port: outputPorts  SEPARATOR " " AFTER " "», «port»,«port»_rate«ENDFOR»«ENDIF»'''
	}
	
	def funcParameterSignature(Vertex vertex, Set<String> inputPorts, Set<String> outputPorts) {
		
		'''
				
			«FOR port: inputPorts  SEPARATOR "," AFTER " "»
				token_«vertex.getSDFChannelName(port,model)» «port»[] , const size_t «port»_rate
			«ENDFOR»
			«IF inputPorts.size()==0 »
				«FOR port: outputPorts  SEPARATOR "," AFTER " "»
					token_«vertex.getSDFChannelName(port,model)» «port»[],const size_t «port»_rate
				«ENDFOR»
			«ELSE»
				«FOR port: outputPorts  SEPARATOR " " AFTER " "»
					,token_«vertex.getSDFChannelName(port,model)»  «port»[],const size_t «port»_rate
				«ENDFOR»				
			«ENDIF»
		'''
	}	
}
