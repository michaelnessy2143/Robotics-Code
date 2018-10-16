package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")
@Disabled
public class BasicOpMode_Iterative extends OpMode
{
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo fLeftDrive;  //Front left wheel
    private Servo fRightDrive;  //Front right wheel
    private Servo bLeftDrive;  //Back left wheel
    private Servo bRightDrive;  //Back right wheel

    //doubles for the speed of each motor (to change the multiple of the speed in the hardcode, it is in the moveServos method)
    private double flds;  //Front left wheel
    private double frds;  //Front right wheel
    private double blds;  //Back left wheel
    private double brds;  //Back right wheel


     //Code to run ONCE when the driver hits INIT

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        //Basically means that we have to name them the same on the phone as they are named here
        fLeftDrive  = hardwareMap.get(Servo.class, "fLeftDrive");
        fRightDrive = hardwareMap.get(Servo.class, "fRightDrive");
        bLeftDrive = hardwareMap.get(Servo.class, "bLeftDrive");
        bRightDrive = hardwareMap.get(Servo.class, "bRightDrive");

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
    //this is the code that we used during TeleOP to drive around
    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_y;
        double turn  =  gamepad1.right_stick_x;

        if(drive <=-0.2 || drive >=0.2) { //left stick must be at 1/5 tilt or more in order to change the drive speed
            flds = drive;
            frds = drive;
            blds = drive;
            brds = drive;
        }
        if(turn <=-0.2) { //left stick must be at 1/5 tilt or more in order to change the turn
            flds+=1;
        } else if(turn >=0.2) {
            frds+=1;
        }

        //Strafe left and right code below
        if(gamepad1.dpad_left) { //input for strafe left
            flds = -1;
            frds = -1;
            blds = 1;
            brds = 1;


        } else if(gamepad1.dpad_right) { //input for strafe right
            flds = 1;
            frds = 1;
            blds = -1;
            brds = -1;
        }


        moveServos();

        //set speed to 0 after motion in order to avoid any problems with continuous motion
        flds = 0;
        frds = 0;
        blds = 0;
        brds = 0;

        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    //This is the code that runs after the STOP button is hit(aka it turns off the robot )
    @Override
    public void stop() {
        fLeftDrive.setPosition(0);
        fRightDrive.setPosition(0);
        bLeftDrive.setPosition(0);
        bRightDrive.setPosition(0);
    }

    public void moveServos() {
        //this may need tweaking depending on what numerical position correlated to 180 degrees
        //this applies if 1 = 180 and 2 = 360

        if(fLeftDrive.getPosition()+(flds/180) >= 2) //check to see if the new position would be over 360 degrees
            fLeftDrive.setPosition(fLeftDrive.getPosition()+(flds/180)%2); //if so, then set it to its corresponding position below 2 (for example, 2.1 =
        else
            fLeftDrive.setPosition(fLeftDrive.getPosition()+(flds/180)); //if not, then just increase its position

        if(fRightDrive.getPosition()+(frds/180) >= 2)
            fRightDrive.setPosition(fRightDrive.getPosition()+(frds/180)%2);
        else
            fRightDrive.setPosition(fRightDrive.getPosition()+(frds/180));

        if(bLeftDrive.getPosition()+(blds/180) >= 2)
            bLeftDrive.setPosition(bLeftDrive.getPosition()+(blds/180)%2);
        else
            bLeftDrive.setPosition(bLeftDrive.getPosition()+(blds/180));

        if(bRightDrive.getPosition()+(brds/180) >= 2)
            bRightDrive.setPosition(bRightDrive.getPosition()+(brds/180)%2);
        else
            bRightDrive.setPosition(bRightDrive.getPosition()+(brds/180));
    }

}
