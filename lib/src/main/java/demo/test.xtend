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
/**
 * load fiodl file
 */
class test {
	def static void main(String[] args) {
		val path="a.forsyde.xmi"
//		var model = (new ForSyDeModelHandler).loadModel(path)
		
		demo1.test(path)
		
		println("end!")	
	}
}