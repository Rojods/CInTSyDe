package processingModule

import java.util.Set

import template.templateInterface.SubsystemTemplate

import utils.Save
import java.util.HashSet
import generator.Generator
import processingModule.Schedule
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule
import java.util.stream.Collectors

class SubsystemMultiprocessorModule implements ModuleInterface {
	Set<SubsystemTemplate> templates

	new() {
		templates = new HashSet
	}

	override create() {
//		Generator.multiProcessorSchedules.stream().forEach([schedule|process(schedule)])
		var tiles=Generator.model.vertexSet().stream().filter([v| GenericProcessingModule.conforms(v) ])
									.collect(Collectors.toSet())
		tiles.stream().forEach([tile|process(tile)])
	}

	def process(Vertex tile) {
		templates.stream().forEach( [ t |

				//println("================="+tile.getIdentifier())
				Save.save(t.create(tile),Generator.root +t.savePath());


		])
	}

	def add(SubsystemTemplate t) {
		templates.add(t)
	}
}
