extends ProgressBar

var parent 
var max_val_amount: int
var min_val_amount: int

func _ready():
	parent = get_parent()
	max_val_amount = parent.healthMax
	min_val_amount = 0
	self.max_value = max_val_amount

func _process(delta):
	if (parent.health != max_val_amount) and (parent.health != 0):
		self.visible = true
		self.value = parent.health
	else:
		self.visible = false
