extends Node2D

func _ready():
	# เชื่อมปุ่มเข้ากับฟังก์ชัน
	$StartButton.pressed.connect(_on_start_pressed)
	$ExitButton.pressed.connect(_on_exit_pressed)

func _on_start_pressed():
	get_tree().change_scene_to_file("res://InGame/GameScence.tscn")  # เปลี่ยนเป็น Scene เกม

func _on_exit_pressed():
	get_tree().quit()  # ออกจากเกม
