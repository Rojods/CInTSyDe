package demo
import utils.Load

import org.jgrapht.alg.shortestpath.BFSShortestPath
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.EdgeInfo
import org.jgrapht.GraphPath
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import utils.Query
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.stream.Collectors
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import template.baremetal.SDFCombTemplateSrc
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannelViewer
import forsyde.io.java.typed.viewers.decision.sdf.PASSedSDFCombViewer
import forsyde.io.java.drivers.ForSyDeModelHandler
import forsyde.io.java.typed.viewers.moc.sdf.SDFCombViewer
import forsyde.io.java.typed.viewers.impl.Executable
import forsyde.io.java.typed.viewers.impl.ExecutableViewer

class test3 {
	def static void main(String[] args) {
		val path1="forsyde-io\\test\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\test\\sobel-application.fiodl"
		val root="generateCode\\c\\single"
		var model1 = Load.load(path1);
		var model2 = Load.load(path2);
		model2.mergeInPlace(model1)	
		
		val model=model2

		var v = Query.findVertexByName(model,"Abs")
		var a = (new SDFCombViewer(v)).getCombFunctionsPort(model)
		
		for(Executable e:a){
			var c = (e as ExecutableViewer).getViewedVertex().getIdentifier()
			println(c)
		}
		
	
	}	
}