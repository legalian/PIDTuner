package org.usfirst.frc.team868.robot;

public class guessmap {
	public guessmapunit next;
	public void addguess(int p, int i, int d,double h) {
		if (next == null) {
			next = new guessmapunit(p,i,d,h);
		} else {
			next = next.addguess(p,i,d,h);
		}
	}
	public double getguess(int p,int i, int d) {
		if (next == null) {
			return -9001;
		} else {
			return next.getguess(p, i, d);
		}
	}
	public boolean existsat(int p,int i, int d) {
		if (next == null) {
			return false;
		} else {
			return next.existsat(p, i, d);
		}
	}
	public int[][] populatequeue(int scale) {
		guessmapunit expander = next.getleastheuristic();
		int s1 = scale;
		int[][] adding = new int[][] {
				{0,0,1},
				{0,1,0},
				{1,0,0},
				{0,0,-1},
				{0,-1,0},
				{-1,0,0}
		};
		int[][] primqueue = new int[7][3];
		primqueue[7][1] = s1;
		primqueue[7][2] = 0;
		int k = 6;
		while (k == 6) {
			for (int i=0;i<6;i++) {
				if (!existsat(expander.p+adding[i][0]*s1,expander.i+adding[i][1]*s1,expander.d+adding[i][2]*s1)) {
					k--;
					primqueue[k][0] = expander.p+adding[i][0]*s1;
					primqueue[k][1] = expander.i+adding[i][1]*s1;
					primqueue[k][2] = expander.d+adding[i][2]*s1;
				}
			}
			if (k == 6) {
				if (s1 == 1) {
					primqueue[7][2] = 1;
					primqueue[0][0] = expander.p;
					primqueue[0][1] = expander.i;
					primqueue[0][2] = expander.d;
					break;
				} else {
					s1 /= 2;
					primqueue[7][1] = s1;
				}
			}
		}
		primqueue[7][0] = k;
		return primqueue;
	}
}
