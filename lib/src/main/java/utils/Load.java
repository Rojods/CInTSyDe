package utils;

import forsyde.io.java.core.ForSyDeSystemGraph;

import forsyde.io.java.drivers.ForSyDeModelHandler;
import generator.Generator;

public class Load {
	public static ForSyDeSystemGraph load(String path) {
		
		ForSyDeModelHandler handler= null;
		ForSyDeSystemGraph model = null;
		try {
			handler= new ForSyDeModelHandler();
			model = handler.loadModel(path);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Graph creation fail!");
			return null;
		}
		
		return model;
	}
}
