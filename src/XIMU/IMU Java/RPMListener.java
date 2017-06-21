package fr.irit.elipse.rpm;

public interface RPMListener {
	public void rpmTouch();
	public void rpmMoved(double angle, double inclinaison);
}
