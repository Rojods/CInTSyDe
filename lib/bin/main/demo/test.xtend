package demo

import utils.Load
import forsyde.io.java.core.Vertex
import java.util.Set
import java.util.HashSet
import java.util.ArrayList
import java.util.Map
import java.util.HashMap
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import forsyde.io.java.drivers.ForSyDeModelHandler
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

class test {
	def static void main(String[] args) {
		val path="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\single"

		//var model = (new ForSyDeModelHandler()).loadModel(path)
		
		var model1 = Load.load(path)
		var model2 = Load.load(path2);	
		
		model1.mergeInPlace(model2)
		model1.vertexSet().stream().filter([v|SDFComb::conforms(v)])
									.forEach([v|println(v)])
									
									
		var a = model1.vertexSet().stream().filter([v|SDFChannel::conforms(v)])
			.filter([v|v.getIdentifier()=="GrayScaleY"])
			.findAny().get()
		
//		var b = a.getProperties().get("__initialTokenValues_ordering__").unwrap()
//
//		var c = 1
		
		
	}
}