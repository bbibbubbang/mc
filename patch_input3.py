with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "r") as f:
    lines = f.readlines()

out_lines = []
for line in lines:
    if "touchControlPressed = newPressed;" in line:
        out_lines.append("\t\t\tfor (EnumTouchControl c : touchControlPressed) {\n")
        out_lines.append("\t\t\t\tif (!newPressed.contains(c)) {\n")
        out_lines.append("\t\t\t\t\tif (c == EnumTouchControl.ACTION_LEFT_CLICK) {\n")
        out_lines.append("\t\t\t\t\t\tnet.minecraft.client.settings.KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);\n")
        out_lines.append("\t\t\t\t\t} else if (c == EnumTouchControl.ACTION_RIGHT_CLICK) {\n")
        out_lines.append("\t\t\t\t\t\tnet.minecraft.client.settings.KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);\n")
        out_lines.append("\t\t\t\t\t}\n")
        out_lines.append("\t\t\t\t}\n")
        out_lines.append("\t\t\t}\n")
        out_lines.append(line)
    elif "touchControlPressed.clear();" in line and "mc.ingameGUI.updateTouchEagler(false);" not in lines[lines.index(line)+1]:
        out_lines.append("\t\t\tfor (EnumTouchControl c : touchControlPressed) {\n")
        out_lines.append("\t\t\t\tif (c == EnumTouchControl.ACTION_LEFT_CLICK) {\n")
        out_lines.append("\t\t\t\t\tnet.minecraft.client.settings.KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode(), false);\n")
        out_lines.append("\t\t\t\t} else if (c == EnumTouchControl.ACTION_RIGHT_CLICK) {\n")
        out_lines.append("\t\t\t\t\tnet.minecraft.client.settings.KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(), false);\n")
        out_lines.append("\t\t\t\t}\n")
        out_lines.append("\t\t\t}\n")
        out_lines.append(line)
    else:
        out_lines.append(line)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "w") as f:
    f.writelines(out_lines)
