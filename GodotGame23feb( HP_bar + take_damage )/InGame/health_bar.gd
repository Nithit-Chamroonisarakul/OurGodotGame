extends TextureProgressBar

@onready var timer = $Timer
@onready var damageBar = $damageBar
@onready var healthBar = $healthBar

var parent 
var health: float

func _ready():
	parent = get_parent()
	health = parent.healthMax
	self.max_value = health
	damageBar.max_value = health
	timer.start()
	
func _process(delta):
	if parent is Player:
		self.visible = true
		damageBar.visible = true
		damageBar.value = parent.currentHealth
	elif (parent.currentHealth != health) and (parent.currentHealth > 0):
		self.visible = true
		damageBar.visible = true
		damageBar.value = parent.currentHealth  # Instantly update damageBar		
	else:
		self.visible = false

func _on_timer_timeout() -> void:
	self.value = parent.currentHealth  # After delay, update HP bar
