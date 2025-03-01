extends Control
@onready var button_click_sound = $ButtonClickSound  # Reference to the button sound

func _ready():
	# Connect button signals
	$StartButton.pressed.connect(_on_start_pressed)
	$ExitButton.pressed.connect(_on_exit_pressed)
	$SettingButton.pressed.connect(_on_setting_pressed)

	# Connect hover animations
	$StartButton.mouse_entered.connect(_on_button_hover.bind($StartButton, 1.1))
	$StartButton.mouse_exited.connect(_on_button_hover.bind($StartButton, 1.0))
	$ExitButton.mouse_entered.connect(_on_button_hover.bind($ExitButton, 1.1))
	$ExitButton.mouse_exited.connect(_on_button_hover.bind($ExitButton, 1.0))
	$SettingButton.mouse_entered.connect(_on_button_hover.bind($SettingButton, 1.1))
	$SettingButton.mouse_exited.connect(_on_button_hover.bind($SettingButton, 1.0))

func _on_button_hover(button, scale_factor):
	var tween = create_tween()
	tween.tween_property(button, "scale", Vector2(scale_factor, scale_factor), 0.2).set_trans(Tween.TRANS_LINEAR).set_ease(Tween.EASE_OUT)

func _on_start_pressed():
	button_click_sound.play()
	await get_tree().create_timer(0.4).timeout 
	get_tree().change_scene_to_file("res://InGame/GameScence.tscn")

func _on_setting_pressed():
	button_click_sound.play()
	await get_tree().create_timer(0.4).timeout
	get_tree().change_scene_to_file("res://InGame/setting.tscn")

func _on_exit_pressed():
	button_click_sound.play()
	await get_tree().create_timer(0.4).timeout
	get_tree().quit()
