package appetizer.Semantic;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import appetizer.ast.*;
import appetizer.Semantic.*;
import appetizer.Env.*;
import appetizer.Types.*;
import appetizer.symbol.*;

public class Semant {
	public Env env = null;

	public Semant() {
		this(new Env());
	}
	public Semant(Env e) {
		env = e;
	}
	
	private List<Error> errors = new ArrayList<Error>();
	private void error(String message) {
		errors.add(new Error(new Position(0, 0), message, true));
	}
	public boolean hasError() {
		return errors.size() > 0;
	}
	public void printErrors() {
		for (Error e : errors) {
			System.out.println("\t" + e);
		}
	}
	
	public Type getType(String st) {
		Env tmp = env;
		while (tmp != null) {
			Type t = (Type)tmp.tEnv.get(Symbol.symbol(st));
			if (t != null)
				return t;
			tmp = tmp.next;
		}
		return null;
	}

	private int loopCount = 0;
	
	public void checkProg(Program p) {
		loopCount = 0;
		if (p.decl instanceof Declaration)
			checkDec((Declaration) p.decl);
		if (p.decl instanceof FunctionDefinition)
			checkDec((FunctionDefinition) p.decl);
		if (p.list != null)
			checkProg(p.list);
	}
	
	public Type checkTypeSp(TypeSpecifier ts) {
		if (ts.decl != null) {
			if (ts.structOrUnion != null) {
				TypeSpecifierDeclarators t = (TypeSpecifierDeclarators)ts.decl;
				RECORD tmp = null;
				while (t != null) {
					Declarators t2 = t.declarators;
					while (t2 != null) {
						//struct *;
						if (t.typeSpecifier.structOrUnion != null
								&& ((Declarator)t2.decl1).plainDeclarator.decl != null
								&& t.typeSpecifier.decl == null) {
							if (tmp != null && tmp.getFieldIndex(((Declarator)t2.decl1).plainDeclarator.sym) != -1) {
								error("field has be declared");
								return tmp;
							}
							Type type = new NAME(ts.sym);
							type = checkDeclarator((Declarator)t2.decl1, type);
							tmp = new RECORD(ts.structOrUnion, ((Declarator)t2.decl1).plainDeclarator.sym,
									type, tmp);
						}
						
						else {
							Type type = checkTypeSp(t.typeSpecifier);
							type = checkDeclarator((Declarator)t2.decl1, type);
							
							if (tmp != null && tmp.getFieldIndex(((Declarator)t2.decl1).plainDeclarator.sym) != -1) {
								error("field has be declared");
								return tmp;
							}
							tmp = new RECORD(ts.structOrUnion, ((Declarator)t2.decl1).plainDeclarator.sym, type, tmp);
						}
						t2 = (Declarators)t2.decl2;
					}
					t = t.typeSpecifierDeclarators;
				}
				if (ts.sym == null)
					env.tEnv.put(Symbol.symbol(tmp.toString()), tmp);
				else
				if (env.tEnv.get(ts.sym) != null)
					error("var has alraedy declarated!");
				else
					env.tEnv.put(ts.sym, tmp);
				return tmp;
			}
		}

		Env tmp = env;
		while (tmp != null) {
			Type ret = (Type)tmp.tEnv.get(ts.sym);
			if (ret != null)
				return ret;
			tmp = tmp.next;
		}
		error("not find type");
		return null;
	}
	
	public void checkDec(Declaration d) {
		if (d == null)
			return;
		
		if (d.decl2 instanceof Declarators) {//typedef
			
		} else {
			// get TypeSpecifier
			Type tp = checkTypeSp((TypeSpecifier) d.decl1);
			Type ty = null;
			
			if (d.decl2 == null)
				return;
			// get array or para
			InitDeclarators id = (InitDeclarators)d.decl2;
			do {
				Declarator dt = id.initDeclarator.declarator;
				ty = checkDeclarator(dt, tp);
				
				if (env.vEnv.get(dt.plainDeclarator.sym) != null)
					error("var has alraedy declarated!");
				else {
					env.vEnv.put(dt.plainDeclarator.sym, new VarEntry(ty, dt.plainDeclarator.sym.toString()));

					// = ...
					if (id.initDeclarator.initializer != null) {
						if (!checkInitializer(id.initDeclarator.initializer, ty))
							error("initializer dont match!");
					}
				}
				id = id.next;
			} while (id != null);
		}
	}
	
