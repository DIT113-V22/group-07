extends CanvasLayer

func _process(delta):
	$MarginContainer/Needle.rotation_degrees = (Globals.mph - 95) + Globals.mph / 1.5

