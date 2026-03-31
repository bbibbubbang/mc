with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "r") as f:
    lines = f.readlines()

import re

content = "".join(lines)
content = re.sub(r'\} else if \(ctrl == EnumTouchControl\.JOYSTICK\) \{.*?\n\s+\}',
                 '} else if (ctrl == EnumTouchControl.JOYSTICK) {\n\t\t\t\t\t\tinput.x = x / fac;\n\t\t\t\t\t\tinput.y = y / fac;\n\t\t\t\t\t\tcontinue;\n\t\t\t\t\t}',
                 content, flags=re.DOTALL)

with open("src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchControls.java", "w") as f:
    f.write(content)
