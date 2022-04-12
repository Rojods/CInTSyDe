package demo

import factory.c.CFactory

import strategy.Strategy
import strategy.c.single.SDFChannelHeader
import strategy.c.single.SDFChannelSource
import strategy.c.single.SDFCombHeader
import strategy.c.single.SDFCombSource
import strategy.c.single.SystemHeader_single
import strategy.c.single.SystemSource_single
import strategy.c.single.main_single
import util.Load
import strategy.withoutRTOS.SpinLock

/**
 * 
 * demo for single platform,c language
 */
class demo {
	def static void main(String[] args) {
		val forsyde="forsyde-io\\sobel2mpsoc_mapped_no_outside_port.forsyde.xmi";
		val root="generatedCode\\c\\single"
		var model = Load.load(forsyde);		
		var system="example"
		var fac = new CFactory(root)
		
		var Strategy s1 = new SDFChannelHeader(model)
		var Strategy s2 = new SDFChannelSource(model)
		
		fac.add(new SDFChannelHeader(model))
		fac.add(new SDFChannelSource(model))
		
		fac.add(new SDFCombHeader(model))
		fac.add(new SDFCombSource(model))
		
		
		s1 = new SystemHeader_single(model)
		s2 = new SystemSource_single(model)
		(s1 as SystemHeader_single).setSystemName(system)
		(s2 as SystemSource_single).setSystemName(system)
		
		
		fac.add(s1)
		fac.add(s2)
		
		
		
		s2 = new main_single(model)
		(s2 as main_single).setSystemName(system)
		fac.add(s2)
		
		var Strategy a= new SpinLock();
		a.setSaveRoot(root);
		fac.add(a);
		fac.create()
		
		println("end!")
		
	}
}