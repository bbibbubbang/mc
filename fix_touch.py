with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "} else if (ctrl == EnumTouchControl.JOYSTICK) {" in line:
        out_lines.append(line)
        out_lines.append("\t\t\t\t\t\tinput.x = x / fac;\n")
        out_lines.append("\t\t\t\t\t\tinput.y = y / fac;\n")
        out_lines.append("\t\t\t\t\t\tcontinue;\n")
        continue
    if "input.x = x / fac;" in line and "EnumTouchControl.JOYSTICK" in lines[lines.index(line)-1]:
        continue
    if "input.y = y / fac;" in line and "EnumTouchControl.JOYSTICK" in lines[lines.index(line)-2]:
        continue
    out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "w") as f:
    f.writelines(out_lines)
