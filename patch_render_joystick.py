with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "r") as f:
    lines = f.readlines()

out_lines = []
skip = False
for line in lines:
    if "// Custom render for joystick" in line:
        out_lines.append("\t\tmc.getTextureManager().bindTexture(TouchOverlayRenderer.spriteSheet);\n")
        out_lines.append("\t\tint[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);\n")
        out_lines.append("\t\t// Base\n")
        out_lines.append("\t\tTouchOverlayRenderer.drawTexturedModalRect(pos[0], pos[1], 102, 0, 60, 60, 2);\n")
        out_lines.append("\t\t// Knob\n")
        out_lines.append("\t\tif (pressed) {\n")
        out_lines.append("\t\t\tfor (com.carrotsearch.hppc.cursors.ObjectCursor<net.lax1dude.eaglercraft.v1_8.touch_gui.TouchControlInput> input_ : TouchControls.touchControls.values()) {\n")
        out_lines.append("\t\t\t\tnet.lax1dude.eaglercraft.v1_8.touch_gui.TouchControlInput input = input_.value;\n")
        out_lines.append("\t\t\t\tif (input.control == EnumTouchControl.JOYSTICK) {\n")
        out_lines.append("\t\t\t\t\tint dx = input.x - input.startX;\n")
        out_lines.append("\t\t\t\t\tint dy = input.y - input.startY;\n")
        out_lines.append("\t\t\t\t\tfloat maxRadius = 30.0f;\n")
        out_lines.append("\t\t\t\t\tfloat radius = (float) Math.sqrt(dx * dx + dy * dy);\n")
        out_lines.append("\t\t\t\t\tif (radius > maxRadius) {\n")
        out_lines.append("\t\t\t\t\t\tdx = (int) (dx * maxRadius / radius);\n")
        out_lines.append("\t\t\t\t\t\tdy = (int) (dy * maxRadius / radius);\n")
        out_lines.append("\t\t\t\t\t}\n")
        out_lines.append("\t\t\t\t\tTouchOverlayRenderer.drawTexturedModalRect(pos[0] + dx + 15, pos[1] + dy + 15, 102, 60, 30, 30, 2);\n")
        out_lines.append("\t\t\t\t\tbreak;\n")
        out_lines.append("\t\t\t\t}\n")
        out_lines.append("\t\t\t}\n")
        out_lines.append("\t\t} else {\n")
        out_lines.append("\t\t\tTouchOverlayRenderer.drawTexturedModalRect(pos[0] + 15, pos[1] + 15, 102, 60, 30, 30, 2);\n")
        out_lines.append("\t\t}\n")
        skip = True
        continue
    if skip and "// Left empty here, will inject render logic soon" in line:
        skip = False
        continue
    out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "w") as f:
    f.writelines(out_lines)
