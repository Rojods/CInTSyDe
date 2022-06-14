package processingModule

import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule
import generator.Generator
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors
import template.templateInterface.SubsystemTemplate
import utils.Save

class SubsystemMultiprocessorModule implements ModuleInterface {
	Set<SubsystemTemplate> templates

	new() {
		templates = new HashSet
	}

	override create() {
		var tiles = Generator.model.vertexSet().stream().filter([v|GenericProcessingModule.conforms(v)]).collect(
			Collectors.toSet())
		tiles.stream().forEach([tile|process(tile)])
	}

	def process(Vertex tile) {
		templates.stream().forEach( [ t |
			Save.save(t.create(tile), Generator.root + t.savePath());

		])
	}

	def add(SubsystemTemplate t) {
		templates.add(t)
	}
}
