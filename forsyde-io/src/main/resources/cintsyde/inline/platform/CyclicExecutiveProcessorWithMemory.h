#ifndef CYCLIC_EXECUTIVE_{{schedulerName}}_H_
#define CYCLIC_EXECUTIVE_{{schedulerName}}_H_

void {{schedulerName}}Loop() {
    while (1) {
        {{#orderedEntries}}
        {{entryName}}({{entryArgumentString}});
        {{/orderedEntries}}
    }
}

##endif