package demo

import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexProperty
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.drivers.ForSyDeModelHandler
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait

/**
 * create fiodl file
 */
class test2 {
	def static void main(String[] args) {
		var ForSyDeSystemGraph model = new ForSyDeSystemGraph
///////////////////////////////////////////////////////////
		val ports = #{"aa", "combFunctions"}
		val properties = #{
			"firingSlots" -> VertexProperty.create(#[0]),
			"production" -> VertexProperty.create(#{"aa" -> 1})
		}
		var a = new Vertex("vertex_a", ports, properties)
		a.addTraits(VertexTrait.MOC_SDF_SDFCOMB, VertexTrait.DECISION_SDF_PASSEDSDFCOMB)
		model.addVertex(a)
////////////////////////////////////////////////////////////////////
		val ports2 = #{"bb", "combFunctions"}
		val properties2 = #{
			"firingSlots" -> VertexProperty.create(#[1]),
			"consumption" -> VertexProperty.create(#{"bb" -> 1})
		}
		var a2 = new Vertex("vertex_b", ports2, properties2)
		a2.addTraits(VertexTrait.MOC_SDF_SDFCOMB, VertexTrait.DECISION_SDF_PASSEDSDFCOMB)
		model.addVertex(a2)
//////////////////////////////////////////////////////////////////////
		val ports3 = #{"producer", "consumer"}
		val properties3 = #{
			"maximumTokens" -> VertexProperty.create(1)
		}

		var a3 = new Vertex("sig", ports3, properties3)
		a3.addTraits(VertexTrait.MOC_SDF_SDFCHANNEL, VertexTrait.DECISION_SDF_BOUNDEDSDFCHANNEL)
		a3.addTraits(VertexTrait.IMPL_TOKENIZABLEDATABLOCK)

		model.addVertex(a3)
/////////////////////////////////////////////////////////////////////////
		
//		var EdgeInfo e = new EdgeInfo("vertex_a","sig","aa","producer")
//		model.add(e)

		model.connect(a,a3,"aa","producer",EdgeTrait.MOC_SDF_SDFDATAEDGE)
		model.connect(a2,a3,"bb","consumer",EdgeTrait.MOC_SDF_SDFDATAEDGE)

///////////////////////////////////////////////////////////////////////////
		(new ForSyDeModelHandler).writeModel(model, "a.fiodl")
		println(model)
		println("end!")
	}
}
