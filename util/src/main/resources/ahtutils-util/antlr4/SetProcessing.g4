grammar SetProcessing;

options {
	language=Java;
}

@lexer::header{
	package net.sf.ahtutils.controller.processor.set;

	import java.util.List;
    import java.util.ArrayList;
}
@parser::header{
	package net.sf.ahtutils.controller.processor.set;

	import java.util.List;
    import java.util.ArrayList;
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