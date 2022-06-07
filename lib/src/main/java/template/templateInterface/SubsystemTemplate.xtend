package template.templateInterface

import processingModule.Schedule

interface SubsystemTemplate {
	def String create(Schedule s)
	def String  savePath()
}
