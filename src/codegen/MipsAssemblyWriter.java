package codegen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Types.ARRAY;
import Types.RECORD;
import Types.Type;

import translate.*;
import Types.*;

public class MipsAssemblyWriter {

	public void load(String reg, varinfo v) {
		if (v.type == Type.STRING) {
			emit("la %s, str_%d", reg, v.offset);
			return;
		}
		if (v.name.charAt(0) == '#') {
			emit("li %s, %s", reg, v.name.substring(1, v.name.length()));
			return;
		}
		if (v.level == 0) {
			if (v.type == Type.VOID) {
				load("$t3", v.off);
				emit("la $gp, data_%s", v.name);
				emit("add $t3, $gp, $t3");
				emit("lw %s, 0($t3)", reg);
			}
			else {
				if (v.type instanceof POINT) {
					emit("la $gp, data_%s", v.name);
					emit("lw $t3, 0($gp)");
					emit("lw %s, 0($t3)", reg);
					return;
				}
				emit("la $gp, data_%s", v.name);
				emit("lw %s, 0($gp)", reg);
			}
		}
		else {
			if (v.type == Type.VOID) {
				load("$t3", v.off);
				emit("sub $t3, $fp, $t3");
				emit("lw %s, %d($t3)", reg, -v.offset);
			}
			else {
				if (v.type instanceof POINT) {
					emit("lw $t3, %d($fp)", -v.offset);
					emit("lw %s, 0($t3)", reg);
					return;
				}
				emit("lw %s, %d($fp)", reg, -v.offset);
			}
		}
	}
	
	public void store(String reg, varinfo v) {
		if (v.level == 0) {
			if (v.type == Type.VOID) {
				load("$t3", v.off);
				emit("la $gp, data_%s", v.name);
				emit("add $t3, $gp, $t3");
				emit("sw %s, 0($t3)", reg);
			}
			else {
				if (v.type instanceof POINT) {
					emit("la $gp, data_%s", v.name);
					emit("lw $t3, 0($gp)");
					emit("sw %s, 0($t3)", reg);
					return;
				}
				emit("la $gp, data_%s", v.name);
				emit("sw %s, 0($gp)", reg);
			}
		}
		else {
			if (v.type == Type.VOID) {
				load("$t3", v.off);
				emit("sub $t3, $fp, $t3");
				emit("sw %s, %d($t3)", reg, -v.offset);
			}
			else {
				if (v.type instanceof POINT) {
					emit("lw $t3, %d($fp)", -v.offset);
					emit("sw %s, 0($t3)", reg);
					return;
				}
				emit("sw %s, %d($fp)", reg, -v.offset);
			}
		}
	}

	public void move(Temp dest, Temp src) {
		emit("lw $t0, %d($fp)", src.offset);
		emit("sw $t0, %d($fp)", dest.offset);
	}

