// Generated from SimpleBoolean.g4 by ANTLR 4.7.1
package org.jeesl.controller.handler.module.survey.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SimpleBooleanLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		AND=1, OR=2, NOT=3, TRUE=4, FALSE=5, LPAREN=6, RPAREN=7, IDENTIFIER=8, 
		WS=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"AND", "OR", "NOT", "TRUE", "FALSE", "LPAREN", "RPAREN", "IDENTIFIER", 
		"WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'TRUE'", "'FALSE'", "'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "AND", "OR", "NOT", "TRUE", "FALSE", "LPAREN", "RPAREN", "IDENTIFIER", 
		"WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public SimpleBooleanLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SimpleBoolean.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13g\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\5\2 \n\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\5\3*\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4D\n\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\t\7\tV\n\t\f\t\16\tY\13\t\3"+
		"\t\7\t\\\n\t\f\t\16\t_\13\t\3\n\6\nb\n\n\r\n\16\nc\3\n\3\n\2\2\13\3\3"+
		"\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\3\2\5\7\2\62;C\\aac|\u0082\u0101"+
		"\6\2\13\13\17\17\"\"*+\5\2\13\f\16\17\"\"\2p\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\3\37\3\2\2\2\5)\3\2\2\2\7C\3\2\2\2\tE\3\2\2\2\13J\3"+
		"\2\2\2\rP\3\2\2\2\17R\3\2\2\2\21W\3\2\2\2\23a\3\2\2\2\25\26\7\"\2\2\26"+
		"\27\7c\2\2\27\30\7p\2\2\30\31\7f\2\2\31 \7\"\2\2\32\33\7\"\2\2\33\34\7"+
		"C\2\2\34\35\7P\2\2\35\36\7F\2\2\36 \7\"\2\2\37\25\3\2\2\2\37\32\3\2\2"+
		"\2 \4\3\2\2\2!\"\7\"\2\2\"#\7q\2\2#$\7t\2\2$*\7\"\2\2%&\7\"\2\2&\'\7Q"+
		"\2\2\'(\7T\2\2(*\7\"\2\2)!\3\2\2\2)%\3\2\2\2*\6\3\2\2\2+,\7p\2\2,-\7q"+
		"\2\2-.\7v\2\2.D\7\"\2\2/\60\7P\2\2\60\61\7Q\2\2\61\62\7V\2\2\62D\7\"\2"+
		"\2\63\64\7\"\2\2\64\65\7p\2\2\65\66\7q\2\2\66\67\7v\2\2\67D\7\"\2\289"+
		"\7\"\2\29:\7P\2\2:;\7Q\2\2;<\7V\2\2<D\7\"\2\2=>\7p\2\2>?\7q\2\2?D\7v\2"+
		"\2@A\7P\2\2AB\7Q\2\2BD\7V\2\2C+\3\2\2\2C/\3\2\2\2C\63\3\2\2\2C8\3\2\2"+
		"\2C=\3\2\2\2C@\3\2\2\2D\b\3\2\2\2EF\7V\2\2FG\7T\2\2GH\7W\2\2HI\7G\2\2"+
		"I\n\3\2\2\2JK\7H\2\2KL\7C\2\2LM\7N\2\2MN\7U\2\2NO\7G\2\2O\f\3\2\2\2PQ"+
		"\7*\2\2Q\16\3\2\2\2RS\7+\2\2S\20\3\2\2\2TV\t\2\2\2UT\3\2\2\2VY\3\2\2\2"+
		"WU\3\2\2\2WX\3\2\2\2X]\3\2\2\2YW\3\2\2\2Z\\\n\3\2\2[Z\3\2\2\2\\_\3\2\2"+
		"\2][\3\2\2\2]^\3\2\2\2^\22\3\2\2\2_]\3\2\2\2`b\t\4\2\2a`\3\2\2\2bc\3\2"+
		"\2\2ca\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\b\n\2\2f\24\3\2\2\2\t\2\37)CW]c\3"+
		"\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}