package org.firstinspires.ftc.teamcode;

//this code will be the code for the teleop part of the competition
//This code consists of the code to run the drive train and the code to move the scissor lifts up and down
//As well as being able to control the servos
//YEET - Nez

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
//@Disabled //This needs to be commented out other-wise the code wont run
public class BasicOpMode_Iterative extends OpMode
{
    // Declare OpMode members.game
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor fLeftDrive;  //Front left wheel
    private DcMotor fRightDrive;  //Front right wheel
    private DcMotor bLeftDrive;  //Back left wheel
    private DcMotor bRightDrive;  //Back right wheel
    //////////////////////////////////////////////////
    private DcMotor lScissorLift;
    private DcMotor rScissorLift;

    double shimmycount;

     //Code to run ONCE when the driver hits INIT

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        //Basically means that we have to name them the exact same on the phone as they are named here
        fLeftDrive  = hardwareMap.get(DcMotor.class, "fLeftDrive");
        fRightDrive = hardwareMap.get(DcMotor.class, "fRightDrive");
        bLeftDrive = hardwareMap.get(DcMotor.class, "bLeftDrive");
        bRightDrive = hardwareMap.get(DcMotor.class, "bRightDrive");
        //Scissor lifts
        lScissorLift = hardwareMap.get(DcMotor.class, "lScissorLift");
        rScissorLift = hardwareMap.get(DcMotor.class, "rScissorLift");

        shimmycount = 0;

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    //Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY

    @Override
    public void init_loop() {
    }

    //Code to run ONCE when the driver hits PLAY

    @Override
    public void start() {
        runtime.reset();
    }

    //Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    //this is the code that we use during TeleOP to drive around
    @Override
    public void loop() {
        double drive = gamepad1.left_stick_y;
        double turn  =  0; //turn is 0 unless...
        lScissorLift.setPower(0);
        rScissorLift.setPower(0);

        if(gamepad1.left_stick_x >= 0.1 || gamepad1.left_stick_x <= -0.1) { //... the stick is turned more than 1/10 in either direction
            turn = -gamepad1.left_stick_x;
        }

        if(gamepad1.left_bumper) { //input for strafe left
            fLeftDrive.setPower(1);
            fRightDrive.setPower(-1);
            bLeftDrive.setPower(-1);
            bRightDrive.setPower(1);
        } else if(gamepad1.right_bumper) { //input for strafe right
            fLeftDrive.setPower(-1);
            fRightDrive.setPower(1);
            bLeftDrive.setPower(1);
            bRightDrive.setPower(-1);
        } else if(drive <=-0.1 || drive >=0.1) { //if not strafing, move normally. left stick must be at 1/10 tilt or more in order to drive
            fLeftDrive.setPower(Range.clip(drive+turn, -1, 1));
            fRightDrive.setPower(Range.clip(-drive+turn, -1, 1));
            bLeftDrive.setPower(Range.clip(drive, -1, 1));
            bRightDrive.setPower(Range.clip(-drive, -1, 1));
        } else {
            fLeftDrive.setPower(turn);
            fRightDrive.setPower(-turn);
            bLeftDrive.setPower(0);
            bRightDrive.setPower(0);
        }

        if(gamepad1.a) {
            lScissorLift.setPower(1);
            rScissorLift.setPower(-1);
        } else if(gamepad1.b) {
            lScissorLift.setPower(-1);
            rScissorLift.setPower(1);
        } else if(gamepad1.y) {
            shimmy(shimmycount);
        }

        if(gamepad1.right_stick_x >= 0.3) {
            fLeftDrive.setPower(-1);
            bLeftDrive.setPower(-1);
            fRightDrive.setPower(-1);
            bRightDrive.setPower(-1);
        } else if(gamepad1.right_stick_x <= -0.3) {
            fLeftDrive.setPower(1);
            bLeftDrive.setPower(1);
            fRightDrive.setPower(1);
            bRightDrive.setPower(1);
        }
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "front left (%.2f), front right (%.2f), back left (%.2f), back right(%.2f)", fLeftDrive.getPower(), fRightDrive.getPower(), bLeftDrive.getPower(), bRightDrive.getPower());
    }

    //This is the code that runs after the STOP button is hit(aka it turns off the robot )
    @Override
    public void stop() {
    }
    public void shimmy(double count) {
        if(count%50<25) {
            lScissorLift.setPower(1);
        } else {
            rScissorLift.setPower(1);
        }
        shimmycount++;
    }
}
