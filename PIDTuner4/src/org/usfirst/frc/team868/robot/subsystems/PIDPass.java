package org.usfirst.frc.team868.robot.subsystems;
import org.usfirst.frc.team868.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;
public class PIDPass extends Subsystem {    
	CANTalon talon;
	private static PIDPass instance;
	
	public PIDPass(){
		talon = new CANTalon(RobotMap.testMotor);
		talon.changeControlMode(ControlMode.Position);
	}
    public void initDefaultCommand() {
    }
    public void trypid(double p, double i, double d) {
    	talon.setPID(p, i, d);
    }
    public double loopset(double target) {
    	talon.set(target);
    	return Math.abs(talon.getEncPosition()-target);
    }
    public boolean finished(double target,double error) {
    	return Math.abs(talon.getEncPosition()-target)<error;
    }
    public static PIDPass getInstance() {
    	if (instance == null) {
    		instance = new PIDPass();
    	}
    	return instance;
    }
}

