grammar BoolProcessing;

options {
	language=Java;
}

@lexer::header{
package net.sf.ahtutils.controller.processor.bool;
}

@parser::header{
package net.sf.ahtutils.controller.processor.bool;
}

parse: expression;

expression:
	left=BOOL op=binary right=BOOL;

WS: [ \n\t\r]+ -> skip;
binary: AND | OR;
AND: 'AND' | '&&' | 'and';
OR: 'OR' | '||' | 'or';
OPAREN: '(';
CPAREN: ')';
BOOL: 'true' | 'false';
SEP: ', ';
CHAR: [a-z];