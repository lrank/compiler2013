package translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Env.*;
import Types.*;
import ast.*;
import symbol.Symbol;

public class Translate {
	public Env env = null;
	int offset = 0, level = 0;

	public Translate() {
		this(new Env());
	}
	public Translate(Env e) {
		env = e;
		offset = level = 0;
	}

	public List<Tcode> list = new ArrayList<Tcode>();
	private Stack<Label> stack = new Stack<Label>();
	public List<String> str = new ArrayList<String>(); 
	
	public void listallCode() {
		for (Tcode c : list) {
			if (c instanceof Label)
				System.out.println(((Label) c).tostring());
			if (c instanceof Gotocode)
				System.out.println(((Gotocode) c).tostring());
			if (c instanceof Ifcode)
				System.out.println(((Ifcode) c).tostring());
			if (c instanceof IICode)
				System.out.println(((IICode) c).tostring());
			if (c instanceof InCode)
				System.out.println(((InCode) c).tostring());
			if (c instanceof NotIfcode)
				System.out.println(((NotIfcode) c).tostring());
			if (c instanceof Callcode)
				System.out.println(((Callcode) c).tostring());
			if (c instanceof TReturn)
				System.out.println(((TReturn) c).tostring());
			if (c instanceof PARAcode)
				System.out.println(((PARAcode) c).tostring());
			if (c instanceof Define)
				System.out.println(((Define) c).tostring());
			if (c instanceof Funstart) {
				System.out.println(((Funstart) c).tostring());
			}
		}
	}
	
	private void push(Label label) {
		stack.add(label);
	}
	private Label pop() {
		return stack.pop();
	}
	
	private void emit(Tcode c) {
		list.add(c);
	}
	
	public void transprog(Program p) {
		Program tmp = p;
		int t1 = -1, t2 = 0;
		boolean flag = true;
		while (tmp != null) {
			t1 = t2;
			if (tmp.decl instanceof Declaration) {
				transDec((Declaration) tmp.decl);
				++t2;
			}
			/*if (flag && t1 == t2) {
				emit(new Gotocode(new Label("main")));
				flag = false;
			}*/
			if (tmp.decl instanceof FunctionDefinition)
				transDec((FunctionDefinition) tmp.decl);
			tmp = tmp.list;
		}
	}
	
	public void transDec(Declaration d) {
		if (d == null) return;
		if (d.decl2 instanceof Declarators) {//typedef
			
		} else {
			Type tp = checkTypeSp((TypeSpecifier) d.decl1);
			Type ty = null;
			if (d.decl2 == null)
				return;
			// get array or para
			InitDeclarators id = (InitDeclarators)d.decl2;
			do {
				Declarator dt = id.initDeclarator.declarator;
				ty = checkDeclarator(dt, tp);
				
				env.vEnv.put(dt.plainDeclarator.sym, new IDEntry(new TID(offset), ty));
				emit(new Define(new varinfo("ID" + TID.count, ty, offset, level)));
				offset += sizeof(ty);

				// = ...
				if (id.initDeclarator.initializer != null) {
					
					//
				}
				id = id.next;
			} while (id != null);
		}
	}
	
	public int sizeof(Type t) {
		if (t instanceof ARRAY)
			return ((ARRAY) t).size * sizeof(((ARRAY) t).element);
		if (t instanceof RECORD)
			if (((RECORD) t).sou.keyw == "STRUCT")
				return sizeof(((RECORD) t).fieldType) + sizeof(((RECORD) t).tail);
		return 4;
	}
	
	public Type checkTypeSp(TypeSpecifier d) {
		if (d.decl != null) {
			if (d.structOrUnion != null) {
				TypeSpecifierDeclarators t = (TypeSpecifierDeclarators)d.decl;
				RECORD tmp = null;
				while (t != null) {
					Declarators t2 = t.declarators;
					while (t2 != null) {
						//struct *;
						if (t.typeSpecifier.structOrUnion != null
								&& ((Declarator)t2.decl1).plainDeclarator.decl != null
								&& t.typeSpecifier.decl == null) {

							Type type = new NAME(d.sym);
							type = checkDeclarator((Declarator)t2.decl1, type);
							tmp = new RECORD(d.structOrUnion, ((Declarator)t2.decl1).plainDeclarator.sym, type, tmp);
						}
						
						else {
							Type type = checkTypeSp(t.typeSpecifier);
							type = checkDeclarator((Declarator)t2.decl1, type);
							tmp = new RECORD(d.structOrUnion, ((Declarator)t2.decl1).plainDeclarator.sym, type, tmp);
						}
						t2 = (Declarators)t2.decl2;
					}
					t = t.typeSpecifierDeclarators;
				}
				
				env.tEnv.put(d.sym, tmp);
				return tmp;
			}
		}

		Env tmp = env;
		while (tmp != null) {
			Type ret = (Type)tmp.tEnv.get(d.sym);
			if (ret != null)
				return ret;
			tmp = tmp.next;
		}
		return null;
	}

