package template.baremetal

import template.templateInterface.ActorTemplate
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.VertexAcessor
import generator.Generator
import utils.Query
import utils.Name

class SDFCombTemplateSrc implements ActorTemplate {
	
	override create(Vertex vertex) {
		'''
		«var name = Name.name(vertex)»
		inline void actor_«name»(){
			//initilize the memory
			
			//read from the input port
			
			//inline code
			«Query.getInlineCode(vertex)»
			//write to the output port
		}
		'''
	}
	
}