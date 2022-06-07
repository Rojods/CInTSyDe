package demo

import forsyde.io.java.drivers.ForSyDeModelHandler

class text6 {
	def static void main(String[] args) {
		val path = "simple.fiodl"
		//val root = "generateCode/c/single2"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)	
		println(model)			
	}
}