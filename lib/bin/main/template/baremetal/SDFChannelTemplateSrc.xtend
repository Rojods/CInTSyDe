package template.baremetal

import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import template.templateInterface.ActorTemplate
import utils.Name
import utils.Query
import java.util.stream.Collectors
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait
import template.templateInterface.ChannelTemplate

@FileTypeAnno(type=FileType.C_SOURCE)
class SDFChannelTemplateSrc implements ChannelTemplate {

	override create(Vertex vertex) {

		'''
		
		'''
	}

}
