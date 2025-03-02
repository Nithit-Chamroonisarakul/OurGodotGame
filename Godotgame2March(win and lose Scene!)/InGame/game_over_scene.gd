extends Control

@onready var button_click_sound = $ButtonClickSound
@onready var gameover_text = $gameover
@onready var youdied_text = $youdied
@onready var back_menu_button = $BacktoMenuButton

func _ready():
	gameover_text.modulate.a = 0
	youdied_text.modulate.a = 0
	back_menu_button.modulate.a = 0

	var tween = create_tween()
	tween.tween_property(gameover_text, "modulate:a", 1.0, 2.0).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)
	tween.tween_property(youdied_text, "modulate:a", 1.0, 2.0).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)
	tween.tween_property(back_menu_button, "modulate:a", 1.0, 1.0).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)

	back_menu_button.pressed.connect(_on_backmenu_pressed)
	back_menu_button.mouse_entered.connect(_on_button_hover.bind(back_menu_button, 1.1))
	back_menu_button.mouse_exited.connect(_on_button_hover.bind(back_menu_button, 1.0))

func _on_button_hover(button, scale_factor):
	var tween = create_tween()
	tween.tween_property(button, "scale", Vector2(scale_factor, scale_factor), 0.2).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)    

func _on_backmenu_pressed():
	button_click_sound.play()
	await get_tree().create_timer(0.4).timeout
	get_tree().change_scene_to_file("res://InGame/main_menu.tscn")
