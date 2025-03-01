extends Area2D

@export var speed: float = 400.0
var direction = Vector2.ZERO

func _process(delta):
	position += direction * speed * delta

func _on_visible_on_screen_notifier_2d_screen_exited():
	queue_free()
