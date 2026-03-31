with open("src/game/java/net/minecraft/util/MovementInputFromOptions.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "this.jump = this.gameSettings.keyBindJump.isKeyDown()" in line:
        out_lines.append("\t\tif (TouchControls.isPressed(EnumTouchControl.JOYSTICK)) {\n")
        out_lines.append("\t\t\tfor (com.carrotsearch.hppc.cursors.ObjectCursor<net.lax1dude.eaglercraft.v1_8.touch_gui.TouchControlInput> input_ : TouchControls.touchControls.values()) {\n")
        out_lines.append("\t\t\t\tnet.lax1dude.eaglercraft.v1_8.touch_gui.TouchControlInput input = input_.value;\n")
        out_lines.append("\t\t\t\tif (input.control == EnumTouchControl.JOYSTICK) {\n")
        out_lines.append("\t\t\t\t\tint dx = input.x - input.startX;\n")
        out_lines.append("\t\t\t\t\tint dy = input.y - input.startY;\n")
        out_lines.append("\t\t\t\t\tfloat maxRadius = 30.0f;\n")
        out_lines.append("\t\t\t\t\tfloat normalizedX = (float) dx / maxRadius;\n")
        out_lines.append("\t\t\t\t\tfloat normalizedY = (float) dy / maxRadius;\n")
        out_lines.append("\t\t\t\t\tthis.moveStrafe += Math.max(-1.0f, Math.min(1.0f, normalizedX));\n")
        out_lines.append("\t\t\t\t\tthis.moveForward += Math.max(-1.0f, Math.min(1.0f, -normalizedY));\n")
        out_lines.append("\t\t\t\t\tbreak;\n")
        out_lines.append("\t\t\t\t}\n")
        out_lines.append("\t\t\t}\n")
        out_lines.append("\t\t}\n\n")
        out_lines.append(line)
    else:
        out_lines.append(line)

with open("src/game/java/net/minecraft/util/MovementInputFromOptions.java", "w") as f:
    f.writelines(out_lines)
