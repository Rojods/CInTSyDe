package template.templateInterface

import generator.Schedule

interface SubsystemTemplate {
	def String create(Schedule s)
	def String  getFileName()
}
