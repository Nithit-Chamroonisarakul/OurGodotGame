extends Control

func _ready():
	# เชื่อมปุ่มเข้ากับฟังก์ชัน
	$StartButton.pressed.connect(_on_start_pressed)
	$ExitButton.pressed.connect(_on_exit_pressed)

	# เชื่อม event hover เข้า Tween Animation
	$StartButton.mouse_entered.connect(_on_button_hover.bind($StartButton, 1.2))  # ซูมเข้า
	$StartButton.mouse_exited.connect(_on_button_hover.bind($StartButton, 1.0))  # กลับขนาดเดิม
	$ExitButton.mouse_entered.connect(_on_button_hover.bind($ExitButton, 1.2))
	$ExitButton.mouse_exited.connect(_on_button_hover.bind($ExitButton, 1.0))

func _on_button_hover(button, scale_factor):
	var tween = create_tween()
	tween.tween_property(button, "scale", Vector2(scale_factor, scale_factor), 0.2).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)

func _on_start_pressed():
	get_tree().change_scene_to_file("res://InGame/GameScence.tscn")  # เปลี่ยนเป็น Scene เกม

func _on_exit_pressed():
	get_tree().quit()  # ออกจากเกม
