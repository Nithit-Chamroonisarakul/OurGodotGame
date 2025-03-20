extends Area2D

@export var speed: float = 1000.0
@export var lifetime: float = 2.0  # ตั้งเวลาให้กระสุนหายอัตโนมัติ

@onready var sprite = $AnimatedSprite2D
 
var direction = Vector2.ZERO
var bullet_damage: int = 10

func _ready():
	connect("body_entered", Callable(self, "_on_body_entered"))

	# ตั้ง Timer ให้กระสุนหายอัตโนมัติ
	var timer = Timer.new()
	timer.wait_time = lifetime
	timer.one_shot = true
	timer.timeout.connect(queue_free)
	add_child(timer)
	timer.start()

func _process(delta):
	position += direction * speed * delta

func _on_body_entered(body):
	if body.is_in_group("enemy"):  # ตรวจสอบว่าโดนศัตรูไหม
		body.take_damage(bullet_damage) # Enemy โดน damage
		queue_free()  # ลบกระสุน
