with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "JUMP(EnumTouchControlPos.BOTTOM_RIGHT, 64, 64, 36, (enumIn, x, y) -> {" in line:
        out_lines.append("\tJOYSTICK(EnumTouchControlPos.BOTTOM_LEFT, 60, 60, 60, null, (enumIn, x, y, pressed, mc, res) -> {\n")
        out_lines.append("\t\t// Custom render for joystick\n")
        out_lines.append("\t\t// Left empty here, will inject render logic soon\n")
        out_lines.append("\t}),\n\t\n\t")
        out_lines.append(line)
    elif "case IN_GAME:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, true);\n")
    elif "case IN_GAME_WALK:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, true);\n")
    elif "case IN_GAME_CAN_FLY:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, true);\n")
    elif "case IN_GAME_WALK_CAN_FLY:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, true);\n")
    elif "case IN_GAME_FLYING:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, true);\n")
    elif "case IN_GAME_WALK_FLYING:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, true);\n")
    elif "case IN_GUI_TYPING:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, false);\n")
    elif "case IN_GUI_NO_BACK:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, false);\n")
    elif "case IN_GUI:" in line:
        out_lines.append(line)
        out_lines.append("\t\t\tJOYSTICK.setVisible(renderer, false);\n")
    else:
        out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "w") as f:
    f.writelines(out_lines)
