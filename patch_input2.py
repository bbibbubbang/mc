with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "action.call(input.control, input.x, input.y);" in line:
        out_lines.append(line)
        # Handle unpress
    else:
        out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "w") as f:
    f.writelines(out_lines)
