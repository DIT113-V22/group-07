extends Label


func toggle_visibility() -> void:
	visible = false


func _on_Timer_timeout():
	toggle_visibility()
