// Generated from BoolProcessing.g4 by ANTLR 4.7

package net.sf.ahtutils.controller.processor.bool;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BoolProcessingLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, AND=2, OR=3, OPAREN=4, CPAREN=5, BOOL=6, SEP=7, CHAR=8;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"WS", "AND", "OR", "OPAREN", "CPAREN", "BOOL", "SEP", "CHAR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, "'('", "')'", null, "', '"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WS", "AND", "OR", "OPAREN", "CPAREN", "BOOL", "SEP", "CHAR"
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


	public BoolProcessingLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BoolProcessing.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\n@\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\6\2\25\n\2"+
		"\r\2\16\2\26\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3#\n\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\5\4+\n\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\5\7:\n\7\3\b\3\b\3\b\3\t\3\t\2\2\n\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\3\2\4\5\2\13\f\17\17\"\"\3\2c|\2E\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\3\24\3\2\2\2\5\"\3\2\2\2\7*\3\2\2\2\t,\3\2\2\2\13.\3\2\2\2\r9\3\2\2"+
		"\2\17;\3\2\2\2\21>\3\2\2\2\23\25\t\2\2\2\24\23\3\2\2\2\25\26\3\2\2\2\26"+
		"\24\3\2\2\2\26\27\3\2\2\2\27\30\3\2\2\2\30\31\b\2\2\2\31\4\3\2\2\2\32"+
		"\33\7C\2\2\33\34\7P\2\2\34#\7F\2\2\35\36\7(\2\2\36#\7(\2\2\37 \7c\2\2"+
		" !\7p\2\2!#\7f\2\2\"\32\3\2\2\2\"\35\3\2\2\2\"\37\3\2\2\2#\6\3\2\2\2$"+
		"%\7Q\2\2%+\7T\2\2&\'\7~\2\2\'+\7~\2\2()\7q\2\2)+\7t\2\2*$\3\2\2\2*&\3"+
		"\2\2\2*(\3\2\2\2+\b\3\2\2\2,-\7*\2\2-\n\3\2\2\2./\7+\2\2/\f\3\2\2\2\60"+
		"\61\7v\2\2\61\62\7t\2\2\62\63\7w\2\2\63:\7g\2\2\64\65\7h\2\2\65\66\7c"+
		"\2\2\66\67\7n\2\2\678\7u\2\28:\7g\2\29\60\3\2\2\29\64\3\2\2\2:\16\3\2"+
		"\2\2;<\7.\2\2<=\7\"\2\2=\20\3\2\2\2>?\t\3\2\2?\22\3\2\2\2\7\2\26\"*9\3"+
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