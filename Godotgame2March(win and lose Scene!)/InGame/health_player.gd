extends TextureProgressBar

var parent

var health: float

func _ready():
	parent = get_parent()
	health = parent.healthMax
	self.max_value = health

func _process(delta):
	if (parent.currentHealth != health) and (parent.currentHealth != 0):
		self.visible = true
		self.value = parent.currentHealth
	else:
		self.visible = false
