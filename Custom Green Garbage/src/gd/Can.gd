extends "res://src/objects/npc.gd"


signal TrashCollected



func _on_Can_body_entered(body):
	emit_signal("TrashCollected")
	queue_free()
