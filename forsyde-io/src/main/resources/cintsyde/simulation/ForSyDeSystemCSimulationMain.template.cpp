#include "forsyde.hpp"
#include "{{dataTypes.targetPath}}"

/*******************************************************************************
 * including SDF modules
 */
{{#sdfModules}}
#include "{{targetPath}}"
{{sdfModules}}

int sc_main(int argc, char** argv) {

    // generating SDF loose 'leaf' processes via their constructors
    {{#sdfActors}}
    {{constructorCall}} *{{childActorName}} = new SDF::{{constructorCall}}({{childActorName}}, {{childInlinedFunctionName}}, {{orderedRates}});
    {{sdfActors}}

    // now the same is done for composite actors
    {{#sdfModules}}
    auto {{actorName}} = new {{actorClassName}}({{actorName}});
    {{sdfModules}}

    // connecting them via ports
    {{#sdfConnections}}
    {{srcActorName}}->{{srcActorPortName}}({{dstActorName}}->{{dstActorPortName}});
    {{sdfConnections}}

    sc_start();

    return 0;
}