package demo


import factory.c.CFactory



import strategy.Strategy


import util.Load

import strategy.c.single.*
import strategy.c.multi.MainSourceStrategy_multi
/**
 * 
 * demo for multi platform,c language
 */
class demo3 {
	def static void main(String[] args) {
		val forsyde="forsyde-io\\sobel2mpsoc_mapped_no_outside_port.forsyde.xmi";
		val root="generatedCode\\c\\multi"
		var model = Load.load(forsyde);		
		var system="example"
		var fac = new CFactory(root)

		fac.add(new SDFChannelHeader(model))
		fac.add(new SDFChannelSource(model))
		
		
		fac.add(new SDFCombHeader(model))
		fac.add(new SDFCombSource(model))
		
		
		fac.add(new MainSourceStrategy_multi(model))
		

		
		fac.create()
		
		println("end!")
		
	}
}