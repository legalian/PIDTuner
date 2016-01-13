package org.usfirst.frc.team868.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team868.robot.guessmap;
import org.usfirst.frc.team868.robot.subsystems.PIDPass;

public class PIDTunerCommand extends Command {
	private PIDPass guess;
	private int[][] guessqueue;
	private int guesson;
	private guessmap guesses;
	private boolean testing;
	private boolean firstpass;
	private boolean finished;
	private double curheur;
	private double testpoint1;
	private double testpoint2;
	private double scaleP;
	private double scaleI;
	private double scaleD;
	private int jumpamount;
	private double error;
	
    public PIDTunerCommand(int p,int i, int d, double dp, double di, double dd,double sp1,double sp2,int subs) {
    	guess = PIDPass.getInstance();
    	jumpamount = 1<<subs;
    	scaleP = dp/jumpamount;
    	scaleI = di/jumpamount;
    	scaleD = dd/jumpamount;
    	guesson = 5;
    	guessqueue = new int[6][3];
    	guessqueue[6][0] = (int)(p/scaleP);
    	guessqueue[6][1] = (int)(i/scaleP);
    	guessqueue[6][2] = (int)(d/scaleP);
    	testpoint1 = sp1;
    	testpoint2 = sp2;
    	testing = false;
    	firstpass = true;
    	finished = false;
    	error = .0001;
    	requires(guess);
    }
    protected void initialize() {	
    }
    protected void execute() {
    	if (finished) {
    		return;
    	}
    	if (testing) {
    		if (firstpass) {
        		if (guess.finished(testpoint1,error)) {
        			firstpass = false;
        		}
        		curheur += guess.loopset(testpoint1);
    		} else {
        		if (guess.finished(testpoint2,error)) {
        			testing = false;
        			firstpass = true;
        			guesses.addguess(guessqueue[guesson][0],guessqueue[guesson][1],guessqueue[guesson][2],curheur);
        			curheur = 0;
        			guesson++;
        		}
        		curheur += guess.loopset(testpoint2);
    		}
    	} else {
        	if (guesson == 6) {
        		int[][] temp = new int[7][3];
        		temp = guesses.populatequeue(jumpamount);
        		guessqueue = temp;
        		guesson = temp[7][0];
        		jumpamount = temp[7][1];
        		if (temp[7][2] == 1) {
        			System.out.println("P: "+(temp[0][0]*scaleP));
        			System.out.println("I: "+(temp[0][1]*scaleI));
        			System.out.println("D: "+(temp[0][2]*scaleD));
        			finished = true;
        			return;
        		}
        	}
        	testing = true;
        	guess.trypid(guessqueue[guesson][0]*scaleP,guessqueue[guesson][1]*scaleI,guessqueue[guesson][2]*scaleD);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return finished;
        //return ;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    //-------------------------------------------------------------------------
    
}
