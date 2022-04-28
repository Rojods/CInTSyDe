package template.baremetal

import template.templateInterface.SubsystemTemplate

class SubsystemTemplateSrc implements SubsystemTemplate{
	
	def String create(){
		'''
		/*
		==============================================
			Declare Extern Channel Variable
		==============================================
		*/	
		/*
		==============================================
			Subsystem Function
		==============================================
		*/	
		int subsystem_«tile_name»(){
			
			/* Initilize Channels */
			
			/*    SDFdelay        */
			
			while(1){
					
				
			}
		}
		
		'''
	}
	
}