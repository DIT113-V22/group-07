extends Spatial


func _process(delta):
	if  $RayCast.is_colliding():
		queue_free()






