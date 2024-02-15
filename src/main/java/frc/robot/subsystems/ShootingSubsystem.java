// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/** Subsystem for the motors that shoot the ball */
public class ShootingSubsystem extends SubsystemBase {

    //Motor controllers
    private CANSparkMax intakeMotor;
    private CANSparkMax topLeftShootMotor;
    private CANSparkMax topRightShootMotor;

    // Initialize the motors
    public ShootingSubsystem() {
        this.intakeMotor = intakeMotor;
        this.topLeftShootMotor = topLeftShootMotor;
        this.topRightShootMotor = topRightShootMotor;

        topRightShootMotor.setInverted(true);
    }

    /**
     * Shoots the ball fast to make it into the top hub
     * @param input Controller input
     */
    public void fastShoot(double input)
    {
        topLeftShootMotor.setVoltage(-input * Constants.Kinematics.fastShootVoltage);
        topRightShootMotor.setVoltage(-input * Constants.Kinematics.fastShootVoltage);
    }

    /**
     * Shoots the ball slowly to make it into the low hub
     * @param input Controller input
     */
    public void slowShoot(double input)
    {
        topLeftShootMotor.setVoltage(-input * Constants.Kinematics.slowShootVoltage);
        topRightShootMotor.setVoltage(-input * Constants.Kinematics.slowShootVoltage);
    }

    /**
     * Shoots the ball slowly to make it into the low hub
     * @param input Controller input
     */
    public void reverseShoot(double input)
    {
        topLeftShootMotor.setVoltage(input * Constants.Kinematics.reverseShootVoltage);
        topRightShootMotor.setVoltage(input * Constants.Kinematics.reverseShootVoltage);
    }

    /**
     * Activates the intake system
     * @param input Controller input
     */
    public void spinIntake(double input)
    {
        intakeMotor.setVoltage(input * Constants.Kinematics.intakeVoltage); 
    }

    public void stopShootingMotors()
    {
        topLeftShootMotor.setVoltage(0);
        topRightShootMotor.setVoltage(0);
    }

    public void stopAllMotors()
    {
        topLeftShootMotor.setVoltage(0);
        topRightShootMotor.setVoltage(0);
        intakeMotor.setVoltage(0);
    }
}
// import java.util.Map;

// import static frc.robot.Constants.Shooter.*;
// import static com.revrobotics.CANSparkMaxLowLevel.MotorType.*;
// import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
// import static edu.wpi.first.wpilibj.PneumaticsModuleType.*;
// import static edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets.*;

// import com.revrobotics.CANSparkMax; 

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import edu.wpi.first.networktables.GenericEntry;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
// import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

// public class ShootingSubsystem extends SubsystemBase {
//   private final CANSparkMax right_shooter;          // right_shooter shooter motor
//   private final CANSparkMax left_shooter;           // left_shooter shooter motor
//   private final DoubleSolenoid shooterPCM;  // Solenoid for the shooter trigger
//   private GenericEntry shooterSpeed;   // NetworkTables controller for motor speed
//   private boolean motorsOn = false;         // Current state of the motors
  
//   /** Creates a new Shooter. */
//   public  ShootingSubsystem() {
//     right_shooter = new CANSparkMax(RIGHT_SHOOTER_ID, kBrushless);
//     left_shooter = new CANSparkMax(LEFT_SHOOTER_ID, kBrushless);
//     shooterPCM = new DoubleSolenoid(CTREPCM, TRIGGER_FORWARD_ID, TRIGGER_REVERSE_ID);
//     //has to be set before toggled
//     toggleTrigger(false);

//     right_shooter.restoreFactoryDefaults();
//     left_shooter.restoreFactoryDefaults();

//     right_shooter.setInverted(RIGHT_SHOOTER_INVERTED);
//     left_shooter.setInverted(LEFT_SHOOTER_INVERTED);

//     // Configure the shuffleboard so we can easily test the Shooter
//     configureShuffleboard();
//   }

//   /** Configures the Shuffleboard dashboard "Shooter" tab */
//   private void configureShuffleboard() {
//     // Create references to Shooter tab and its various layouts
//     ShuffleboardTab shuffleShooterTab = Shuffleboard.getTab("Shooter");

//     // Add piston object to tab
//     shuffleShooterTab
//         .add("Trigger Piston", shooterPCM)
//         .withPosition(0, 0)
//         .withSize(2, 1);

//     // Configure speed slider
//     shooterSpeed = shuffleShooterTab
//         .add("Shooter Speed", SHOOTER_SPEED)
//         .withWidget(kNumberSlider)
//         .withProperties(
//             Map.of(
//                 "Min", 0,
//                 "Max", 1))
//         .withSize(2, 1)
//         .withPosition(0, 1)
//         .getEntry();
//   }

//   /* Toggle the trigger on/off */
//   public void toggleTrigger(boolean offOn){
//     if (offOn == true){
//       shooterPCM.set(kForward);
//     }
//     else{
//       shooterPCM.set(kReverse);
//     }
//   }

//   /* Toggle the spin-up motors on/off */
//   public void toggleMotors(){
//     motorsOn = !motorsOn;
//   }

//   /* Toggle the trigger on/off */
//   public void toggleTrigger(){
//     shooterPCM.toggle();
//   }

//   /** Set the motor speed to the default in Constants */
//   public void setMotorSpeed() {
//     setMotorSpeed(SHOOTER_SPEED);
//   }

//   /** Set the motor speed between [0.0, 1.0] */
//   public void setMotorSpeed(double speed) {
//     shooterSpeed.setDouble(speed);
//   }

//   @Override
//   public void periodic() {
//     double output = (motorsOn) ? shooterSpeed.getDouble(SHOOTER_SPEED) : 0;
//     right_shooter.set(output);
//     left_shooter.set(output);
//   }
// }
