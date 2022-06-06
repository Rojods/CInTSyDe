package template.templateInterface

import forsyde.io.java.core.Vertex

interface ActorTemplate {
	def String create(Vertex vertex)	
	def String savePath()
}
