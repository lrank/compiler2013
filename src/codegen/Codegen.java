package codegen;

import java.util.ArrayList;
import java.util.List;

import translate.*;

public class Codegen {

	private List<Tcode> list = null;
	public List<String> str = null; 
	
	public MipsAssemblyWriter write = new MipsAssemblyWriter();
	
	public Codegen() {
	}
	
	public void gen(Translate tran) {
		str = tran.str;
		write.emitPrologue(str, tran.offset);
		list = tran.list;
		
		for (Tcode c : list) {
			if (c instanceof Label)
				write.emit((Label) c);
			if (c instanceof Gotocode)
				write.jump(((Gotocode) c).l);
//			if (c instanceof Ifcode)
	//			write.(((Ifcode) c).tostring());
			if (c instanceof IICode)
				write.IICode((IICode) c);
			if (c instanceof InCode)
				write.InCode((InCode) c);
			if (c instanceof NotIfcode)
				write.jz(((NotIfcode) c));
			if (c instanceof Callcode)
				write.Callcode(((Callcode) c));
			if (c instanceof Funstart)
				write.Funstart((Funstart) c);
			if (c instanceof TReturn)
				write.TReturn((TReturn) c);
			if (c instanceof PARAcode)
				write.PARAcode((PARAcode) c);
//			if (c instanceof Define)
	//			write.Define((Define) c);
		}
		
		write.emitEpilogue();
	}
	
	public String tostring() {
		return write.toString();
	}
	
}
