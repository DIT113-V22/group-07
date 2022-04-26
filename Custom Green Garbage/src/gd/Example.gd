extends Spatial

var mph = 0


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.

func _process(delta):
	var speed = $truck/truck.get_speed_kph()
	$Speed2.text = ("%0.1d" % speed) + " km/h"
