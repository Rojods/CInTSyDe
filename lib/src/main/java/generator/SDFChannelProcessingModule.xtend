package generator

import java.util.Set
import template.templateInterface.ChannelTemplate
import java.util.HashSet
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import utils.Save
import utils.Name
import forsyde.io.java.core.ForSyDeSystemGraph
import utils.Query
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexTrait

class SDFChannelProcessingModule implements ModuleInterface {
	Set<ChannelTemplate> templates

	new() {
		templates = new HashSet
	}

	override create() {
		Generator.model.vertexSet().stream().filter([v|SDFChannel::conforms(v)]).forEach([v|process(v)])
	}

	def void process(Vertex v) {
		templates.stream().forEach( [ t |
			save(Generator.model,v,t)
		])
	}

	def void add(ChannelTemplate t) {
		templates.add(t)
	}

	def save(ForSyDeSystemGraph model, Vertex v, ChannelTemplate t) {
		if (Generator.platform != 2) {

			Save.save(t.create(v), Generator.root + "/tile/" + t.savePath());
		} else {
			if (Query.isOnOneCoreChannel(model, v)) {

				var Vertex consumer = VertexAcessor.getNamedPort(model, v, "consumer", VertexTrait.MOC_SDF_SDFACTOR).
					orElse(null)
				if (consumer !== null) {
					var Vertex tile = Query.findTile(model, consumer)
					Save.save(t.create(v), Generator.root + "/" + tile.getIdentifier() + t.savePath());
					println(Generator.root + "/" + tile.getIdentifier() + t.savePath())
				} else {
					var Vertex producer = VertexAcessor.getNamedPort(model, v, "producer",
						VertexTrait.MOC_SDF_SDFACTOR).orElse(null)
					if (producer !== null) {
						var Vertex tile2 = Query.findTile(model, producer)
						Save.save(t.create(v), Generator.root + "/" + tile2.getIdentifier() + t.savePath());
					}
				}

			} else {
				var Vertex consumer = VertexAcessor.getNamedPort(model, v, "consumer", VertexTrait.MOC_SDF_SDFACTOR).
					orElse(null)
				if (consumer !== null) {
					var Vertex tile = Query.findTile(Generator.model, consumer)
					Save.save(t.create(v), Generator.root+"/" + tile.getIdentifier() + t.savePath());
				}

				var Vertex producer = VertexAcessor.getNamedPort(model, v, "producer", VertexTrait.MOC_SDF_SDFACTOR).
					orElse(null)
				if (producer !== null) {
					var Vertex tile2 = Query.findTile(model, producer)
					Save.save(t.create(v), Generator.root + "/" + tile2.getIdentifier() + t.savePath());
				}

			}
		}

	}

}