	public Type checkDeclarator(Declarator dt, Type tp) {
		Type ar = tp;
		//array check;
		if (dt.constantExpressionStar != null) {
			ConstantExpressionStar tmp = dt.constantExpressionStar;
			while (tmp != null) {
				ar = new ARRAY(ar, tmp.constantExpression);
				tmp = tmp.constantExpressionStar;
			}
		}
		//check point
		TIMESStar s = (TIMESStar)dt.plainDeclarator.decl;
		while (s != null) {
			ar = new POINT(ar);
			s = s.tIMESStar;
		}
		return ar;
	}
	
	public void transDec(FunctionDefinition d) {
		int tempoffset = offset;
		offset = 0;
		
		Label fstart = new Label(((PlainDeclarator) d.decl2).sym.toString());
		emit(fstart);
		Funstart l = new Funstart();
		emit(l);
		
		Type tp = checkTypeSp((TypeSpecifier) d.decl1);//func type
		PlainDeclarator pd = (PlainDeclarator) d.decl2;
		
		TIMESStar s = (TIMESStar) pd.decl;
		while (s != null) {
			tp = new POINT(tp);
			s = s.tIMESStar;
		}
		RECORD r = checkParameters((Parameters)d.decl3);
		env.vEnv.put(pd.sym, new FunEntry(tp, pd.sym.toString(), r));
		
		transStatement((CompoundStatement) d.statements, r);
		emit(new TReturn());
		
		l.toff = offset;
		offset = tempoffset;
	}
	
	public RECORD checkParameters(Parameters p) {
		if (p == null)
			return null;
		RECORD r = null, tr = null;
		Parameters tmp = p;
		while (tmp != null) {
			PlainDeclaration pd = (PlainDeclaration)tmp.decl1;
			Type tp = checkTypeSp(pd.typeSpecifier); //para type
			tp = checkDeclarator((Declarator)pd.decl, tp);

			RECORD ur = new RECORD(new StructOrUnion("STRUCT"),((Declarator)pd.decl).plainDeclarator.sym, tp, null);
			if (r == null)
				r = ur;
			else
				tr.tail = ur;
			tr = ur;
			
			tmp = tmp.decl2;
		}
		return r;
	}
	
	public void transStatement(Statements s) {
		if (s instanceof ExpressionStatement)
			transStatement((ExpressionStatement) s);
		if (s instanceof CompoundStatement)
			transStatement((CompoundStatement) s, null);
		if (s instanceof SelectionStatement)
			transStatement((SelectionStatement) s);
		if (s instanceof IterationWhileStatement)
			transStatement((IterationWhileStatement) s);
		if (s instanceof IterationForStatement)
			transStatement((IterationForStatement) s);
		if (s instanceof JumpContinueStatement) {
			Label l1 = pop(), l2 = pop();
			emit(new Gotocode(l2));
			push(l2);
			push(l1);
		}
		if (s instanceof JumpBreakStatement) {
			Label l1 = pop(), l2 = pop();
			emit(new Gotocode(l1));
			push(l2);
			push(l2);
		}
		if (s instanceof JumpReturnStatement) {
			if (((JumpReturnStatement) s).expressions != null) {
				varinfo ret = transExp((Expression) ((JumpReturnStatement) s).expressions);
				emit(new TReturn(ret));
				return;
			}
			emit(new TReturn());
		}
	}
	
	public void transStatement(ExpressionStatement s) {
		if (s.expressions != null)
			transExp((Expression)s.expressions);
	}
	
