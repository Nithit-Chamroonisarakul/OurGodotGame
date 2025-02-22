extends CharacterBody2D

@export var speed: float = 100.0  
@export var stop_distance: float = 20.0  

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
		queue_free()  # ลบ Enemy
