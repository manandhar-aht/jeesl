package org.jeesl.controller.handler.module.survey.antlr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

//Does not work with special characters
public class ConditionEvaluator 
{
	public boolean evaluate(String condition, List<Boolean> booleans)
	{
		List<String> expressions = getIdentifierTokens(condition);
		Map<String, Boolean> variables = new HashMap<String, Boolean>() ;
		boolean result = false;
		if (expressions.size()==booleans.size())
		{
			for(int i=0;i<expressions.size();i++) 
			{
				variables.put(expressions.get(i), booleans.get(i));
			}
			SimpleBooleanLexer lexer = new SimpleBooleanLexer(CharStreams.fromString(condition));
			SimpleBooleanParser parser = new SimpleBooleanParser(new CommonTokenStream(lexer));
			return result = new EvalVisitor(variables).visit(parser.parse());
		}
		System.out.println("Number of statements does not match number of booleans");
		return result;
	}
	
	private List<String> getIdentifierTokens(String condition)
	{
		condition = buildCondition(condition);
		List<String> identifier = new ArrayList<String>();
		SimpleBooleanLexer lexer = new SimpleBooleanLexer(CharStreams.fromString(condition));
		List<? extends Token> tokens = lexer.getAllTokens();
		int tokenType = lexer.getTokenType("IDENTIFIER");
		for (Token token : tokens)
		{
			if(token.getType()==tokenType)
			{
				identifier.add(token.getText());
			}
		}
		return identifier;
	}
	
	public String buildCondition(String condition)
	{
		String newCondition= "";
		SimpleBooleanLexer lexer = new SimpleBooleanLexer(CharStreams.fromString(condition));
		List<? extends Token> tokens = lexer.getAllTokens();
		for(Token token:tokens)
		{
			newCondition+=token.getText();
		}
		return newCondition;
	}
}
