extends Spatial

var mph = 0

func _process(delta):
	var speed = $gb/gb.get_speed_kph()
	$Speed2.text = ("%0.1d" % speed) + " km/h"
		
		
		
		




