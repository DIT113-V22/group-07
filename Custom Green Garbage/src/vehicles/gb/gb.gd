extends VehicleBody


#vehicle force 
export var max_engine = 60.0
export var max_brake = 5.0
export var max_steer = 0.1
var current_speed = 0.0
export (float) var final_drive_ratio = 3.88
export (float) var max_engine_rpm = 5000.0

#Vehicle steering
export var steer_speed = 5.0
var steer_target = 0
var steer_angle = 1

#control values
onready var last_pos = translation
export var joy_steering = JOY_ANALOG_LX
export var joy_throttle = JOY_ANALOG_R2
export var joy_brake = JOY_ANALOG_L2
export var steering_mult = -1.0
export var throttle_mult = 1.0
export var brake_mult = 1.0

func get_speed_kph():
	return current_speed * 3600.0 / 1000.0

func _ready():
	pass

	
func calculate_rpm() -> float:


	if current_speed == 0:
		return 0.0
	
	var wheel_circumference : float = 2.0 * PI * $frontr.wheel_radius
	var wheel_rotation_speed : float = 60.0 * current_speed / wheel_circumference
	var drive_shaft_rotation_speed : float = wheel_rotation_speed * final_drive_ratio

	return 0.0

func _process(delta : float):
	
	var speed = get_speed_kph()
	var rpm = calculate_rpm()
	
	#Globals.mph = speed / 1.609

	var info = 'Speed: %.0f, RPM: %.0f ' % [ speed, rpm ]
	
	



func _physics_process(delta):
	
	current_speed = (translation - last_pos).length() / delta

	var steer_val = steering_mult * Input.get_joy_axis(0, joy_steering)
	var throttle_val = throttle_mult * Input.get_joy_axis(0, joy_throttle)
	var brake_val = brake_mult * Input.get_joy_axis(0, joy_brake)
	
	#keyboard input for control
	if Input.is_action_pressed("ui_up"):
		throttle_val = 1.0
	if Input.is_action_pressed("ui_down"):
		throttle_val = -1.0
	if Input.is_action_pressed("ui_accept"):
		brake_val = 1.0
	if Input.is_action_pressed("ui_left"):
		steer_val = 1.0
	if Input.is_action_pressed("ui_right"):
		steer_val = -1.0
	
	engine_force = throttle_val * max_engine
	brake = brake_val * max_brake
	
	steer_target = steer_val * max_steer
	if (steer_target < steer_angle):
		steer_angle -= steer_speed * delta
		if (steer_target > steer_angle):
			steer_angle = steer_target
	elif (steer_target > steer_angle):
		steer_angle += steer_speed * delta
		if (steer_target < steer_angle):
			steer_angle = steer_target
	steering = steer_angle

	last_pos = translation