	public void checkDec(FunctionDefinition d) {
		Type tp = checkTypeSp((TypeSpecifier) d.decl1);//func type
		PlainDeclarator pd = (PlainDeclarator) d.decl2;
		
		TIMESStar s = (TIMESStar) pd.decl;
		while (s != null) {
			tp = new POINT(tp);
			s = s.tIMESStar;
		}
		
		RECORD r = checkParameters((Parameters)d.decl3);
		
		if (env.vEnv.get(pd.sym) != null)
			error("function name has alraedy declarated!");
		else
			env.vEnv.put(pd.sym, new FunEntry(tp, pd.sym.toString(), r));

		checkStatement((CompoundStatement) d.statements, r);
	}
	
	public RECORD checkParameters(Parameters p) {
		if (p == null)
			return null;
		RECORD r = null;
		Parameters tmp = p;
		while (tmp != null) {
			PlainDeclaration pd = (PlainDeclaration)tmp.decl1;
			Type tp = checkTypeSp(pd.typeSpecifier); //para type
			tp = checkDeclarator((Declarator)pd.decl, tp);

			r = new RECORD(new StructOrUnion("STRUCT"),((Declarator)pd.decl).plainDeclarator.sym, tp, r);
			tmp = tmp.decl2;
		}
		return r;
	}
	
	public Type checkDeclarator(Declarator dt, Type tp) {
		Type ar = tp;
		//array check;
		if (dt.constantExpressionStar != null) {
			ConstantExpressionStar tmp = dt.constantExpressionStar;
			while (tmp != null) {
				if (!(checkConstantExpression(tmp.constantExpression) instanceof INT)) {
					error("array index is not integer!");
					return tp;
				}
				ar = new ARRAY(tp);
				tmp = tmp.constantExpressionStar;
			}
		} else //parameters check
		if (dt.parameters != null)
			checkParameters(dt.parameters);

		//check point
		TIMESStar s = (TIMESStar)dt.plainDeclarator.decl;
		while (s != null) {
			ar = new POINT(ar);
			s = s.tIMESStar;
		}
		return ar;
	}
	
	public boolean checkInitializer(Initializer init, Type t) {
		if (t instanceof ARRAY) {
			Type nt = ((ARRAY)t).element;
			if (init.initializer1 != null)
				if (!checkInitializer(init.initializer1, nt))
					return false;
			if (init.initializer2 != null)
				if (!checkInitializer(init.initializer2, nt))
					return false;
		}
		if (!checkTypeEq(checkAssignmentExpression(init.assignmentExpression), t))
			return false;
		return true;
	}
	
	public void checkStatement(Statements s) {
		if (s instanceof ExpressionStatement)
			checkStatement((ExpressionStatement) s);
		if (s instanceof CompoundStatement)
			checkStatement((CompoundStatement) s, null);
		if (s instanceof SelectionStatement)
			checkStatement((SelectionStatement) s);
		if (s instanceof IterationWhileStatement)
			checkStatement((IterationWhileStatement) s);
		if (s instanceof IterationForStatement)
			checkStatement((IterationForStatement) s);
		if (s instanceof JumpContinueStatement ||
			s instanceof JumpBreakStatement)
			if (loopCount <= 0) {
				error("Jump statement is in wrong place!");
			}
	}
	public void checkStatement(ExpressionStatement s) {
		if (s.expressions != null)
			checkExp((Expression)s.expressions);
	}
	public void checkStatement(CompoundStatement s, RECORD r) {
		Env tmp = new Env(); //new table
		tmp.next = env;
		env = tmp;
		
		while (r != null) {//r is the parameter of a function
			env.vEnv.put(r.fieldName, new VarEntry(r.fieldType, r.fieldName.toString()));
			r = r.tail;
		}
		
		DeclarationStar d = (DeclarationStar)s.declaration;
		while (d != null) {
			checkDec((Declaration)d.decl);
			d = d.declarationStar;
		}
		StatementStar st = (StatementStar) s.statements;
		while (st != null) {
			checkStatement(st.statements);
			st = st.statementStar;
		}

		env = tmp.next;
	}
	public void checkStatement(SelectionStatement s) {
		if (!checkTypeEq(checkExp((Expression)s.expressions), Type.INT))
			error("if need a bool stm");
		checkStatement(s.statements1);
		if (s.statements2 != null)
			checkStatement(s.statements2);
	}
	public void checkStatement(IterationWhileStatement s) {
		if (!checkTypeEq(checkExp((Expression)s.expressions), Type.INT))
			error("while loop need a bool stm");
		++loopCount;
		checkStatement(s.statements);
		--loopCount;
	}
	
