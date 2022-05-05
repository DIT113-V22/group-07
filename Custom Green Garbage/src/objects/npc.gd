extends Area

export var npc = "npc name"
export var dialog_index = 0

func _ready():
	add_to_group("NPC")
	
func start_dialog():
	var dialog = Dialogic.start(npc + str(dialog_index))
	dialog.pause_mode = PAUSE_MODE_PROCESS
	Input.set_mouse_mode(Input.MOUSE_MODE_VISIBLE)
	get_parent().add_child(dialog)
	dialog.connect("timeline_end",self,"end_dialog")
	get_tree().paused = true
	
func end_dialog(data):
	get_tree().paused = false
	

