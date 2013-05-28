package Types;

import ast.*;

public class constint{
	public int value = 0;
	public constint() {
	}

	public int transConstantExpression(ConstantExpression ex) {
		return transLogicalOrExpression(ex.logicalOrExpression);
	}
	
	public int calc(Expression ex) {
		int ret = 0;
		Expression tmp = ex;
		while (tmp != null) {
			ret = transAssignmentExpression(tmp.assignmentExpression);
			tmp = tmp.next;
		}
		return ret;
	}
	
	public int transAssignmentExpression(AssignmentExpression ex) {
		int ret = 0;
		if (ex instanceof LogicalOrExpression)
			return transLogicalOrExpression((LogicalOrExpression) ex);
		
		if (ex.assignmentExpression != null) {
			ret = transAssignmentExpression(ex.assignmentExpression); //r1 op ret
			int r1 = transUnaryExpressionALL(ex.unaryExpressionAll);
			
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.ASSIGN)
				return r1;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.MUL_ASSIGN)
				return r1 * ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.DIV_ASSIGN)
				return r1 / ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.MOD_ASSIGN)
				return r1 % ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.ADD_ASSIGN)
				return r1 + ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.SUB_ASSIGN)
				return r1 - ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.SHL_ASSIGN)
				return r1 << ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.SHR_ASSIGN)
				return r1 >> ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.AND_ASSIGN)
				return r1 & ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.XOR_ASSIGN)
				return r1 ^ ret;
			if (ex.assignmentOperator.assignmentOperator == AssignmentOperator.Type.OR_ASSIGN)
				return r1 | ret;
		}
		return 0;
	}
	
	public int transLogicalOrExpression(LogicalOrExpression ex) {
		int ret = transLogicalAndExpression(ex.logicalAndExpression);
		return ret;
	}
	
	public int transLogicalAndExpression(LogicalAndExpression ex) {
		int ret = transInclusiveOrExpression(ex.inclusiveOrExpression);
		return ret;
	}
	
	public int transInclusiveOrExpression(InclusiveOrExpression ex) {
		int ret = transExclusiveOrExpression(ex.exclusiveOrExpression);
		OporExclusiveOrExpressionStar l = ex.oporExclusiveOrExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			int t = transExclusiveOrExpression(l.exclusiveOrExpression);
			ret = ret | t;
			l = l.oporExclusiveOrExpressionStar;
		}
		return ret;
	}

	public int transExclusiveOrExpression(ExclusiveOrExpression ex) {
		int ret = transAndExpression(ex.andExpression);
		OPXORAndExpressionStar l = ex.oPXORAndExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			int t = transAndExpression(l.andExpression);
			ret = ret ^ t;
			l = l.oPXORAndExpressionStar;
		}
		return ret;
	}
	
	public int transAndExpression(AndExpression ex) {
		int ret = transEqualityExpression(ex.equalityExpression);
		OPANDEqualityExpressionStar l = ex.oPANDEqualityExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			int t = transEqualityExpression(l.equalityExpression);
			ret = ret & t;
			l = l.oPANDEqualityExpressionStar;
		}
		return ret;
	}

	public int transEqualityExpression(EqualityExpression ex) {
		int ret = transRelationalExpression(ex.relationalExpression);
		return ret;
	}

	public int transRelationalExpression(RelationalExpression ex) {
		int ret = transShiftExpression(ex.shiftExpression);
		return ret;
	}

	public int transShiftExpression(ShiftExpression ex) {
		int ret = transAdditiveExpression(ex.additiveExpression);
		ShiftOperatorAdditiveExpressionStar l = ex.shiftOperatorAdditiveExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			int t = transAdditiveExpression(l.additiveExpression);
			if (l.shiftOperator.shiftOperator == ShiftOperator.Type.SHL)
				ret = ret << t;
			if (l.shiftOperator.shiftOperator == ShiftOperator.Type.SHR)
				ret = ret >> t;
			l = l.shiftOperatorAdditiveExpressionStar;
		}
		return ret;
	}
	
	public int transAdditiveExpression(AdditiveExpression ex) {
		int ret = transMultiplicativeExpression(ex.multiplicativeExpression);
		AdditiveOperatorMultiplicativeExpressionStar l = ex.additiveOperatorMultiplicativeExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			int t = transMultiplicativeExpression(l.multiplicativeExpression);
			if (l.additiveOperator.additiveOperator == AdditiveOperator.Type.PLUS)
				ret = ret + t;
			if (l.additiveOperator.additiveOperator == AdditiveOperator.Type.MINUS)
				ret = ret - t;
			l = l.additiveOperatorMultiplicativeExpressionStar;
		}
		return ret;
	}
	
	public int transMultiplicativeExpression(MultiplicativeExpression ex) {
		int ret = transCastExpression(ex.castExpression);
		MultiOperatorCastExpressionStar l = ex.multiOperatorCastExpressionStar;
		if (l == null)
			return ret;
		while (l != null) {
			int t = transCastExpression(l.castExpression);
			if (l.multiplicativeOperator.multiplicativeOperator == MultiplicativeOperator.Type.TIMES)
				ret = ret * t;
			if (l.multiplicativeOperator.multiplicativeOperator == MultiplicativeOperator.Type.DIVIDE)
				ret = ret / t;
			if (l.multiplicativeOperator.multiplicativeOperator == MultiplicativeOperator.Type.MOD)
				ret = ret % t;
			l = l.multiOperatorCastExpressionStar;
		}
		return ret;
	}

	public int transCastExpression(CastExpression ex) {
		if (ex instanceof UnaryExpressionAll)
			return transUnaryExpressionALL((UnaryExpressionAll) ex);
		else
			return transCastExpression(ex.FacastExpression);
	}
	
	public int transUnaryExpressionALL(UnaryExpressionAll ex) {
		if (ex instanceof PostfixExpression)
			return transPostfixExpression((PostfixExpression) ex);
		return 0;
	}
	
	public int transPostfixExpression(PostfixExpression ex) {
		if (ex.primaryExpression.sym == null)
			return transPrimaryExpression(ex.primaryExpression);
		return 0;
	}
	
	public int transPrimaryExpression(PrimaryExpression ex) {
		if (ex.expressions instanceof Num)
			return ((Num) ex.expressions).numvalue;
		if (ex.expressions instanceof Expression)
			return calc((Expression) ex.expressions);
		return 0;
	}
}
