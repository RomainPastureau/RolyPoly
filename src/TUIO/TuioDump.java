package TUIO;

public class TuioDump implements TuioListener {
	
	public void addTuioObject(TuioObject tobj) {
		System.out.println("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());	
	}

	public void updateTuioObject(TuioObject tobj) {
		System.out.println("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()+" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
	}
	
	public void removeTuioObject(TuioObject tobj) {
		System.out.println("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");	
	}

	public void addTuioCursor(TuioCursor tcur) {
		System.out.println("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY());	
	}

	public void updateTuioCursor(TuioCursor tcur) {
		System.out.println("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
	}
	
	public void removeTuioCursor(TuioCursor tcur) {
		System.out.println("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");	
	}

	public void addTuioBlob(TuioBlob tblb) {
		System.out.println("add blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getWidth()+" "+tblb.getHeight()+" "+tblb.getArea());	
	}
	
	public void updateTuioBlob(TuioBlob tblb) {
		System.out.println("set blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+") "+tblb.getX()+" "+tblb.getY()+" "+tblb.getAngle()+" "+tblb.getWidth()+" "+tblb.getHeight()+" "+tblb.getArea()+" "+tblb.getMotionSpeed()+" "+tblb.getRotationSpeed()+" "+tblb.getMotionAccel()+" "+tblb.getRotationAccel());
	}
	
	public void removeTuioBlob(TuioBlob tblb) {
		System.out.println("del blb "+tblb.getBlobID()+" ("+tblb.getSessionID()+")");	
	}
	
	public void refresh(TuioTime frameTime) {
		System.out.println("frame "+frameTime.getFrameID()+" "+frameTime.getTotalMilliseconds());
	}

	public static void main(String argv[]) {
	
		int port = 3333;

		if (argv.length==1) {
			try { port = Integer.parseInt(argv[0]); }
			catch (Exception e) { System.out.println("usage: java TuioDump [port]"); }
		} else if (argv.length>1) System.out.println("usage: java TuioDump [port]");

		TuioDump dump = new TuioDump();
		TuioClient client = new TuioClient(port);

		System.out.println("listening to TUIO messages at port "+port);
		client.addTuioListener(dump);
		client.connect();
	}
}
