with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "JUMP.setVisible(renderer, false);" in line:
        out_lines.append("\t\t\tACTION_LEFT_CLICK.setVisible(renderer, false);\n")
        out_lines.append("\t\t\tACTION_RIGHT_CLICK.setVisible(renderer, false);\n")
        out_lines.append(line)
    elif "JUMP.setVisible(renderer, true);" in line:
        out_lines.append("\t\t\tACTION_LEFT_CLICK.setVisible(renderer, true);\n")
        out_lines.append("\t\t\tACTION_RIGHT_CLICK.setVisible(renderer, true);\n")
        out_lines.append(line)
    else:
        out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "w") as f:
    f.writelines(out_lines)
