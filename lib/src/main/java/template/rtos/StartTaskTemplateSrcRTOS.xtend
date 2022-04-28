package template.rtos

class StartTaskTemplateSrcRTOS {
	def String create(){
		'''
		/*
		=================================================
			Define Task Stack
		=================================================
		*/
		StackType_t task_StartTask_stk[TASK_STACKSIZE]; 
		StaticTask_t tcb_start;
		/*
		=================================================
			Declare Extern Task Stack
		=================================================
		*/		
		/*
		=================================================
			Declare Message Queue
		=================================================
		*/	
		/*
		=================================================
			Declare Extern Software Timer and Semaphore
		=================================================
		*/	
						
		void start_task(){
			/* Initilize Message Queue     */
			
			/* Initilize Software Timer    */
			
			/* Initilize Timer's Semaphore */
			
			/* Initilize Actor Tasks       */
			
			/* Start Software Timer        */
			
			/* Delete start task           */
			
		}
		
		'''
	}
}