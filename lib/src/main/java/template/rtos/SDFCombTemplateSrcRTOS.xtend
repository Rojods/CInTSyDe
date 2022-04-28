package template.rtos
import fileAnnotation.FileType
import fileAnnotation.FileTypeAnno
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexAcessor
import forsyde.io.java.core.VertexAcessor.VertexPortDirection
import forsyde.io.java.core.VertexTrait
import generator.Generator
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import template.templateInterface.ActorTemplate
import utils.Name
import utils.Query
@FileTypeAnno(type=FileType.C_SOURCE)
class SDFCombTemplateSrcRTOS implements ActorTemplate {
	Set<Vertex> implActorSet
	
	override create(Vertex vertex) {
		var name=Name.name(vertex)
		'''
		/*
		==============================================
			Define Task Stack
		==============================================
		*/
		StackType_t task_«name»_stk[TASK_STACKSIZE];
		StaticTask_t tcb_«name»;
		/*

		
		==============================================
			Declare Extern Message Queue Handler
		==============================================
		*/
		/*
		==============================================
			Define Soft Timer and Soft Timer Semaphore
		==============================================
		*/
		
		SemaphoreHandle_t timer_sem_«name»;
		TimerHandle_t task_timer_«name»;
		/*
		==============================================
			Define Task Function
		==============================================
		*/
		void task_«name»(void* pdata){
			/* Initilize Memory           */
			«initMemory()»
			while(1){
				/* Read From Channel      */
				«read(vertex)»
				/* Inline Code            */
				«getInlineCode()»
				/* Write To Channel       */
				«write(vertex)»
				/* Pend Timer's Semaphore */	
				xSemaphoreTake(task_sem_«name», portMAX_DELAY);	
				
			}
			
			
		}
		'''
	}
	/**
	 * initMemory is copied from initMemory in SDFCombTemplateSrc class
	 */
	def String initMemory() {
		var Set<String> variableNameRecord = new HashSet
		var String ret = ""
		for (Vertex impl : implActorSet) {

			val portToTypeNameHashMap = (impl.getProperties().get("portToTypeName").unwrap() as HashMap<String, String>)
			val inputPortList = (impl.getProperties().get("inputPorts").unwrap() as ArrayList<String>)
			val outputPortList = (impl.getProperties().get("outputPorts").unwrap() as ArrayList<String>)

			var inputPortAndoutputPortSet = new HashSet
			inputPortAndoutputPortSet.addAll(inputPortList)
			inputPortAndoutputPortSet.addAll(outputPortList)

			ret += '''
				«FOR port : inputPortAndoutputPortSet SEPARATOR "" AFTER ""»
					«IF !variableNameRecord.contains(port)»
						«portToTypeNameHashMap.get(port)»  «port»;
						«var tmp=variableNameRecord.add(port)»
					«ENDIF»
				«ENDFOR»
			'''
		}
		return ret
	}
	/**
	 * copied and modified from method read in SDFCombTemplateSrc class
	 */
	def String read(Vertex vertex) {
		var consumption = vertex.getProperties().get("consumption")
		if (consumption !== null) {
			var inputPortsHashMap = (consumption.unwrap() as HashMap<String, Integer>)
			'''
				«FOR port : inputPortsHashMap.keySet() SEPARATOR "" AFTER ""»
					«IF inputPortsHashMap.get(port)==1»
						read_nonblocking(«port»_channel);
					«ELSE»
						for(int i=0;i<«inputPortsHashMap.get(port)»;++i){
							read_nonblocking(«port»_channel);
						}
					«ENDIF»
				«ENDFOR»
			'''
		} else {
			'''
			'''
		}
	}
	/**
	 * copied and modified from method write in SDFCombTemplateSrc class
	 */	
	def String write(Vertex vertex) {
		var production = vertex.getProperties().get("production")
		if (production !== null) {
			var inputPortsHashMap = (production.unwrap() as HashMap<String, Integer>)
			'''
				«FOR port : inputPortsHashMap.keySet() SEPARATOR "" AFTER ""»
					«IF inputPortsHashMap.get(port)==1»
						write(«port»_channel);
					«ELSE»
						for(int i=0;i<«inputPortsHashMap.get(port)»;++i){
							write(«port»_channel);
						}
					«ENDIF»
				«ENDFOR»
			'''
		} else {
			'''
			'''
		}
	}
	/**
 	*This method is same as getInlineCode in SDFCombTemplateSrc class
 	*/	
	private def String getInlineCode() {

		'''
			«FOR impl : implActorSet SEPARATOR "" AFTER ""»
				//in combFunction «impl.getIdentifier()»
				«Query.getInlineCode(impl)»
			«ENDFOR»		
		'''

	}		
}