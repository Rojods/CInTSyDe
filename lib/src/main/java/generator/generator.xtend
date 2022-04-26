package generator

import forsyde.io.java.core.ForSyDeSystemGraph
import java.util.HashSet
import java.util.Set
import template.Template
import utils.Global

import static utils.Global.*

class generator {

	public static String root=null	
	Set<Template> set=new HashSet
	
	
	
	new(ForSyDeSystemGraph model,String root){
		this.root=root
		Global.model=model
	}
	
	def create() {
		
		for(Template s:set){
			s.create()
		}
					
	}	

	def add(Template template) {
		set.add(template)
	}	


}