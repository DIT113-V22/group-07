extends "res://TruckScript/garbageTruck-.gd"


# Declare member variables here. Examples:
# var a = 2
# var b = "text"

func get_input():
	var turn = Input.get_action_strength("steer_left")
	turn -= Input.get_action_strength("steer_right")
	steer_angle = turn * deg2rad(steering_limit)
	$tmpParent/garbageTruck/VehicleWheelFR/wheel_frontRight.rotation.y = steer_angle*2
	$tmpParent/garbageTruck/VehicleWheelFL/wheel_frontLeft.rotation.y = steer_angle*2
	acceleration = Vector3.ZERO
	if Input.is_action_pressed("accelerate"):
		acceleration = -transform.basis.z * engine_power
	if Input.is_action_pressed("reverse"):
		acceleration = -transform.basis.z * braking


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
#func _process(delta):
#	pass