	public void checkStatement(IterationForStatement s) {
		checkExp((Expression)s.expressions1);
		if (!checkTypeEq(checkExp((Expression)s.expressions2), Type.INT))
			error("for loop st2 need a bool stm");
		checkExp((Expression)s.expressions3);
		++loopCount;
		checkStatement(s.statements);
		--loopCount;
	}
	
	public Type checkExp(Expression ex) {
		Type t = null;
		Expression tmp = ex;
		while (tmp != null) {
			t = checkAssignmentExpression(tmp.assignmentExpression);
			tmp = tmp.next;
		}
		return t;
	}
	
	public boolean checkTypeEq(Type t1, Type t2) {
		//trans bool is not used
		if (t1 == Type.CHAR || t1 == Type.BOOL)
			t1 = Type.INT;
		if (t2 == Type.CHAR || t2 == Type.BOOL)
			t2 = Type.INT;
		if ((t1 instanceof POINT) && (t2 instanceof POINT))
			return true;
		if ((t2 instanceof POINT) && (t1 == Type.INT))
			return true;
		if (t1 == t2)
			return true;
		
		//if (t1 instanceof RECORD)
		return false;
	}
	
	public boolean islvalue(UnaryExpressionAll ex) {
		if (!(ex instanceof PostfixExpression))
			return false;
		if (ex instanceof UnaryExpression) {
			UnaryExpression e = (UnaryExpression) ex;
			if (e.unaryOperator.equals(UnaryOperator.Type.OPAND) ||
				e.unaryOperator.equals(UnaryOperator.Type.TIMES))
				return islvalue(e.castExpression);
		}
		return islvalue((PostfixExpression)ex);
	}
	public boolean islvalue(PostfixExpression ex) {
		if (ex.primaryExpression.sym == null)
			return false;
		if (checkID(ex.primaryExpression.sym) instanceof FunEntry)
			return false;
		return islvalue(ex.postfixStar);
	}
	public boolean islvalue(PostfixStar ex) {
		if (ex == null)
			return true;
		if (ex.postfix instanceof NULLArg)
			return false;
		if (ex.postfix instanceof Postfix) {
			Postfix p = (Postfix)ex.postfix;
			if (p == null)
				return true;
			if (p.op == Postfix.Type.DEC || p.op == Postfix.Type.INC)
				return false;
		}
		return islvalue(ex.postfixStar);
	}
	public boolean islvalue(CastExpression ex) {
		if (ex instanceof UnaryExpression)
			return islvalue((UnaryExpressionAll) ex);
		return false;
	}
	
	public Type checkAssignmentExpression(AssignmentExpression ex) {
		if (ex instanceof LogicalOrExpression)
			return checkLogicalOrExpression((LogicalOrExpression) ex);
		
		AssignmentExpression tmp = ex;
		Type tp = checkUnaryExpressionALL(tmp.unaryExpressionAll);
		while (tmp != null) {
			Type t = null;
			if (tmp instanceof LogicalOrExpression)
				t = checkLogicalOrExpression((LogicalOrExpression)tmp);
			else {
				t = checkUnaryExpressionALL(tmp.unaryExpressionAll);
				if (!islvalue(tmp.unaryExpressionAll)) {
					error("not a lvalue");
					return null;
				}
			}
			if (!checkTypeEq(t, tp)) {
				error("Assignment type doesnt match!");
			}
			tmp = tmp.assignmentExpression;
		}
		return Type.INT;
	}
	
