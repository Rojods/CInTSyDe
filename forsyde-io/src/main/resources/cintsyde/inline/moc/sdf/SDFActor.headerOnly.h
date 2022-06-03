#ifndef SDF_ACTOR_{{actorName}}_H_
#define SDF_ACTOR_{{actorName}}_H_

/*
========================================
mandatory includes for compilation
========================================
*/

#include "{{typeDefsPath}}"
#include "{{circularFifoDefsPath}}"

/*
========================================
Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
«FOR sdf : this.inputSDFChannelSet SEPARATOR "" AFTER ""»
    «IF !record.contains(sdf)»
«««					extern circular_fifo_«Query.findSDFChannelDataType(Generator.model,sdf)» fifo_«sdf.getIdentifier()»;
«««					extern spinlock spinlock_«sdf.getIdentifier()»;
{{#connectedChannel}}
extern ref_fifo fifo_«sdf.getIdentifier()»;
extern spinlock spinlock_«sdf.getIdentifier()»;
        «var tmp=record.add(sdf)»
    «ENDIF»
«ENDFOR»
{{/connectedChannel}}
/* Output FIFO */
«FOR sdf : this.outputSDFChannelSet SEPARATOR "" AFTER ""»
    «IF !record.contains(sdf)»
«««					extern circular_fifo_«Query.findSDFChannelDataType(Generator.model,sdf)» fifo_«sdf.getIdentifier()»;
«««					extern spinlock spinlock_«sdf.getIdentifier()»;
        extern ref_fifo fifo_«sdf.getIdentifier()»;
        extern spinlock spinlock_«sdf.getIdentifier()»;
        «var tmp=record.add(sdf)»
    «ENDIF»
«ENDFOR»
/*
        ========================================
            Declare Extern Global Variables
        ========================================
        */
            «FOR d : datablock»
        extern «findType(model,d)» «d.getIdentifier()»;
            «ENDFOR»

        /*
        ========================================
            Actor Function
        ========================================
        */
        /*  initialize memory*/
«««						«initMemory(model,actor)»
        «ret1»
        void actor_«name»(){

«««			/*  initialize memory*/
«««			«initMemory(model,actor)»
            «ret2»
            /* Read From Input Port  */
            int ret=0;
            «read(model,actor)»


            /* Inline Code           */
            «getInlineCode()»

            /* Write To Output Ports */
            «write(actor)»

        }
    '''
}
#endif