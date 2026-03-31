with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "r") as f:
    lines = f.readlines()

out_lines = []
for i, line in enumerate(lines):
    if "touchControlPressed.clear();" in line and "touchControlPressed = newPressed;" not in "".join(lines[max(0, i-20):i]):
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
