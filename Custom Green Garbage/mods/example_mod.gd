extends Reference

var mod_name: String = "example"

func init(global) -> void:
	global.register_environment("example/Example", preload("res://src/environments/example/Example.tscn"))

	global.register_vehicle("TemplateCar", preload("res://src/vehicles/SketchCar/TemplateCar.tscn"))

	print("Hello World!")
