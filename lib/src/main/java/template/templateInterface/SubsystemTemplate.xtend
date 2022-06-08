package template.templateInterface

import processingModule.Schedule
import forsyde.io.java.core.Vertex

interface SubsystemTemplate {
	//def String create(Schedule s)
	def String create(Vertex tile)
	def String  savePath()
}
