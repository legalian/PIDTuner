
package org.usfirst.frc.team868.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team868.robot.commands.PIDTunerCommand;

public class Robot extends IterativeRobot {
	public static OI oi;
    Command autonomousCommand;
    public void robotInit() {
		oi = new OI();
        autonomousCommand = new PIDTunerCommand(0, 0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0);
    }
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
    public void autonomousInit() {
        if (autonomousCommand != null) autonomousCommand.start();
    }
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
    }
    public void disabledInit(){
    }
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    public void testPeriodic() {
        LiveWindow.run();
    }
}
