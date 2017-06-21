package fr.irit.elipse.rpm;

public class Test implements RPMListener
{
	public void rpmTouch(){}

	public void rpmMoved(double angle, double inclinaison) 
	{
		System.out.println("Reçu : "+angle+" - "+inclinaison);
	}
	
	public static void main(String[] args) 
	{
		GestionRPM rpm = new GestionRPM();
		rpm.addRPMListener(new Test());
		Thread t = new Thread(rpm);
		t.start();
	}
}
