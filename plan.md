1. **Remove interact button:**
   - Modify `src/game/java/net/minecraft/client/gui/GuiIngame.java` to remove `drawEaglerInteractButton` method, `interactButtonX/Y/W/H` fields, and its hit logic inside `isTouchOverlapEagler`.
   - Use `read_file` to verify the edits.

2. **Remove existing D-PAD buttons:**
   - Modify `src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/EnumTouchControl.java` to remove `DPAD_UP`, `DPAD_LEFT`, `DPAD_RIGHT`, `DPAD_DOWN`, `DPAD_UP_LEFT`, `DPAD_UP_RIGHT`. Remove these from the `setLayoutState` logic.
   - Modify `src/main/java/net/lax1dude/eaglercraft/v1_8/touch_gui/TouchOverlayRenderer.java` to remove `showDiagButtons` and related logic that switches layout states between `IN_GAME_WALK` and `IN_GAME`.
   - Modify `src/game/java/net/minecraft/util/MovementInputFromOptions.java` to remove `TouchControls.isPressed(EnumTouchControl.DPAD_...)` checks.
   - Use `read_file` to verify the edits.

3. **Add 360-degree Joystick:**
   - In `EnumTouchControl.java`, add `JOYSTICK(EnumTouchControlPos.BOTTOM_LEFT, 60, 60, 60, ...)` with logic to draw the base and knob based on input.
   - Modify `TouchControls.java` to track initial x/y coordinates in `TouchControlInput` for joystick drags and update dx/dy.
   - Update `MovementInputFromOptions.java` to calculate `moveStrafe` and `moveForward` using the tracked dx/dy from `JOYSTICK` input.
   - Use `read_file` to verify the edits.

4. **Add Action Buttons:**
   - In `EnumTouchControl.java`, add `ACTION_LEFT_CLICK(EnumTouchControlPos.BOTTOM_RIGHT, 110, 60, 36, ...)` which calls `Minecraft.getMinecraft().clickMouse()` in its `TouchAction`.
   - In `EnumTouchControl.java`, add `ACTION_RIGHT_CLICK(EnumTouchControlPos.BOTTOM_RIGHT, 10, 60, 36, ...)` which calls `Minecraft.getMinecraft().rightClickMouse()`.
   - Reposition existing `JUMP(EnumTouchControlPos.BOTTOM_RIGHT, 60, 10, 36, ...)`, `SNEAK(EnumTouchControlPos.BOTTOM_RIGHT, 110, 10, 36, ...)`, and `PICK(EnumTouchControlPos.BOTTOM_RIGHT, 10, 110, 36, ...)`. Add them to the layout states in `EnumTouchControl.setLayoutState`.
   - Use `read_file` to verify the edits.

5. **Local Verification:**
   - Compile via `./gradlew makeMainOfflineDownload` and test by checking locally using `python3 -m http.server`.

6. **Pre Commit Steps:**
   - Complete pre-commit steps to ensure proper testing, verification, review, and reflection are done.

7. **Submit:**
   - Submit changes.
