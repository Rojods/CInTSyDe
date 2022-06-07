package demo
import forsyde.io.java.drivers.ForSyDeModelHandler


import generator.Generator

import template.baremetal_single.*
import utils.Query
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.VertexAcessor.VertexPortDirection

class test5 {
	def static void main(String[] args) {
		val path = "example1-2cores.fiodl"
		//val root = "generateCode/c/single2"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)		
		
		var sdfchannel = model.queryVertex("gxsig").get()
		//var type = Query. findSDFChannelDataType(model,sdfchannel)
		
		var sdfactor = model.queryVertex("Gx").get()
		
		var b =SDFActor.safeCast(sdfactor).get().getCombFunctionsPort(model)
		println(b)
		
		var c=VertexAcessor.getMultipleNamedPort(model,sdfactor,"combFunctions",VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE,  VertexPortDirection.OUTGOING)
		for(Vertex t:c){
			println(t.getIdentifier())
		}
		
		println(c)
		
		println("")
	}
}