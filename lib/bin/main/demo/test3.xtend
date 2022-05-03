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

class test3 {
	def static void main(String[] args) {
		val path1="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\single"
		var model1 = Load.load(path1);
		var model2 = Load.load(path2);
		model2.mergeInPlace(model1)	
		
		val model=model2
		var sdfs =model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(Collectors.toSet())
		var type=sdfs.stream().map([sdf|Query.findSDFChannelDataType(model,sdf)]).collect(Collectors.toSet())
		for(String s:type){
			println(s)
		}
		
		Query.findVertexByName(model,"")
		
				
	}	
}