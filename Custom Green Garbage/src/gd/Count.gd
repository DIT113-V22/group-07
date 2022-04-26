extends Label

var trash = 0

func _ready():
	text = String(trash)


func _on_barrel_TrashCollected():
	trash = trash + 1
	_ready()


func _on_glass_TrashCollected():
	trash = trash + 1
	_ready()


func _on_bottle_TrashCollected():
	trash = trash + 1
	_ready()


func _on_Can_TrashCollected():
	trash = trash + 1
	_ready()


func _on_Wine_TrashCollected():
	trash = trash + 1
	_ready()


func _on_cup_TrashCollected():
	trash = trash + 1
	_ready()
