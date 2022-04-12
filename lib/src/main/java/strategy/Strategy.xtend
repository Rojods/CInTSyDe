package strategy

import forsyde.io.java.core.Vertex
import forsyde.io.java.core.ForSyDeSystemGraph

interface Strategy {
	def void create()
	def void setSaveRoot(String root)

}