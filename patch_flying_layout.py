with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "ACTION_LEFT_CLICK.setVisible(renderer, false);" in line and ("case IN_GAME_FLYING" in "".join(out_lines[-5:]) or "case IN_GAME_WALK_FLYING" in "".join(out_lines[-5:])):
        out_lines.append("\t\t\tACTION_LEFT_CLICK.setVisible(renderer, true);\n")
    elif "ACTION_RIGHT_CLICK.setVisible(renderer, false);" in line and ("case IN_GAME_FLYING" in "".join(out_lines[-6:]) or "case IN_GAME_WALK_FLYING" in "".join(out_lines[-6:])):
        out_lines.append("\t\t\tACTION_RIGHT_CLICK.setVisible(renderer, true);\n")
    else:
        out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "w") as f:
    f.writelines(out_lines)
