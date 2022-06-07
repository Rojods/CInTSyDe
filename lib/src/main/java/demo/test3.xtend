package demo

import org.jgrapht.traverse.BreadthFirstIterator
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.drivers.ForSyDeModelHandler

class test3 {
	def static void main(String[] args) {
		val path = "forsyde-io/modified1/complete-mapped-sobel-model.forsyde.xmi";
		val path2 = "forsyde-io/modified1/sobel-application.fiodl"
		val root = "generateCode/c/single/single"

		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)
		model.mergeInPlace(loader.loadModel(path2))
		
		
		(new ForSyDeModelHandler).writeModel(model,"example1.fiodl")
	}
}