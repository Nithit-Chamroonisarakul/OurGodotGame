class_name Player extends CharacterBody2D
@onready var shoot_sound = $ShootSound  

@export var speed: float = 300.0

@export var healthMax: float = 20

@onready var currentHealth: float = healthMax

@onready var player = $AnimatedSprite2D  # แก้ให้ตรงกับชื่อจริง

@onready var bullet_scene = preload("res://InGame/Bullet.tscn")

var last_direction = Vector2.DOWN
var is_alive:bool = true#new
var can_shoot: bool = true

func _physics_process(delta):
	var direction = Vector2.ZERO

	direction.x = Input.get_action_strength("move_right") - Input.get_action_strength("move_left")
	direction.y = Input.get_action_strength("move_down") - Input.get_action_strength("move_up")
	if is_alive:#new
		if direction.length() > 0:
			last_direction = direction.normalized()

		if direction.length() > 1:
			direction = direction.normalized()

		velocity = direction * speed
		move_and_slide()

		# Animation ตามการเดิน
		if direction.length() > 0:
			if abs(direction.x) > abs(direction.y):
				if direction.x > 0:
					player.play("walk_right")
				else:
					player.play("walk_left")
			else:
				if direction.y > 0:
					player.play("walk_down")
				else:
					player.play("walk_up")
		else:
			player.play("stand")

func _input(event):
	if event.is_action_pressed("shoot") and can_shoot == true:
		shoot()

func shoot():
	can_shoot = false
	var bullet = bullet_scene.instantiate()
	bullet.position = position
	bullet.direction = last_direction
	get_parent().add_child(bullet)
	shoot_sound.play()
	
	if bullet.has_node("AnimatedSprite2D"):
		bullet.get_node("AnimatedSprite2D").play("shootnormal")
	await get_tree().create_timer(0.5).timeout  # Wait for cooldowncan_shoot = true  # Allow shooting again	
	can_shoot = true

func take_damage(damage):
	currentHealth -= damage
	if currentHealth <= 0:
		is_alive = false #new
		die()

func die(): #new
	if is_alive == false:
		player.play("died")
		await player.animation_finished
		get_tree().change_scene_to_file("res://InGame/main_menu.tscn")