	public void transStatement(CompoundStatement s, RECORD r) {
		Env tmp = new Env();
		tmp.next = env;
		env = tmp;
		++level;
		
		int i = 0;
		while (r != null) {//r is the parameter of a function
			env.vEnv.put(r.fieldName, new PARAEntry(new TPARA(offset), r.fieldType));
			//
			offset += 4;
			r = r.tail;
			++i;
		}
		
		DeclarationStar d = (DeclarationStar)s.declaration;
		while (d != null) {
			transDec((Declaration)d.decl);
			d = d.declarationStar;
		}
		
		StatementStar st = (StatementStar) s.statements;
		while (st != null) {
			transStatement(st.statements);
			st = st.statementStar;
		}
		
		--level;
		env = env.next;
	}

	public void transStatement(SelectionStatement s) {
		Label l = new Label();
		varinfo r = transExp((Expression) s.expressions);
		
		emit(new NotIfcode(r, l));
		transStatement(s.statements1);

		emit(l);
		
		if (s.statements2 != null)
			transStatement(s.statements2);
	}
	
	public void transStatement(IterationWhileStatement s) {
		Label While = new Label();
		emit(While);
		varinfo ret = transExp((Expression)s.expressions);

		Label next = new Label();
		push(While);
		push(next);
		emit(new NotIfcode(ret, next));
		
		transStatement(s.statements);
		emit(new Gotocode(While));
		
		emit(next);
		pop();
		pop();
	}
	
	public void transStatement(IterationForStatement s) {
		transExp((Expression)s.expressions1);
		Label For = new Label(), next = new Label(), Continue = new Label();
		push(Continue);
		push(next);
		
		emit(For);
		varinfo ret = transExp((Expression)s.expressions2);
		emit(new NotIfcode(ret, next));
		
		transStatement(s.statements);
		
		emit(Continue);
		transExp((Expression)s.expressions3);
		emit(new Gotocode(For));
		emit(next);
		pop();
		pop();
	}
	
	public varinfo transExp(Expression ex) {
		varinfo ret = null;
		Expression tmp = ex;
		while (tmp != null) {
			ret = transAssignmentExpression(tmp.assignmentExpression);
			tmp = tmp.next;
		}
		return ret;
	}
	
	public varinfo transAssignmentExpression(AssignmentExpression ex) {
		varinfo ret = null;
		if (ex instanceof LogicalOrExpression)
			return transLogicalOrExpression((LogicalOrExpression) ex);
		
		if (ex.assignmentExpression != null) {
			ret = transAssignmentExpression(ex.assignmentExpression); //r1 op ret
			varinfo r1 = transUnaryExpressionALL(ex.unaryExpressionAll);
			
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.ASSIGN)
				emit(new InCode(r1, ret, "+", new varinfo("#0", Type.INT, 0, level)));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.MUL_ASSIGN)
				emit(new InCode(r1, r1, "*", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.DIV_ASSIGN)
				emit(new InCode(r1, r1, "/", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.MOD_ASSIGN)
				emit(new InCode(r1, r1, "%", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.ADD_ASSIGN)
				emit(new InCode(r1, r1, "+", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.SUB_ASSIGN)
				emit(new InCode(r1, r1, "-", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.SHL_ASSIGN)
				emit(new InCode(r1, r1, "<<", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.SHR_ASSIGN)
				emit(new InCode(r1, r1, ">>", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.AND_ASSIGN)
				emit(new InCode(r1, r1, "&", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.XOR_ASSIGN)
				emit(new InCode(r1, r1, "^", ret));
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.OR_ASSIGN)
				emit(new InCode(r1, r1, "|", ret));
			return r1;
		}
		return null;
	}
	
	public varinfo transLogicalOrExpression(LogicalOrExpression ex) {
		varinfo ret = transLogicalAndExpression(ex.logicalAndExpression);
		OrLogicalAndExpressionStar l = ex.orLogicalAndExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transLogicalAndExpression(l.head);
			emit(new InCode(ret, ret, "||", t));
			l = l.next;
		}
		return ret;
	}
	
	public varinfo transLogicalAndExpression(LogicalAndExpression ex) {
		varinfo ret = transInclusiveOrExpression(ex.inclusiveOrExpression);
		AndInclusiveOrExpressionStar l = ex.andInclusiveOrExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transInclusiveOrExpression(l.inclusiveOrExpression);
			emit(new InCode(ret, ret, "&&", t));
			l = l.andInclusiveOrExpressionStar;
		}
		return ret;
	}
	
	public varinfo transInclusiveOrExpression(InclusiveOrExpression ex) {
		varinfo ret = transExclusiveOrExpression(ex.exclusiveOrExpression);
		OporExclusiveOrExpressionStar l = ex.oporExclusiveOrExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transExclusiveOrExpression(l.exclusiveOrExpression);
			if (isNum(ret) && isNum(t))
				ret = new varinfo("#" + (getNum(ret) | getNum(t)), Type.INT, 0, level);
			else {
				varinfo tmp = ret;
				ret = needNew(ret);
				emit(new InCode(ret, tmp, "|", t));
			}
			l = l.oporExclusiveOrExpressionStar;
		}
		return ret;
	}

	public varinfo transExclusiveOrExpression(ExclusiveOrExpression ex) {
		varinfo ret = transAndExpression(ex.andExpression);
		OPXORAndExpressionStar l = ex.oPXORAndExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transAndExpression(l.andExpression);
			if (isNum(ret) && isNum(t))
				ret = new varinfo("#" + (getNum(ret) ^ getNum(t)), Type.INT, 0, level);
			else {
				varinfo tmp = ret;
				ret = needNew(ret);
				emit(new InCode(ret, tmp, "^", t));
			}
			l = l.oPXORAndExpressionStar;
		}
		return ret;
	}
	
	public varinfo transAndExpression(AndExpression ex) {
		varinfo ret = transEqualityExpression(ex.equalityExpression);
		OPANDEqualityExpressionStar l = ex.oPANDEqualityExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transEqualityExpression(l.equalityExpression);
			if (isNum(ret) && isNum(t))
				ret = new varinfo("#" + (getNum(ret) & getNum(t)), Type.INT, 0, level);
			else {
				varinfo tmp = ret;
				ret = needNew(ret);
				emit(new InCode(ret, tmp, "&", t));
			}
			l = l.oPANDEqualityExpressionStar;
		}
		return ret;
	}

