with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "JUMP(EnumTouchControlPos.BOTTOM_RIGHT, 64, 64, 36, (enumIn, x, y) -> {" in line:
        # We need to change JUMP position
        out_lines.append("\tACTION_LEFT_CLICK(EnumTouchControlPos.BOTTOM_RIGHT, 110, 60, 36, (enumIn, x, y) -> {\n")
        out_lines.append("\t\tnet.minecraft.client.settings.KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), true);\n")
        out_lines.append("\t}, (enumIn, x, y, pressed, mc, res) -> {\n")
        out_lines.append("\t\tmc.getTextureManager().bindTexture(TouchOverlayRenderer.spriteSheet);\n")
        out_lines.append("\t\tint[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);\n")
        out_lines.append("\t\tTouchOverlayRenderer.drawTexturedModalRect(pos[0], pos[1], 18, 0, 18, 18, 2);\n") # Using DPAD up left icon temporarily, but doesn't matter much as long as it's a button
        out_lines.append("\t}),\n\t\n")

        out_lines.append("\tACTION_RIGHT_CLICK(EnumTouchControlPos.BOTTOM_RIGHT, 10, 60, 36, (enumIn, x, y) -> {\n")
        out_lines.append("\t\tnet.minecraft.client.settings.KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), true);\n")
        out_lines.append("\t}, (enumIn, x, y, pressed, mc, res) -> {\n")
        out_lines.append("\t\tmc.getTextureManager().bindTexture(TouchOverlayRenderer.spriteSheet);\n")
        out_lines.append("\t\tint[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);\n")
        out_lines.append("\t\tTouchOverlayRenderer.drawTexturedModalRect(pos[0], pos[1], 18, 18, 18, 18, 2);\n") # Using DPAD up right icon
        out_lines.append("\t}),\n\t\n")

        out_lines.append("\tJUMP(EnumTouchControlPos.BOTTOM_RIGHT, 60, 10, 36, (enumIn, x, y) -> {\n")
    elif "SNEAK(EnumTouchControlPos.BOTTOM_LEFT, 64, 64, 36, (enumIn, x, y) -> {" in line:
        out_lines.append("\tSNEAK(EnumTouchControlPos.BOTTOM_RIGHT, 110, 10, 36, (enumIn, x, y) -> {\n")
    elif "PICK(EnumTouchControlPos.BOTTOM_RIGHT, 62, 125, 40, (enumIn, x, y) -> {" in line:
        out_lines.append("\tPICK(EnumTouchControlPos.BOTTOM_RIGHT, 10, 110, 36, (enumIn, x, y) -> {\n")
    else:
        out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java", "w") as f:
    f.writelines(out_lines)
