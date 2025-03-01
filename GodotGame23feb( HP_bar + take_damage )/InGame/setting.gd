extends Control
@onready var button_click_sound = $ButtonClickSound  # Reference to the button sound
@onready var volume_slider = $VolumeSlider  # Get slider reference

func _ready() -> void:
	volume_slider.value = db_to_linear(AudioServer.get_bus_volume_db(0))
	volume_slider.value_changed.connect(_on_volume_changed)

	$BacktoMenuButton.pressed.connect(_on_backmenu_pressed)
	$BacktoMenuButton.mouse_entered.connect(_on_button_hover.bind($BacktoMenuButton, 1.1))
	$BacktoMenuButton.mouse_exited.connect(_on_button_hover.bind($BacktoMenuButton, 1.0))

func _on_volume_changed(value: float):
	AudioServer.set_bus_volume_db(0, linear_to_db(value)) 

func _on_button_hover(button, scale_factor):
	var tween = create_tween()
	tween.tween_property(button, "scale", Vector2(scale_factor, scale_factor), 0.2).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)	

func _on_backmenu_pressed():
	button_click_sound.play()
	await get_tree().create_timer(0.4).timeout
	get_tree().change_scene_to_file("res://InGame/main_menu.tscn")
