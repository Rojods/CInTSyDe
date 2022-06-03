{{#requireImports}}
#include "{{importPath}}"
{{/requireImports}}

{{#globalIO}}
volatile {{ioType}} {{ioName}};
{{/globalIO}}

int main(int argc, char** argv) {
    while (1) {
        {{#orderedEntries}}
        {{entryName}}({{entryArgumentString}});
        {{/orderedEntries}}
    }
    return 0;
}