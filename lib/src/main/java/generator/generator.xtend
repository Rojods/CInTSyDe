package generator

import forsyde.io.java.core.ForSyDeSystemGraph
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.TreeMap
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import java.util.ArrayList
import processingModule.ModuleInterface
import processingModule.Schedule

class Generator {

	public static var String root = null

	public static var ForSyDeSystemGraph model
	public static var Set<Vertex> sdfchannelSet
	public static var Set<Vertex> sdfcombSet

	public static int fifoType=1
	public static int platform=0
	Set<ModuleInterface> modules = new HashSet

	new(ForSyDeSystemGraph model, String root) {
		Generator.root = root
		Generator.model = model
		
		Generator.sdfchannelSet = Generator.model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(
			Collectors.toSet())
		Generator.sdfcombSet = Generator.model.vertexSet().stream().filter([v|SDFActor.conforms(v)]).collect(
			Collectors.toSet())	
					

	}

	def create() {
		
		modules.stream().forEach([m|m.create()])

	}

	def add(ModuleInterface m) {
		modules.add(m)
	}

}
