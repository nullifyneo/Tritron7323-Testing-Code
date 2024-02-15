// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.Shooter;
import frc.robot.Constants.USB;
import frc.robot.commands.SwerveJoystickCmd;
import frc.robot.subsystems.ShootingSubsystem;
import frc.robot.subsystems.SwerveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final static SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  public final static ShootingSubsystem shootingSubsystem = new ShootingSubsystem();
  //public final static ShootingSubsystem shootingSubsystem = new ShootingSubsystem();
  //private final static Shooter m_shooter = new Shooter();

  public final static XboxController driverController = new XboxController(OIConstants.kDriverControllerPort);
  public final static XboxController operatorController = new XboxController(USB.OPERATOR_CONTROLLER);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(swerveSubsystem,
     () -> -driverController.getRawAxis(OIConstants.kDriverYAxis), 
     () -> driverController.getRawAxis(OIConstants.kDriverXAxis), 
     () -> driverController.getRawAxis(OIConstants.kDriverRotAxis), 
     () -> !driverController.getLeftBumper()
     ));

     // Unsure what the parameters should 
     
//      Command shootCommand = new ShootCommand(
//         shootingSubsystem,
//         () -> driverController.getRawAxis(OIConstants.left_trigger),
//         () -> driverController.getRawAxis(OIConstants.right_trigger),
//         () -> driverController.getRawAxis(3),

//         () -> driverController.getRawAxis(OIConstants.kDriverFieldOrientedButtonIdx));
//      shooterSubsystem.setDefaultCommand(new ShootingSubsystem(shootingSubsystem,
//      () -> operatorController.getRightTriggerAxis(Constants.Controller.right_trigger),
//      () -> operatorController.getRawAxis(3),
//      () -> operatorController.getRawAxis(Constants.Controller.)
//      ));

    // dont know what this does but hopefully it fixes something
//     shooterSubsystem.setDefaultCommand(new SwerveJoystickCmd(shooterSubsystem,
//     () -> driverController.getRawAxis(OIConstants.right_trigger)
//     ));
    // Configure the button bindings
    configureBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureBindings() {
//          // Configure the button for turning on FOD
//     SmartDashboard.putData("Drive with FOD", swerveSubsystem);
//     /**SmartDashboard.putData(
//         "Initialize Intake",
//         new InstantCommand(m_intake::toggleDrop, m_intake));
//     */
//     new JoystickButton(swerveSubsystem, driverController.getRightTriggerAxis())
    
//         .whenPressed(new InstantCommand(m_shooter::toggleMotors, m_shooter));
        
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
        // 1. Create trajectory settings
        TrajectoryConfig trajectoryConfig = new TrajectoryConfig(
                AutoConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                        .setKinematics(DriveConstants.kDriveKinematics);

        // 2. Generate trajectory
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                new Pose2d(0, 0, new Rotation2d(0)),
                List.of(
                        new Translation2d(0.075, 0),
                        new Translation2d(0.-.075, 0)),
                new Pose2d(-0.25 , 0.01, Rotation2d.fromDegrees(0)),
                trajectoryConfig);

        // 3. Define PID controllers for tracking trajectory
        PIDController xController = new PIDController(AutoConstants.kPXController, 0, 0);
        PIDController yController = new PIDController(AutoConstants.kPYController, 0, 0);
        ProfiledPIDController thetaController = new ProfiledPIDController(
                AutoConstants.kPThetaController, 0, 0, AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        // 4. Construct command to follow trajectory
        SwerveControllerCommand swerveControllerCommand = new SwerveControllerCommand(
                trajectory,
                swerveSubsystem::getPose,
                DriveConstants.kDriveKinematics,
                xController,
                yController,
                thetaController,
                swerveSubsystem::setModuleStates,
                swerveSubsystem);

        // 5. Add some init and wrap-up, and return everything
        return new SequentialCommandGroup(
          new InstantCommand(() -> swerveSubsystem.resetOdometry(trajectory.getInitialPose())),
          swerveControllerCommand,
          new InstantCommand(() -> swerveSubsystem.stopModules()));
    }
}