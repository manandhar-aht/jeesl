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
	 op=binary right=CHAR
	| op=binary OPAREN? expression CPAREN?
	)?;

//list:
//	BEG elem (SEP elem)* END;
//
//elem:
//	CHAR+;

WS: [ \n\t\r]+ -> skip;
binary: AND | OR;
AND: 'AND' | '&&';
OR: 'OR' | '||';
BEG: '[';
END: ']';
OPAREN: '(';
CPAREN: ')';
CHAR: [a-z];
SEP: ', ';