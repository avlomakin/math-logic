package com.github.avlomakin.prop;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PropFormulaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, LOGICAL_AND=3, LOGICAL_OR=4, LOGICAL_IMPLY=5, NOT=6, SP=7, 
		PROP_VARIABLE=8, STRING=9;
	public static final int
		RULE_formula = 0;
	private static String[] makeRuleNames() {
		return new String[] {
			"formula"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'&'", "'|'", "'->'", "'!'", "' '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "LOGICAL_AND", "LOGICAL_OR", "LOGICAL_IMPLY", "NOT", 
			"SP", "PROP_VARIABLE", "STRING"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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

	@Override
	public String getGrammarFileName() { return "PropFormula.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PropFormulaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FormulaContext extends ParserRuleContext {
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
	 
		public FormulaContext() { }
		public void copyFrom(FormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LogicalAndContext extends FormulaContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode LOGICAL_AND() { return getToken(PropFormulaParser.LOGICAL_AND, 0); }
		public List<TerminalNode> SP() { return getTokens(PropFormulaParser.SP); }
		public TerminalNode SP(int i) {
			return getToken(PropFormulaParser.SP, i);
		}
		public LogicalAndContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).enterLogicalAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).exitLogicalAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropFormulaVisitor ) return ((PropFormulaVisitor<? extends T>)visitor).visitLogicalAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalOrContext extends FormulaContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode LOGICAL_OR() { return getToken(PropFormulaParser.LOGICAL_OR, 0); }
		public List<TerminalNode> SP() { return getTokens(PropFormulaParser.SP); }
		public TerminalNode SP(int i) {
			return getToken(PropFormulaParser.SP, i);
		}
		public LogicalOrContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).enterLogicalOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).exitLogicalOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropFormulaVisitor ) return ((PropFormulaVisitor<? extends T>)visitor).visitLogicalOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesisContext extends FormulaContext {
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode NOT() { return getToken(PropFormulaParser.NOT, 0); }
		public ParenthesisContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).enterParenthesis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).exitParenthesis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropFormulaVisitor ) return ((PropFormulaVisitor<? extends T>)visitor).visitParenthesis(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LogicalImplyContext extends FormulaContext {
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public TerminalNode LOGICAL_IMPLY() { return getToken(PropFormulaParser.LOGICAL_IMPLY, 0); }
		public List<TerminalNode> SP() { return getTokens(PropFormulaParser.SP); }
		public TerminalNode SP(int i) {
			return getToken(PropFormulaParser.SP, i);
		}
		public LogicalImplyContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).enterLogicalImply(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).exitLogicalImply(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropFormulaVisitor ) return ((PropFormulaVisitor<? extends T>)visitor).visitLogicalImply(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralContext extends FormulaContext {
		public TerminalNode PROP_VARIABLE() { return getToken(PropFormulaParser.PROP_VARIABLE, 0); }
		public TerminalNode NOT() { return getToken(PropFormulaParser.NOT, 0); }
		public LiteralContext(FormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PropFormulaListener) ((PropFormulaListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PropFormulaVisitor ) return ((PropFormulaVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		return formula(0);
	}

	private FormulaContext formula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FormulaContext _localctx = new FormulaContext(_ctx, _parentState);
		FormulaContext _prevctx = _localctx;
		int _startState = 0;
		enterRecursionRule(_localctx, 0, RULE_formula, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				_localctx = new ParenthesisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(4);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(3);
					match(NOT);
					}
				}

				setState(6);
				match(T__0);
				setState(7);
				formula(0);
				setState(8);
				match(T__1);
				}
				break;
			case 2:
				{
				_localctx = new LiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(11);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(10);
					match(NOT);
					}
				}

				setState(13);
				match(PROP_VARIABLE);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(45);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(43);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new LogicalAndContext(new FormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(16);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(18);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==SP) {
							{
							setState(17);
							match(SP);
							}
						}

						setState(20);
						match(LOGICAL_AND);
						setState(22);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==SP) {
							{
							setState(21);
							match(SP);
							}
						}

						setState(24);
						formula(5);
						}
						break;
					case 2:
						{
						_localctx = new LogicalOrContext(new FormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(25);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(27);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==SP) {
							{
							setState(26);
							match(SP);
							}
						}

						setState(29);
						match(LOGICAL_OR);
						setState(31);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==SP) {
							{
							setState(30);
							match(SP);
							}
						}

						setState(33);
						formula(4);
						}
						break;
					case 3:
						{
						_localctx = new LogicalImplyContext(new FormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_formula);
						setState(34);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(36);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==SP) {
							{
							setState(35);
							match(SP);
							}
						}

						setState(38);
						match(LOGICAL_IMPLY);
						setState(40);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==SP) {
							{
							setState(39);
							match(SP);
							}
						}

						setState(42);
						formula(3);
						}
						break;
					}
					} 
				}
				setState(47);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 0:
			return formula_sempred((FormulaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean formula_sempred(FormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13\63\4\2\t\2\3\2"+
		"\3\2\5\2\7\n\2\3\2\3\2\3\2\3\2\3\2\5\2\16\n\2\3\2\5\2\21\n\2\3\2\3\2\5"+
		"\2\25\n\2\3\2\3\2\5\2\31\n\2\3\2\3\2\3\2\5\2\36\n\2\3\2\3\2\5\2\"\n\2"+
		"\3\2\3\2\3\2\5\2\'\n\2\3\2\3\2\5\2+\n\2\3\2\7\2.\n\2\f\2\16\2\61\13\2"+
		"\3\2\2\3\2\3\2\2\2\2=\2\20\3\2\2\2\4\6\b\2\1\2\5\7\7\b\2\2\6\5\3\2\2\2"+
		"\6\7\3\2\2\2\7\b\3\2\2\2\b\t\7\3\2\2\t\n\5\2\2\2\n\13\7\4\2\2\13\21\3"+
		"\2\2\2\f\16\7\b\2\2\r\f\3\2\2\2\r\16\3\2\2\2\16\17\3\2\2\2\17\21\7\n\2"+
		"\2\20\4\3\2\2\2\20\r\3\2\2\2\21/\3\2\2\2\22\24\f\6\2\2\23\25\7\t\2\2\24"+
		"\23\3\2\2\2\24\25\3\2\2\2\25\26\3\2\2\2\26\30\7\5\2\2\27\31\7\t\2\2\30"+
		"\27\3\2\2\2\30\31\3\2\2\2\31\32\3\2\2\2\32.\5\2\2\7\33\35\f\5\2\2\34\36"+
		"\7\t\2\2\35\34\3\2\2\2\35\36\3\2\2\2\36\37\3\2\2\2\37!\7\6\2\2 \"\7\t"+
		"\2\2! \3\2\2\2!\"\3\2\2\2\"#\3\2\2\2#.\5\2\2\6$&\f\4\2\2%\'\7\t\2\2&%"+
		"\3\2\2\2&\'\3\2\2\2\'(\3\2\2\2(*\7\7\2\2)+\7\t\2\2*)\3\2\2\2*+\3\2\2\2"+
		"+,\3\2\2\2,.\5\2\2\5-\22\3\2\2\2-\33\3\2\2\2-$\3\2\2\2.\61\3\2\2\2/-\3"+
		"\2\2\2/\60\3\2\2\2\60\3\3\2\2\2\61/\3\2\2\2\r\6\r\20\24\30\35!&*-/";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}