	public void and(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("and $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void add(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("add $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void beq(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("beq $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void sub(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sub $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void mul(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("mul $t0, $t1, $t2");
		store("$t0", dest);
	}
	
	public void rem(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("rem $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void div(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("div $t0, $t1, $t2");
		store("$t0", dest);
	}
	

	public void sne(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sne $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void slt(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("slt $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void sgt(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sgt $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void sle(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sle $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void sge(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sge $t0, $t1, $t2");
		store("$t0", dest);
	}
	
	public void seq(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("seq $t0, $t1, $t2");
		store("$t0", dest);
	}
	
	public void sll(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sll $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void sra(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("sra $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void or(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("or $t0, $t1, $t2");
		store("$t0", dest);
	}
	
	public void xor(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("xor $t0, $t1, $t2");
		store("$t0", dest);
	}
	
	public void emit(Label label) {
		emit(label.tostring() + ":");
	}

	public void jump(Label label) {
		emit("j %s", label.tostring());
	}

	public void jz(NotIfcode cond) {
		load("$t0", cond.t1);
		emit("beqz $t0, %s", cond.label.tostring());
	}

	private List<String> lines = new ArrayList<String>();

	public void IICode(IICode c) {
		if (c.op == "++") {
			load("$a1", c.t3);
			emit("addi $a1, $a1, 1");
			store("$a1", c.t1);
		} else if (c.op == "--") {
			load("$a1", c.t3);
			emit("addi $a1, $a1, -1");
			store("$a1", c.t1);
		} else if (c.op == "&") {
			load("$a1", c.t3);
			emit("lw $a1, 0($a1)");
			store("$a1", c.t1);
		} else if (c.op == "*") {
			load("$a1", c.t3);
			store("$a1", c.t1);
		} else if (c.op == "!" || c.op == "~") {
			load("$a1", c.t3);
			emit("not $a1, $a1");
			store("$a1", c.t1);
		} else emit("%s", c.op);
	}
	
	public void InCode(InCode c) {
		if (c.op == "+")
			add(c.t1, c.t2, c.t3);
		else if (c.op == "-")
			sub(c.t1, c.t2, c.t3);
		else if (c.op == "*")
			mul(c.t1, c.t2, c.t3);
		else if (c.op == "/")
			div(c.t1, c.t2, c.t3);
		else if (c.op == "%")
			rem(c.t1, c.t2, c.t3);
		else if (c.op == "&&" || c.op == "&")
			and(c.t1, c.t2, c.t3);
		else if (c.op == "||" || c.op == "|")
			or(c.t1, c.t2, c.t3);
		else if (c.op == "^")
			xor(c.t1, c.t2, c.t3);
		else if (c.op == "==")
			seq(c.t1, c.t2, c.t3);
		else if (c.op == "!=")
			sne(c.t1, c.t2, c.t3);
		else if (c.op == "<")
			slt(c.t1, c.t2, c.t3);
		else if (c.op == "<=")
			sle(c.t1, c.t2, c.t3);
		else if (c.op == ">")
			sgt(c.t1, c.t2, c.t3);
		else if (c.op == ">=")
			sge(c.t1, c.t2, c.t3);
		else if (c.op == "<<")
			sll(c.t1, c.t2, c.t3);
		else if (c.op == ">>")
			sra(c.t1, c.t2, c.t3);
		else emit("%s", c.op);
	}
	
	public void Callcode(Callcode c) {
		emit("add $fp, $sp, $0");
		emit("jal Label_%s", c.t1);
		store("$v0", c.t2);
	}
	
	public void Funstart(Funstart c) {
		emit("sw $31, 4($fp)");
		emit("addi $sp, $sp, -%d", c.toff);
	}
	
	public void TReturn(TReturn c) {
		if (c.t1 != null)
			load("$v0", c.t1);
		emit("addi $sp, $fp, 0");
		emit("lw $t0, 8($sp)");
		emit("lw $31, 4($sp)");
		emit("addi $sp, $sp, 8");
		emit("add $fp, $sp, $t0");
		
		emit("jr $31");
	}
	
	int p = 0;
	public void PARAcode(PARAcode c) {
		if (c.t1.name.equals("START")) {
			p = 0;
			emit("addi $sp, $sp, -4");
			emit("sub $t0, $fp, $sp");
			emit("sw $t0, 0($sp)");
			emit("addi $sp, $sp, -8");
			return;
		}
		if (p < 4) {
			load("$a" + p, c.t1);
			emit("sw $a%d, -%d($sp)", p, p * 4);
			++p;
			return;
		}
		load("$t0", c.t1);
		emit("sw $t0, -%d($sp)", p * 4);
		++p;
	}
	/*
	public void Define(Define c) {
		if (c.v.level == 0) {
			emit(".globl data_%s", c.v.name);
			emit("data_%s:", c.v.name);
			if (c.v.type instanceof ARRAY)
				emit(".space %d", ((ARRAY)c.v.type).size * 4);
			else
				emit(".space %d", 4);
		}
	}*/
	
	private void emit(String fmt, Object... objects) {
		lines.add(String.format(fmt, objects));
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (String line : lines) {
			if (!line.startsWith(".") && !line.endsWith(":")) {
				sb.append('\t');
			}
			sb.append(line);
			sb.append('\n');
		}
		return sb.toString();
	}

	public int sizeof(Type t) {
		if (t instanceof ARRAY)
			return ((ARRAY) t).size * sizeof(((ARRAY) t).element);
		if (t instanceof RECORD)
			if (((RECORD) t).sou.keyw == "STRUCT")
				return sizeof(((RECORD) t).fieldType) + sizeof(((RECORD) t).tail);
		return 4;
	}
	
	public void emitPrologue(List<String> str, List<InCode> pre, List<Define> glo) {
		emit(".data");
		int i = 0;
		for (String s : str) {
			emit(".globl str_%d", i);
			emit("str_%d: .asciiz \"%s\"", i++, s);
		}

		emit(".align 2");
		emit(".globl data_gp");
		
		emit("data_gp:");
		
		for (Define c : glo) {
			emit(".globl data_%s", c.v.name);
			int o = 4;
			if (c.v.type instanceof ARRAY)
				o = sizeof((ARRAY) c.v.type);
			emit("data_%s: .space %d", c.v.name, o);
		}
			
		
		emit(".text");
		emit(".align 2");
		emit(".globl main");
		emit("main:");
		//emit("move $gp, $sp     # set global pointer (unused)");
		emit("la $gp, data_gp");
		emit("sw $0, 0($sp)");
		emit("sw $0, -4($sp)");
		emit("addi $sp, $sp, -8 # move up a cell");
		emit("move $fp, $sp     # start using memory here");
		
		for (InCode c : pre) {
			InCode(c);
		}
		
		emit("jal Label_main");
		emit("end_main:");
		emit("li $v0, 10        # exit");
		emit("syscall");
	}

	public void emitEpilogue() {
	}
}
