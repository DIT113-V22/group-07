extends Label

var trash = 0

func _ready():
	text = String(trash)


func _on_Bottle_TrashCollected():
	trash = trash + 1
	_ready()


func _on_barrel_TrashCollected():
	trash = trash + 1
	_ready()


func _on_Wine_TrashCollected():
	trash = trash + 1
	_ready()


func _on_glassbroken_TrashCollected():
	trash = trash + 1
	_ready()
