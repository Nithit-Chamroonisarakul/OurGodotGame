extends CharacterBody2D

@export var speed: float = 100.0 
@export var stop_distance: float = 20.0  

@export var healthMax: float = 100
@export var knockback_strength: float = 600.0  #boucne
@onready var currentHealth: float = healthMax

@onready var sprite = $AnimatedSprite2D
@onready var player = get_tree().get_first_node_in_group("player")
@onready var hitbox = $Hitbox if has_node("Hitbox") else null  # หา Hitbox

func _ready():
	if player == null:
		print("ERROR: Player not found in group 'player'!")
	
	if hitbox:
		hitbox.body_entered.connect(_on_Hitbox_body_entered)
	else:
		print("ERROR: Hitbox not found in Enemy.tscn!")
		
func _physics_process(delta):
	if player:
		var direction = (player.global_position - global_position)
		var distance = direction.length()
		
		if distance > stop_distance:
			direction = direction.normalized()
			velocity = direction * speed
			sprite.play("default")
		else:
			velocity = Vector2.ZERO 
		
		move_and_slide()

func _on_Hitbox_body_entered(body):
	if body.is_in_group("player"):
		body.take_damage(10)
		#queue_free() # ถ้าชน Enermy จะตายทันที
func take_damage(damage):
	currentHealth -= damage
	var knockback_direction = (global_position - player.global_position).normalized()#boucne
	velocity = knockback_direction * knockback_strength#boucne
	move_and_slide()#boucne
	if currentHealth <= 0:
		queue_free()  # ลบ Enemy
		get_parent().on_enemy_died()
