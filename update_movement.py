with open("src/game/java/net/minecraft/util/MovementInputFromOptions.java", "r") as f:
    lines = f.readlines()

out_lines = []
skip = False
for line in lines:
    if "if (this.gameSettings.keyBindForward.isKeyDown() || TouchControls.isPressed(EnumTouchControl.DPAD_UP)" in line:
        out_lines.append("\t\tif (this.gameSettings.keyBindForward.isKeyDown()) {\n")
        skip = True
        continue
    if "if (this.gameSettings.keyBindBack.isKeyDown() || TouchControls.isPressed(EnumTouchControl.DPAD_DOWN)) {" in line:
        out_lines.append("\t\tif (this.gameSettings.keyBindBack.isKeyDown()) {\n")
        skip = False
        continue
    if "if (this.gameSettings.keyBindLeft.isKeyDown() || TouchControls.isPressed(EnumTouchControl.DPAD_LEFT)" in line:
        out_lines.append("\t\tif (this.gameSettings.keyBindLeft.isKeyDown()) {\n")
        skip = True
        continue
    if "if (this.gameSettings.keyBindRight.isKeyDown() || TouchControls.isPressed(EnumTouchControl.DPAD_RIGHT)" in line:
        out_lines.append("\t\tif (this.gameSettings.keyBindRight.isKeyDown()) {\n")
        skip = True
        continue

    if skip and "{" in line and "TouchControls" in line:
        continue
    if skip and "++this.moveForward" in line:
        skip = False
        out_lines.append(line)
        continue
    if skip and "++this.moveStrafe" in line:
        skip = False
        out_lines.append(line)
        continue
    if skip and "--this.moveStrafe" in line:
        skip = False
        out_lines.append(line)
        continue

    if not skip:
        out_lines.append(line)

with open("src/game/java/net/minecraft/util/MovementInputFromOptions.java", "w") as f:
    f.writelines(out_lines)
