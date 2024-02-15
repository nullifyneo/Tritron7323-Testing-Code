package frc.robot.subsystems;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.RobotContainer;
import com.revrobotics.CANSparkMax;
import frc.robot.Constants;
// import com.ctre.phoenix.sensors.CANCoder;



public class IntakeRamp {
    //Initialize SparkMax
    private CANSparkMax rampMotor;

    // Initialize the motors
    public IntakeRamp() {
        this.rampMotor = rampMotor;
    }
    //rampConstants
    public static final class Kinematics{
        public static final int IntakeRampMotor = 9;
    }

    public void spinRamp(double input)
    {
        rampMotor.setVoltage(input * Constants.Kinematics.intakeVoltage); 
    }

    public void stopRampMotors()
    {
        rampMotor.setVoltage(0);
    }

}
