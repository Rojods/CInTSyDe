package generator
import forsyde.io.java.core.Vertex

import java.util.List
import java.util.ArrayList
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.Trait
import java.util.TreeSet
import java.util.Set
import java.util.HashSet
import generator.Generator
import forsyde.io.java.core.VertexAcessor.VertexPortDirection

class Schedule {
	public var Vertex tile
	public var Vertex order = null
	public List<Vertex> slots =new ArrayList
	public Set<Vertex> outgoingchannels = new HashSet
	public Set<Vertex> incomingchannels = new HashSet
	new() {
		
	}

	new(forsyde.io.java.core.Vertex tile) {
		
		this.tile = tile
		if (order === null) {
			order = help1(VertexTrait.PLATFORM_RUNTIME_FIXEDPRIORITYSCHEDULER)
		}

		if (order === null) {
			order = help1(VertexTrait.PLATFORM_RUNTIME_ROUNDROBINSCHEDULER)
		}

		if (order === null) {
			order = help1(VertexTrait.PLATFORM_RUNTIME_STATICCYCLICSCHEDULER)
		}
		if (order === null) {
			order = help1(VertexTrait.PLATFORM_RUNTIME_STATICCYCLICSCHEDULER)
		}
		if (order === null) {
			order = help1(VertexTrait.PLATFORM_RUNTIME_TIMETRIGGEREDSCHEDULER)

		}
		if (order !== null) {
			var ports = new TreeSet(order.getPorts())
			ports.remove("contained")
			for (String portname : ports) {
				var actor = VertexAcessor.getNamedPort(
					Generator.model,
					order,
					portname,
					VertexTrait.MOC_SDF_SDFACTOR
				).orElse(null)
				slots.add(actor)
			}
		}

		for (Vertex actor : slots) {
			if (actor !== null) {
				actor.getPorts().stream().forEach([ p |
					if (p != "Combinator" && p != "CombFunction") {
						outgoingchannels.add(
							VertexAcessor.getNamedPort(Generator.model, actor, p, VertexTrait.MOC_SDF_SDFCHANNEL,VertexPortDirection.OUTGOING)
										.orElse(null))
					}
				])
			}
		}
		
		if(outgoingchannels.contains(null)){
			outgoingchannels.remove(null)
		}
		
		for (Vertex actor : slots) {
			if (actor !== null) {
				actor.getPorts().stream().forEach([ p |
					if (p != "Combinator" && p != "CombFunction") {
//						incomingchannels.add(
//							VertexAcessor.getNamedPort(Generator.model, actor, p, VertexTrait.MOC_SDF_SDFCHANNEL,VertexPortDirection.INCOMING)
//										.orElse(null))
										
						var channel = 	VertexAcessor.getNamedPort(Generator.model, actor, p, VertexTrait.MOC_SDF_SDFCHANNEL,VertexPortDirection.INCOMING)
										.orElse(null)
						if(!outgoingchannels.contains(channel)){
							incomingchannels.add(channel)
						}
					}
				])
			}
		}
		
		if(incomingchannels.contains(null)){
			incomingchannels.remove(null)
		}		
	}
/**
 * find order
 */
	private def help1(Trait a) {
		return VertexAcessor.getNamedPort(
			Generator.model,
			tile,
			"execution",
			a
		).orElse(null)
	}

	def print() {
		println("tile: " + tile.getIdentifier())
		if(order === null) println("order null") else println("order : " + order.getIdentifier())
		for (Vertex v : slots) {
			if(v === null) println("null") else println(v.getIdentifier())
		}
	}	
}