	public Type checkConstantExpression(ConstantExpression ex) {
		return checkLogicalOrExpression(ex.logicalOrExpression);
	}
	
	public Type checkLogicalOrExpression(LogicalOrExpression ex) {
		Type t = checkLogicalAndExpression(ex.logicalAndExpression);
		OrLogicalAndExpressionStar l = ex.orLogicalAndExpressionStar;
		if (l == null)
			return t;

		if (!checkTypeEq(t, Type.INT)) {
			error("|| need type int");
			return t;
		}
		while (l != null) {
			t = checkLogicalAndExpression(l.head);
			if (!checkTypeEq(t, Type.INT)) {
				error("|| need type int");
				return t;
			}
			l = l.next;
		}
		return Type.INT;
	}
	
	public Type checkLogicalAndExpression(LogicalAndExpression ex) {
		Type t = checkInclusiveOrExpression(ex.inclusiveOrExpression);
		AndInclusiveOrExpressionStar l = ex.andInclusiveOrExpressionStar;
		if (l == null)
			return t;

		if (!checkTypeEq(t, Type.INT)) {
			error("&& need type int");
			return t;
		}
		while (l != null) {
			t = checkInclusiveOrExpression(l.inclusiveOrExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("&& need type int");
				return t;
			}
			l = l.andInclusiveOrExpressionStar;
		}
		return Type.INT;
	}
	
	public Type checkInclusiveOrExpression(InclusiveOrExpression ex) {
		Type t = checkExclusiveOrExpression(ex.exclusiveOrExpression);
		OporExclusiveOrExpressionStar l = ex.oporExclusiveOrExpressionStar;
		if (l == null)
			return t;
		
		if (!checkTypeEq(t, Type.INT)) {
			error("| need type int");
			return t;
		}
		while (l != null) {
			t = checkExclusiveOrExpression(l.exclusiveOrExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("| need type int");
				return t;
			}
			l = l.oporExclusiveOrExpressionStar;
		}
		return Type.INT;
	}
	
	public Type checkExclusiveOrExpression(ExclusiveOrExpression ex) {
		Type t = checkAndExpression(ex.andExpression);
		OPXORAndExpressionStar l = ex.oPXORAndExpressionStar;
		if (l == null)
			return t;
		
		if (!checkTypeEq(t, Type.INT)) {
			error("^ need type int");
			return t;
		}
		while (l != null) {
			t = checkAndExpression(l.andExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("^ need type int");
				return t;
			}
			l = l.oPXORAndExpressionStar;
		}
		return Type.INT;
	}
	
	public Type checkAndExpression(AndExpression ex) {
		Type t = checkEqualityExpression(ex.equalityExpression);
		OPANDEqualityExpressionStar l = ex.oPANDEqualityExpressionStar;
		if (l == null)
			return t;

		if (!checkTypeEq(t, Type.INT)) {
			error("& need type int");
			return t;
		}
		while (l != null) {
			t = checkEqualityExpression(l.equalityExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("& need type int");
				return t;
			}
			l = l.oPANDEqualityExpressionStar;
		}
		return Type.INT;
	}
	
	public Type checkEqualityExpression(EqualityExpression ex) {
		Type ty = checkRelationalExpression(ex.relationalExpression);
		EqualityOperatorRelationalExpressionStar l = ex.equalityOperatorRelationalExpressionStar;
		if (l == null)
			return ty;

		while (l != null) {
			Type t = checkRelationalExpression(l.relationalExpression);
			if (!checkTypeEq(t, ty)) {
				error("== != need equal type");
				return t;
			}
			l = l.equalityOperatorRelationalExpressionStar;
		}
		return Type.INT;
	}
	

	public Type checkRelationalExpression(RelationalExpression ex) {
		Type t = checkShiftExpression(ex.shiftExpression);
		RelationalOperatorShiftExpressionStar l = ex.relationalOperatorShiftExpressionStar;
		if (l == null)
			return t;

		if (!checkTypeEq(t, Type.INT)) {
			error("< > <= >= need type int");
			return t;
		}
		while (l != null) {
			t = checkShiftExpression(l.shiftExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("< > <= >= need type int");
				return t;
			}
			l = l.relationalOperatorShiftExpressionStar;
		}
		return Type.INT;
	}
	

