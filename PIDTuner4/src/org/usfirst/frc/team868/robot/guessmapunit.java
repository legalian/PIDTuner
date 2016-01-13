package org.usfirst.frc.team868.robot;

public class guessmapunit {
	public guessmapunit next;
	int p;
	int i;
	int d;
	double h;
	public guessmapunit(int pe, int ie, int de, double he) {
		p = pe;
		i = ie;
		d = de;
		h = he;
	}
	public guessmapunit(int pe, int ie, int de, double he,guessmapunit dnext) {
		p = pe;
		i = ie;
		d = de;
		h = he;
		next = dnext;
	}
	public boolean existsat(int pe,int ie, int de) {
		if (p == pe && i == ie && d == de) {
			return true;
		} else if (next == null) {
			return false;
		} else {
			return next.existsat(p, i, d);
		}
	}
	public double getguess(int pe, int ie, int de) {
		if (p == pe && i == ie && d == de) {
			return h;
		}
		if (next == null) {
			return -9001;
		} else {
			return next.getguess(pe, ie, de);
		}
	}
	public guessmapunit addguess(int pe, int ie, int de, double he) {
		if (compare(pe,ie,de,p,i,d)) {
			return new guessmapunit(pe,ie,de,he,this);
		} else {
			if (next == null) {
				next = new guessmapunit(pe,ie,de,he);
			} else {
				next = next.addguess(pe,ie,de,he);
			}
			return this;
		}
	}
	
	private boolean compare(int p1, int i1, int d1, int p2, int i2, int d2) {
		if (p1>p2) {return true;}
		else if (p1<p2) {return false;}
		if (i1>i2) {return true;}
		else if (i1<i2) {return false;}
		if (d1>d2) {return true;}
		else {return false;} //this will never be the case: p1==p2&&i1==i2&&d1==d2
	}
	public guessmapunit getleastheuristic() {
		if (next == null) {
			return this;
		}
		else if (h<next.h) {
			return this;
		}
		else {
			return next.getleastheuristic();
		}
	}
}
