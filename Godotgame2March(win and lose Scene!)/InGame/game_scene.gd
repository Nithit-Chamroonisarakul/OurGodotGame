extends Node2D

func _ready():
	check_enemies()

func check_enemies():
	var enemy = get_tree().get_nodes_in_group("enemy")
	if enemy.size() ==1:
		go_to_scene()

func on_enemy_died():
	check_enemies()

func go_to_scene():
	await get_tree().create_timer(0.1).timeout  # Optional delay
	get_tree().change_scene_to_file("res://InGame/GameClearScene.tscn")  # Ensure this path is correct
