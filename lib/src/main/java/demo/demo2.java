package demo;

import factory.Factory;
import factory.c.CFactory;
import strategy.c.rtos.*;
import util.Load;
/**
 * 
 * demo for rtos platform,c language
 */
public class demo2 {

	public static void main(String[] args) {
		String root = "generatedCode\\c\\rtos";
		String forsyde="forsyde-io\\sobel2mpsoc_mapped_no_outside_port.forsyde.xmi";
		
		var model = Load.load(forsyde);	
		
		
		
		Factory f = new CFactory(root);
		f.add(new MainStrategy_rtos(model));
		f.add(new SDFChannelHeaderStrategy_rtos(model));
		f.add(new SDFChannelSourceStrategy_rtos(model));
		f.add(new SDFCombHeaderStrategy_rtos(model));
		f.add(new SDFCombSourceStrategy_rtos(model));
		f.add(new ConfigStrategy_rtos(model));
		f.add(new StartTaskSourceStrategy_rtos(model));
		f.add(new StartTaskHeaderStrategy_rtos(model));
		f.create();
	}

}
