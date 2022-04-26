extends Area

signal TrashCollected

func _on_cup_body_entered(body):
	emit_signal("TrashCollected")
	queue_free()
