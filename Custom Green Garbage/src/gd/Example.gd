extends Spatial

var mph = 0


# Called when the node enters the scene tree for the first time.
func _ready():
	_start()
	
func _start():
	var newDialog = Dialogic.start('Welcome')
	newDialog.pause_mode = PAUSE_MODE_PROCESS
	add_child(newDialog)
	newDialog.connect("timeline_end",self,"end_dialog")
	get_tree().paused = true
 
	
func end_dialog(data):
	get_tree().paused = false
	
func _process(_delta):
	var speed = $truck/truck.get_speed_kph()
	$Speed2.text = ("%0.1d" % speed) + " km/h"