	public Type checkShiftExpression(ShiftExpression ex) {
		Type t = checkAdditiveExpression(ex.additiveExpression);
		ShiftOperatorAdditiveExpressionStar l = ex.shiftOperatorAdditiveExpressionStar;
		if (l == null)
			return t;
		
		if (!checkTypeEq(t, Type.INT)) {
			error("<< >> need type int");
			return t;
		}
		while (l != null) {
			t = checkAdditiveExpression(l.additiveExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("<< >> need type int");
				return t;
			}
			l = l.shiftOperatorAdditiveExpressionStar;
		}
		return Type.INT;
	}
	
	
	public Type checkAdditiveExpression(AdditiveExpression ex) {
		Type t = checkMultiplicativeExpression(ex.multiplicativeExpression);
		AdditiveOperatorMultiplicativeExpressionStar l = ex.additiveOperatorMultiplicativeExpressionStar;
		if (l == null)
			return t;
		
		if (!checkTypeEq(t, Type.INT)) {
			error("+ - need type int");
			return t;
		}
		while (l != null) {
			t = checkMultiplicativeExpression(l.multiplicativeExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("+ - need type int");
				return t;
			}
			l = l.additiveOperatorMultiplicativeExpressionStar;
		}
		return Type.INT;
	}
	
	
	public Type checkMultiplicativeExpression(MultiplicativeExpression ex) {
		Type t = checkCastExpression(ex.castExpression);
		MultiOperatorCastExpressionStar l = ex.multiOperatorCastExpressionStar;
		if (l == null)
			return t;
		if (!checkTypeEq(t, Type.INT)) {
			error("* / % need type int");
			return t;
		}
		while (l != null) {
			t = checkCastExpression(l.castExpression);
			if (!checkTypeEq(t, Type.INT)) {
				error("* / % need type int");
				return t;
			}
			l = l.multiOperatorCastExpressionStar;
		}
		return Type.INT;
	}
	
	
	public Type checkCastExpression(CastExpression ex) {
		if (ex instanceof UnaryExpressionAll)
			return checkUnaryExpressionALL((UnaryExpressionAll) ex);
		else {
			Type t = checkTypename(ex.type);
			checkCastExpression(ex.castExpression);
			return t;
		}
	}
	

	public Type checkTypename(TypeName ty) {
		Type t = checkTypeSp(ty.typeSpecifier);
		TIMESStar s = ty.tIMESStar;
		while (s != null) {
			t = new POINT(t);
			s = s.tIMESStar;
		}
		return t;
	}
	

	public Type checkUnaryExpressionALL(UnaryExpressionAll ex) {
		if (ex instanceof PostfixExpression)
			return checkPostfixExpression((PostfixExpression) ex);
		if (ex instanceof UnaryExpression) {
			Type tt = checkUnaryExpressionALL(((UnaryExpression)ex).unaryExpressionAll);
			if (((UnaryExpression)ex).unaryOperator.unaryOperator == UnaryOperator.Type.SIZEOF)
				return Type.INT;
			else return tt;
		}
		if (ex instanceof UnaryExpressionToCastExpression) {
			Type tt = checkCastExpression(ex.castExpression);
			UnaryOperator op = ((UnaryExpression)ex).unaryOperator;
			if (op.unaryOperator != UnaryOperator.Type.OPAND)
				return new POINT(tt);
			if (op.unaryOperator != UnaryOperator.Type.TIMES)
				if (tt instanceof POINT)
					return ((POINT)tt).element;
				else {
					error("* must follow a POINT");
					return null;
				}
			return tt;
		}
		if (ex instanceof UnaryExpressionToTypeName) {
			checkTypename(((UnaryExpressionToTypeName)ex).typeName);
			return Type.INT;
		}
		return null;
	}
	
