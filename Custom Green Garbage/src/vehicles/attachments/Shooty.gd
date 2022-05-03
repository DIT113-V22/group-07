#
#  Shooty.gd
#  Copyright 2022 ItJustWorksTM
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

extends Spatial
class_name Shooty
func extern_class_name():
	return "Shooty"

var view = null setget set_view

onready var timer: SceneTreeTimer = get_tree().create_timer(0);

func set_view(_view) -> void:
	view = _view

	view.connect("invalidated", self, "set_physics_process", [true])
	view.connect("validated", self, "set_physics_process", [true])
	
	set_physics_process(view.is_valid())


func _ready() -> void:
	set_physics_process(false)


func _physics_process(_delta: float):
	if timer.get_time_left() > 0:
		return

	if view.read_digital_pin(250):
		shoot()


func name() -> String:
	return "Shooty"


func visualize() -> Control:
	var visualizer: Button = ShootyVisualizer.new()
	visualizer.theme = load("res://src/ui/themes/regular_button/regular_button.tres")
	visualizer.rect_min_size.y = 30
	visualizer.mouse_default_cursor_shape = Control.CURSOR_POINTING_HAND
	visualizer.display_shooty(self)
	return visualizer


func shoot() -> void:
	timer = get_tree().create_timer(5)
	var car = get_parent().get_parent()
	car.add_force(car.transform.basis.xform(Vector3.BACK) * 64,  global_transform.origin - car.global_transform.origin + Vector3.UP)
	var shell: RigidBody = load("res://src/objects/tank_shell/Ball.tscn").instance()
	get_parent().get_parent().get_parent().add_child(shell)
	shell.global_transform.origin = global_transform.origin
	shell.apply_central_impulse(car.transform.basis.xform(Vector3.FORWARD) * 100)
	shell.set_physics_process(true)


func cooldown() -> float:
	return clamp(timer.get_time_left(), 0, 65535)
