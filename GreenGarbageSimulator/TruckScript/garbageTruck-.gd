extends VehicleBody

# Car behavior parameters, adjust as needed
export var gravity = -20.0
export var wheel_base = 0.6  # distance between front/rear axles
export var steering_limit = 10.0  # front wheel max turning angle (deg)
export var engine_power = 6.0
export var braking = -9.0
export var drag = -2.0
export var max_speed_reverse = 6.0

# Car state properties
var acceleration = Vector3.ZERO  # current acceleration
var velocity = Vector3.ZERO  # current velocity
var steer_angle = 0.0  # current wheel angle


func apply_friction(delta):
	if velocity.length() < 0.2 and acceleration.length() == 0:
		velocity.x = 0
		velocity.z = 0
	var friction_force = velocity * friction * delta
	var drag_force = velocity * velocity.length() * drag * delta
	acceleration += drag_force + friction_force

func calculate_steering(delta):
	var rear_wheel = transform.origin + transform.basis.z * wheel_base / 2.0
	var front_wheel = transform.origin - transform.basis.z * wheel_base / 2.0
	rear_wheel += velocity * delta
	front_wheel += velocity.rotated(transform.basis.y, steer_angle) * delta
	var new_heading = rear_wheel.direction_to(front_wheel)

	var d = new_heading.dot(velocity.normalized())
	if d > 0:
		velocity = new_heading * velocity.length()
	if d < 0:
		velocity = -new_heading * min(velocity.length(), max_speed_reverse)
	look_at(transform.origin + new_heading, transform.basis.y)

func get_input():
	# Override this in inherited scripts for controls

	var turn = Input.get_action_strength("steer_left")
	turn -= Input.get_action_strength("steer_right")
	steer_angle = turn * deg2rad(steering_limit)
	$tmpParent/garbageTruck/wheel_frontRight.rotation.y = steer_angle*2
	$tmpParent/garbageTruck/wheel_frontLeft.rotation.y = steer_angle*2
	acceleration = Vector3.ZERO
	if Input.is_action_pressed("accelerate"):
		acceleration = -transform.basis.z * engine_power
	if Input.is_action_pressed("back"):
		acceleration = -transform.basis.z * braking

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
#func _process(delta):
#	pass