	public Type checkPostfixExpression(PostfixExpression ex) {
		Type ty = null;
		Entry en = null;
		if (ex.primaryExpression.sym != null)
			en = checkID(ex.primaryExpression.sym);
		else
			ty = checkPrimaryExpression(ex.primaryExpression);
		
		if (en instanceof FunEntry) {
			if (ex.postfixStar.postfix != null) {
				if (!(ex.postfixStar.postfix instanceof Arguments)) {
					error("para and arg not match");
					return ((FunEntry) en).type;
				}
				if (!checkArg((Arguments)ex.postfixStar.postfix, ((FunEntry) en).para)) {
					error("para and arg not match");
					return ((FunEntry) en).type;
				}
			}
			return ((FunEntry) en).type;
		}
		if (en instanceof VarEntry) {
			if (ex.postfixStar != null && ex.postfixStar.postfix != null && ex.postfixStar.postfix instanceof NULLArg) {
				error("var is not a func");
				return ((VarEntry)en).ty;
			}
		}
		
		if (ty == null)
			ty = ((VarEntry)en).ty;
		
		Type ret = checkPostfix((PostfixStar)ex.postfixStar, ty);
		if (ret != null)
			return ret;
		else
			return Type.INT;
	}
	
	public Type checkPostfix(PostfixStar ex, Type ty) {
		Type retop = ty;
		while (ty instanceof POINT)
			ty = ((POINT)ty).element;
		
		while (ex != null) {
			if (ex.postfix instanceof Expression) {
				if (!(ty instanceof ARRAY)) {
					error("var not a array");
					return null;
				}
				if (!checkTypeEq(checkExp((Expression)ex.postfix), Type.INT)) {
					error("array index should be a int");
					return null;
				}
				ty = ((ARRAY)ty).element;
			}
			else
			if (ex.postfix instanceof Postfix) {
				Postfix p = (Postfix) ex.postfix;
				// .
				if (p.op == Postfix.Type.DOT) {
					if (!(ty instanceof RECORD)) {
						error(". should follows Record");
						return null;
					}
					//find .
					RECORD tmp = (RECORD)ty;
					if (tmp.getFieldIndex(p.sym) == -1) {
						error("undefined field");
						return null;
					}
					ty = tmp.getField(p.sym).fieldType;
				} else
				// ->
				if (p.op == Postfix.Type.PTR) {
					if (!(retop instanceof POINT)) {
						error("-> should follow a point");
						return null;
					}
					if (!(ty instanceof RECORD)) {
						error("-> should follows Record");
						return null;
					}
					RECORD tmp = (RECORD)ty;
					if (tmp.getFieldIndex(p.sym) == -1) {
						error("undefined field");
						return null;
					}
					tmp = tmp.getField(p.sym);
					/*
					while (ty instanceof POINT)
						ty = ((POINT) ty).element;
					*/
					return tmp.fieldType;
				}
				// ++ --
			}
			ex = ex.postfixStar;
		}
		
		if (retop instanceof POINT) {
			POINT retop1 = (POINT)retop;
			while (retop1.element instanceof POINT)
				retop1 = (POINT)retop1.element;
			retop1.element = ty;
			return retop;
		}
			else return ty;
	}
	
	public boolean checkArg(Arguments a, RECORD r) {
		while (a != null) {
			if (r == null)
				return false;
			if (r.variable == true)
				return true;
			Type t = checkAssignmentExpression(a.assignmentExpression);
			if (!checkTypeEq(t, r.fieldType))
				return false;
			
			a = a.next;
			r = r.tail;
		}
		if (r != null)
			return false;
		return true;
	}
	
	public Type checkPrimaryExpression(PrimaryExpression ex) {
		if (ex.expressions instanceof Num)
			return Type.INT;
		if (ex.expressions instanceof Char)
			return Type.CHAR;
		if (ex.expressions instanceof SString)
			return Type.STRING;
		return checkExp((Expression) ex.expressions);
	}
	
	public Entry checkID(Symbol s) {
		Env e = env;
		while (e != null) {
			Entry tmp = (Entry)e.vEnv.get(s);
			if (tmp != null)
				return tmp;
			e = e.next;
		}
		error("not find ID");
		return null;
	}
	
}
