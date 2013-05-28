package codegen;

import java.util.ArrayList;
import java.util.List;

import Types.Type;

import translate.*;

public class MipsAssemblyWriter {

	public void returnValue(Temp src) {
		emit("lw $v0, %d($fp)", src.offset);
	}

	public void load(String reg, varinfo v) {
		if (v.type == Type.STRING) {
			emit("li %s, str_%d", reg, v.level);
			return;
		}
		if (v.name.charAt(0) == '#') {
			emit("li %s, %s", reg, v.name.substring(1, v.name.length()));
			return;
		}
		if (v.level == 0) {
			if (v.type == Type.VOID) {
				load(reg, v.off);
				emit("add %s, $gp, %s", reg, reg);
				emit("lw %s, %d($%s)", reg, v.offset, reg);
			}
			else
				emit("lw %s, %d($gp)", reg, v.offset);
		}
		else {
			if (v.type == Type.VOID) {
				load(reg, v.off);
				emit("sub %s, $fp, %s", reg, reg);
				emit("lw %s, %d($%s)", reg, -v.offset, reg);
			}
			else
				emit("lw %s, %d($fp)", reg, -v.offset);
		}
	}
	
	public void store(Temp dest, int src) {
		emit("li $t0, %d", src);
		emit("sw $t0, %d($fp)", dest.offset);
	}
	
	public void store(String reg, varinfo v) {
		if (v.level == 0) {
			if (v.type == Type.VOID) {
				load(reg, v.off);
				emit("add %s, $gp, %s", reg, reg);
				emit("sw %s, %d($%s)", reg, v.offset, reg);
			}
			else
				emit("sw %s, %d($gp)", reg, v.offset);
		}
		else {
			if (v.type == Type.VOID) {
				load(reg, v.off);
				emit("sub %s, $fp, %s", reg, reg);
				emit("sw %s, %d($%s)", reg, -v.offset, reg);
			}
			else
				emit("sw %s, %d($fp)", reg, -v.offset);
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

	public void div(Temp dest, Temp src1, Temp src2) {
		emit("lw $t1, %d($fp)", src1.offset);
		emit("lw $t2, %d($fp)", src2.offset);
		emit("div $t0, $t1, $t2");
		emit("sw $t0, %d($fp)", dest.offset);
	}

	public void seq(Temp dest, Temp src1, Temp src2) {
		emit("lw $t1, %d($fp)", src1.offset);
		emit("lw $t2, %d($fp)", src2.offset);
		emit("seq $t0, $t1, $t2");
		emit("sw $t0, %d($fp)", dest.offset);
	}

	public void sne(Temp dest, Temp src1, Temp src2) {
		emit("lw $t1, %d($fp)", src1.offset);
		emit("lw $t2, %d($fp)", src2.offset);
		emit("sne $t0, $t1, $t2");
		emit("sw $t0, %d($fp)", dest.offset);
	}

	public void slt(varinfo dest, varinfo src1, varinfo src2) {
		load("$t1", src1);
		load("$t2", src2);
		emit("slt $t0, $t1, $t2");
		store("$t0", dest);
	}

	public void sgt(Temp dest, Temp src1, Temp src2) {
		emit("lw $t1, %d($fp)", src1.offset);
		emit("lw $t2, %d($fp)", src2.offset);
		emit("sgt $t0, $t1, $t2");
		emit("sw $t0, %d($fp)", dest.offset);
	}

	public void sle(Temp dest, Temp src1, Temp src2) {
		emit("lw $t1, %d($fp)", src1.offset);
		emit("lw $t2, %d($fp)", src2.offset);
		emit("sle $t0, $t1, $t2");
		emit("sw $t0, %d($fp)", dest.offset);
	}

	public void sge(Temp dest, Temp src1, Temp src2) {
		emit("lw $t1, %d($fp)", src1.offset);
		emit("lw $t2, %d($fp)", src2.offset);
		emit("sge $t0, $t1, $t2");
		emit("sw $t0, %d($fp)", dest.offset);
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
		
	}
	
	public void InCode(InCode c) {
		if (c.op == "+") {
			add(c.t1, c.t2, c.t3);
		} else
		if (c.op == "-") {
			sub(c.t1, c.t2, c.t3);
		} else
		if (c.op == "*") {
			mul(c.t1, c.t2, c.t3);
		} else
		if (c.op == "<") {
			slt(c.t1, c.t2, c.t3);
		} else
		if (c.op == "&&") {
			and(c.t1, c.t2, c.t3);
		} else
		if (c.op == "==") {
			beq(c.t1, c.t2, c.t3);
		} else {
			emit("%s", c.op);
		}
	}
	
	public void Callcode(Callcode c) {
		emit("j Label_%s", c.t1);
	}
	
	public void Funstart(Funstart c) {
		emit("sw $31, 0($sp)");
	}
	
	public void TReturn(TReturn c) {
		if (c.t1 != null)
			load("$v0", c.t1);
		emit("addi $sp, $fp, 0");
		emit("lw $t0, 0($sp)");
		emit("add $fp, $fp, $t0");
		
		emit("lw $31, 0($fp)");
		emit("jr $31");
	}
	
	int p = 0;
	public void PARAcode(PARAcode c) {
		if (c.t1.name.equals("START")) {
			p = 0;
			emit("addi $sp, $sp, -4");
			emit("sub $t0, $fp, $sp");
			emit("sw $t0, 4($sp)");
			emit("add $fp, $0, $sp");
			return;
		}
		if (p < 4) {
			load("$a" + p, c.t1);
			emit("addi $sp, $sp, -4");
			emit("sw $a%d, 0($sp)", p);
			++p;
		}
		load("$t0", c.t1);
		emit("addi $sp, $sp, -4");
		emit("sw $t0, 0($sp)");
	}
	
	public void Define(Define c) {
		
	}
	
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

	public void emitPrologue(List<String> str) {
		emit(".data");
		int i = 0;
		for (String s : str)
			emit("str_%d : \"%s\"", ++i, s);
		
		emit(".text");
		emit(".align 2");
		emit(".globl main");
		emit("main:");
		Label l = new Label("end_main");
		emit("addi $sp, $sp, -4 # move up a cell");
		emit("move $fp, $sp     # start using memory here");
		emit("move $gp, $sp     # set global pointer (unused)");
		emit("jal Label_main");
		emit("end_main:");
		emit("li $v0, 10        # exit");
		emit("syscall");
	}

	public void emitEpilogue() {
		emit("Label_printf:");
		emit("lw $a0, 0($sp)");
		emit("move $a0, $v0");
		emit("li $v0, 1         # print_int");
		emit("syscall");
		emit("jr $31");
	}
}