	public varinfo transEqualityExpression(EqualityExpression ex) {
		varinfo ret = transRelationalExpression(ex.relationalExpression);
		EqualityOperatorRelationalExpressionStar l = ex.equalityOperatorRelationalExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transRelationalExpression(l.relationalExpression);
			if (l.equalityOperator.equalityOperator == EqualityOperator.Type.EQ)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) == getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "==", t));
				}
			if (l.equalityOperator.equalityOperator == EqualityOperator.Type.NE)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) != getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "!=", t));
				}
			l = l.equalityOperatorRelationalExpressionStar;
		}
		return ret;
	}

	public varinfo transRelationalExpression(RelationalExpression ex) {
		varinfo ret = transShiftExpression(ex.shiftExpression);
		RelationalOperatorShiftExpressionStar l = ex.relationalOperatorShiftExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transShiftExpression(l.shiftExpression);
			if (l.relationalOperator.relationalOperator == RelationalOperator.Type.GT)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) > getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, ">", t));
				}
			if (l.relationalOperator.relationalOperator == RelationalOperator.Type.GE)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) >= getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, ">=", t));
				}
			if (l.relationalOperator.relationalOperator == RelationalOperator.Type.LT)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) < getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "<", t));
				}
			if (l.relationalOperator.relationalOperator == RelationalOperator.Type.LE)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) <= getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "<=", t));
				}
			l = l.relationalOperatorShiftExpressionStar;
		}
		return ret;
	}

	public varinfo transShiftExpression(ShiftExpression ex) {
		varinfo ret = transAdditiveExpression(ex.additiveExpression);
		ShiftOperatorAdditiveExpressionStar l = ex.shiftOperatorAdditiveExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transAdditiveExpression(l.additiveExpression);
			if (l.shiftOperator.shiftOperator == ShiftOperator.Type.SHL) {
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) << getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "<<", t));
				}
			}
			if (l.shiftOperator.shiftOperator == ShiftOperator.Type.SHR)  {
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) >> getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, ">>", t));
				}
			}
			l = l.shiftOperatorAdditiveExpressionStar;
		}
		return ret;
	}
	
	public varinfo transAdditiveExpression(AdditiveExpression ex) {
		varinfo ret = transMultiplicativeExpression(ex.multiplicativeExpression);
		AdditiveOperatorMultiplicativeExpressionStar l = ex.additiveOperatorMultiplicativeExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transMultiplicativeExpression(l.multiplicativeExpression);
			if (l.additiveOperator.additiveOperator == AdditiveOperator.Type.PLUS) {
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) + getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "+", t));
				}
			}
			if (l.additiveOperator.additiveOperator == AdditiveOperator.Type.MINUS) {
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) - getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "-", t));
				}
			}
			l = l.additiveOperatorMultiplicativeExpressionStar;
		}
		return ret;
	}
	
	public varinfo transMultiplicativeExpression(MultiplicativeExpression ex) {
		varinfo ret = transCastExpression(ex.castExpression);
		MultiOperatorCastExpressionStar l = ex.multiOperatorCastExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			varinfo t = transCastExpression(l.castExpression);
			if (l.multiplicativeOperator.multiplicativeOperator == MultiplicativeOperator.Type.TIMES)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) * getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "*", t));
				}
			if (l.multiplicativeOperator.multiplicativeOperator == MultiplicativeOperator.Type.DIVIDE)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) / getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "/", t));
				}
			if (l.multiplicativeOperator.multiplicativeOperator == MultiplicativeOperator.Type.MOD)
				if (isNum(ret) && isNum(t))
					ret = new varinfo("#" + (getNum(ret) % getNum(t)), Type.INT, 0, level);
				else {
					varinfo tmp = ret;
					ret = needNew(ret);
					emit(new InCode(ret, tmp, "%", t));
				}
			l = l.multiOperatorCastExpressionStar;
		}
		return ret;
	}

	public varinfo transCastExpression(CastExpression ex) {
		if (ex instanceof UnaryExpressionAll)
			return transUnaryExpressionALL((UnaryExpressionAll) ex);
		else
			return transCastExpression(ex.FacastExpression);
	}
	
	public varinfo transUnaryExpressionALL(UnaryExpressionAll ex) {
		if (ex instanceof PostfixExpression)
			return transPostfixExpression((PostfixExpression) ex);
		if (ex instanceof UnaryExpression)
			return transUnaryExpression((UnaryExpression) ex);
		if (ex instanceof UnaryExpressionToCastExpression)
			return transUnaryExpressionToCastExpression((UnaryExpressionToCastExpression) ex);
		if (ex instanceof UnaryExpressionToTypeName)
			return transUnaryExpressionToTypeName((UnaryExpressionToTypeName) ex);
		return null;
	}
	
	public void transArg(Arguments r) {
		while (r != null) {
			varinfo ret = transAssignmentExpression(r.assignmentExpression);
			emit(new PARAcode(ret));
			
			//offset += sizeof(ret.type);
			r = r.next;
		}
	}

	public varinfo transPostfixExpression(PostfixExpression ex) {
		if (ex.primaryExpression.sym == null)
			return transPrimaryExpression(ex.primaryExpression);
		
		Entry en =  transSymbol(ex.primaryExpression.sym);
		if (en instanceof FunEntry) {
			Label l = new Label();
			emit(new PARAcode(new varinfo("START", Type.INT, 0, level), l));
			if (ex.postfixStar.postfix != null)
				if (ex.postfixStar.postfix instanceof Arguments) {
					transArg((Arguments)ex.postfixStar.postfix);
				}
			TRegister k = new TRegister();
			varinfo vs = new varinfo(k.to(), Type.INT, offset, level);
			emit(new Define(vs));
			emit(new Callcode(((FunEntry) en).name, vs, l));
			emit(l);
			return vs;
		}
		
		if (en instanceof IDEntry) {
			IDEntry e = (IDEntry) en;
			if (ex.postfixStar == null)
				return new varinfo(e.to() + "(" + ex.primaryExpression.sym.toString() + ")",
							Type.INT, e.name.offset, level);
			else {
			//
				varinfo off = getpostfix(e.ty, ex.postfixStar);
				
				return new varinfo(e.to() + "(" + ex.primaryExpression.sym.toString() + ")",
						Type.VOID, off, level);
			}
		}
		if (en instanceof PARAEntry) {
			PARAEntry e = (PARAEntry) en;
			if (ex.postfixStar == null)
				return new varinfo(e.to() + "(" + ex.primaryExpression.sym.toString() + ")",
						Type.INT, e.name.offset, level);
			else {
			//
			
				return new varinfo(e.to() + "(" + ex.primaryExpression.sym.toString() + ")",
						Type.INT, e.name.offset, level);
			}
		}
		return null;
	}
	
	public varinfo getpostfix(Type ty, PostfixStar p) {
		varinfo ret = needNew(null);
		emit(new InCode(ret, new varinfo("#0", Type.INT, 0, level), "+", new varinfo("#0", Type.INT, 0, level)));
		while (p != null) {
			if (ty instanceof ARRAY) {
				ARRAY t = (ARRAY) ty;
				varinfo tmp = transExp((Expression) p.postfix);
				emit(new InCode(ret, ret, "+", tmp));
				if (((ARRAY) ty).element instanceof ARRAY) {
					emit(new InCode(ret, ret, "*", new varinfo("#" + ((ARRAY) ((ARRAY) ty).element).size, Type.INT, 0, level)));
					ty = t.element;
				}
				else
					emit(new InCode(ret, ret, "*", SizeOf(((ARRAY) ty).element)));
			}
			
			//
			p = p.postfixStar;
		}
		return ret;
	}
	
	int stringnum = 0;
	
	public varinfo transPrimaryExpression(PrimaryExpression ex) {
		if (ex.expressions instanceof Num)
			return new varinfo("#" + ((Num) ex.expressions).numvalue, Type.INT, 0, level);
		if (ex.expressions instanceof Char)
			return new varinfo("#" + ((Char) ex.expressions).charvalue, Type.CHAR, 0, level);
		if (ex.expressions instanceof SString) {
			String t = ((SString) ex.expressions).st;
			if (!str.contains(t)) {
				str.add(t);
			}
			return new varinfo(t, Type.STRING, ++stringnum, level);
		}
		if (ex.expressions instanceof Expression)
			return transExp((Expression) ex.expressions);
		return null;
	}
	
	public Entry transSymbol(Symbol s) {
		Env e = env;
		while (e != null) {
			Entry tmp = (Entry)e.vEnv.get(s);
			if (tmp != null)
				return tmp;
			e = e.next;
		}
		return null;
	}
	
	public varinfo transUnaryExpression(UnaryExpression ex) {
		varinfo ret = transUnaryExpressionALL(ex.unaryExpressionAll);
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.INC)
			emit(new IICode(ret, "++", ret));
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.DEC)
			emit(new IICode(ret, "--", ret));
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.SIZEOF)
			return SizeOf(getType(ret));
		return ret;
	}
	
	public varinfo transUnaryExpressionToCastExpression(UnaryExpressionToCastExpression ex) {
		varinfo ret = transCastExpression(ex.castExpression);
		TRegister k = new TRegister();
		varinfo u = new varinfo(k.to(), Type.INT, offset, level);
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.OPAND)
			emit(new IICode(u, "&", ret));
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.TIMES)
			emit(new IICode(u, "*", ret));
		//UnaryOperator.Type.PLUS don't need to be thought
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.MINUS)
			emit(new InCode(u, new varinfo("#0", Type.INT, 0, level), "-", ret));
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.NEGATE)
			emit(new IICode(u, "~", ret));
		if (ex.unaryOperator.unaryOperator == UnaryOperator.Type.NOT)
			emit(new IICode(u, "!", ret));
		return u;
	}
	
	public varinfo transUnaryExpressionToTypeName(UnaryExpressionToTypeName ex) {
		return SizeOf(checkTypeSp(ex.typeName.typeSpecifier));
	}
	
	public varinfo SizeOf(Type t) {
		//TRegister k = new TRegister();
		//emit(new InCode(k.to(), "#4", "+", "#0"));
		return new varinfo("#4", Type.INT, 0, level);
	}
	public Type getType(varinfo t) {
		return Type.INT;
	}

	public boolean isNum(varinfo v) {
		String s = v.tostring();
		if (s.charAt(0) != '#')
			return false;
		for (int i = 1; i < s.length(); ++i)
			if (s.charAt(i) < '0' || '9' < s.charAt(i))
				return false;
		return true;
	}
	
	public int getNum(varinfo v) {
		String s = v.tostring();
		int ret = 0;
		for (int i = 1; i < s.length(); ++i)
			ret = ret * 10 + (s.charAt(i) - '0');
		return ret;
	}
	
	public boolean checkid(String s) {
		if (s.indexOf("ID") == 0 || s.indexOf("PARA") == 0 || s.indexOf("#") == 0)
			return true;
		return false;
	}
	
	public varinfo needNew(varinfo v) {
		String s = null;
		if (v != null)
			s = v.tostring();
		if (v == null || checkid(s)) {
			TRegister k = new TRegister();
			varinfo vs = new varinfo(k.to(), Type.INT, offset, level);
			offset += 4;
			emit(new Define(vs));
			return vs;
		}
		return v;
	}
}
