grammar SetProcessing;

options {
	language=Java;
}

@lexer::header{
package net.sf.ahtutils.controller.processor.set;
}

@parser::header{
package net.sf.ahtutils.controller.processor.set;
}

parse: expression;

expression:
	left=CHAR
	(
	op=binary OPAREN? expression CPAREN?
	)?;

WS: [ \n\t\r]+ -> skip;
binary: AND | OR;
AND: 'AND' | '&&' | 'and';
OR: 'OR' | '||' | 'or';
BEG: '[';
END: ']';
OPAREN: '(';
CPAREN: ')';
CHAR: [a-z];
SEP: ', ';