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
import forsyde.io.java.typed.viewers.values.Value
import forsyde.io.java.typed.viewers.typing.datatypes.IntegerViewer
import forsyde.io.java.typed.viewers.values.IntegerValue

class test {
	def static void main(String[] args) {
		val path="forsyde-io\\test\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\test\\sobel-application.fiodl"
		val root="generateCode\\c\\single"

		//var model = (new ForSyDeModelHandler()).loadModel(path)
		
		var model1 = Load.load(path)
		var model2 = Load.load(path2);	
		
		model1.mergeInPlace(model2)
		
		var model=model1
		
		var a = model.queryVertex("GrayScaleY").get()
		var sdf=SDFChannel.safeCast(a).get()
		var b = (sdf.getProperties().get("__initialTokenValues_ordering__").unwrap() as HashMap<String,Integer>)
		var c = b.keySet()
		
		
		println(b)
	}
}