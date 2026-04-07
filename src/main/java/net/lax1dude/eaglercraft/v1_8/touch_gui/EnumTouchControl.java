/*
 * Copyright (c) 2024 lax1dude, ayunami2000. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package net.lax1dude.eaglercraft.v1_8.touch_gui;

import net.lax1dude.eaglercraft.v1_8.Touch;
import net.lax1dude.eaglercraft.v1_8.minecraft.EnumInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;

public enum EnumTouchControl {
	
	JOYSTICK(EnumTouchControlPos.BOTTOM_LEFT, 32, 32, 64, null, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		// Draw base
		Gui.drawRect(pos[0], pos[1], pos[0] + 64, pos[1] + 64, 0x44000000);

		int knobX = pos[0] + 16;
		int knobY = pos[1] + 16;

		if(pressed) {
			float len = (float)Math.sqrt(TouchControls.joystickX * TouchControls.joystickX + TouchControls.joystickY * TouchControls.joystickY);
			float maxLen = 1.0f;
			if(len > maxLen) len = maxLen;
			knobX += (int)(TouchControls.joystickX * 16.0f * len);
			knobY -= (int)(TouchControls.joystickY * 16.0f * len); // Y is inverted on screen
		}

		// Draw knob
		Gui.drawRect(knobX, knobY, knobX + 32, knobY + 32, 0x88FFFFFF);
	}),
	
	
	
	
	JUMP(EnumTouchControlPos.BOTTOM_RIGHT, 60, 16, 44, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			if(TouchControls.isSneakToggled) {
				TouchControls.resetSneakInvalidate();
			}
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "JUMP";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	SNEAK(EnumTouchControlPos.BOTTOM_RIGHT, 60, 104, 44, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			enumIn.invalid = true;
			TouchControls.isSneakToggled = !TouchControls.isSneakToggled;
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed || TouchControls.isSneakToggled ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "SNEAK";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	BACK(EnumTouchControlPos.TOP, -18, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			if(Touch.isDeviceKeyboardOpenMAYBE()) {
				Touch.closeDeviceKeyboard();
			}else {
				Minecraft mc = Minecraft.getMinecraft();
				if(mc.thePlayer != null) {
					mc.setIngameFocus();
				}else if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiMainMenu)) {
					mc.displayGuiScreen(null);
				}
			}
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "ESC";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	BACK_DISABLED(EnumTouchControlPos.TOP, -18, 0, 36, null, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
	}),
	
	
	KEYBOARD(EnumTouchControlPos.TOP, 18, 0, 36, null, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "KBD";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	PAUSE(EnumTouchControlPos.TOP, -18, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.displayInGameMenu();
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "PAUSE";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	CHAT(EnumTouchControlPos.TOP, 18, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.displayGuiScreen(new GuiChat());
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "CHAT";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	F3(EnumTouchControlPos.TOP, 144, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			Minecraft mc = Minecraft.getMinecraft();
			GameSettings gameSettings = mc.gameSettings;
			gameSettings.showDebugInfo = !gameSettings.showDebugInfo;
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "F3";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	F5(EnumTouchControlPos.TOP, 90, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			Minecraft mc = Minecraft.getMinecraft();
			mc.togglePerspective();
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "F5";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	PASTE(EnumTouchControlPos.TOP, 144, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;
			if(screen != null) {
				screen.fireInputEvent(EnumInputEvent.CLIPBOARD_PASTE, null);
			}
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "PASTE";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	COPY(EnumTouchControlPos.TOP, 90, 0, 36, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			GuiScreen screen = Minecraft.getMinecraft().currentScreen;
			if(screen != null) {
				screen.fireInputEvent(EnumInputEvent.CLIPBOARD_COPY, null);
			}
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "COPY";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	PICK(EnumTouchControlPos.BOTTOM_RIGHT, 60, 148, 40, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			Minecraft.getMinecraft().middleClickMouse();
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "PICK";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	FLY(EnumTouchControlPos.BOTTOM_RIGHT, 104, 60, 44, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			TouchControls.resetSneakInvalidate();
			EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
			player.jump();
			player.capabilities.isFlying = true;
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "FLY";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	FLY_UP(EnumTouchControlPos.BOTTOM_RIGHT, 60, 104, 44, null, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "UP";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	FLY_DOWN(EnumTouchControlPos.BOTTOM_RIGHT, 60, 16, 44, null, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "DOWN";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	}),
	
	
	FLY_END(EnumTouchControlPos.BOTTOM_RIGHT, 16, 60, 44, (enumIn, x, y) -> {
		if(!TouchControls.isPressed(enumIn)) {
			Minecraft.getMinecraft().thePlayer.capabilities.isFlying = false;
		}
	}, (enumIn, x, y, pressed, mc, res) -> {
		int[] pos = enumIn.getLocation(res, TouchOverlayRenderer._fuck);
		int color = pressed ? 0xAAFFFFFF : 0x88000000;
		int size = enumIn.getSize();
		Gui.drawRect(pos[0], pos[1], pos[0] + size, pos[1] + size, color);
		String text = "END";
		int textWidth = mc.fontRendererObj.getStringWidth(text);
		mc.fontRendererObj.drawStringWithShadow(text, pos[0] + (size - textWidth) / 2.0f, pos[1] + (size - mc.fontRendererObj.FONT_HEIGHT) / 2.0f, 0xFFFFFFFF);
	});

	public static interface TouchAction {
		void call(EnumTouchControl enumIn, int x, int y);
	}

	public static interface TouchRender {
		void call(EnumTouchControl enumIn, int x, int y, boolean pressed, Minecraft mc, ScaledResolution res);
	}

	protected final EnumTouchControlPos pos;
	protected final int offX;
	protected final int offY;
	protected final int size;
	protected final TouchAction action;
	protected final TouchRender render;

	protected boolean visible = true;
	protected boolean invalid = true;
	
	public static final EnumTouchControl[] _VALUES = values();

	EnumTouchControl(EnumTouchControlPos pos, int offX, int offY, int size, TouchAction action, TouchRender render) {
		this.pos = pos;
		this.offX = offX;
		this.offY = offY;
		this.size = size;
		this.action = action;
		this.render = render;
	}

	public int[] getLocation(ScaledResolution scaledResolution, int[] loc) {
		if(loc == null) {
			loc = new int[2];
		}
		int sz = size;
		switch (pos) {
			case TOP_LEFT:
				loc[0] = offX;
				loc[1] = offY;
				break;
			case TOP:
				loc[0] = offX + (scaledResolution.getScaledWidth() - sz) / 2;
				loc[1] = offY;
				break;
			case TOP_RIGHT:
				loc[0] = -offX + (scaledResolution.getScaledWidth() - sz);
				loc[1] = offY;
				break;
			case LEFT:
				loc[0] = offX;
				loc[1] = offY + (scaledResolution.getScaledHeight() - sz) / 2;
				break;
			case RIGHT:
				loc[0] = -offX + (scaledResolution.getScaledWidth() - sz);
				loc[1] = offY + (scaledResolution.getScaledHeight() - sz) / 2;
				break;
			case BOTTOM_LEFT:
				loc[0] = offX;
				loc[1] = -offY + (scaledResolution.getScaledHeight() - sz);
				break;
			case BOTTOM:
				loc[0] = offX + (scaledResolution.getScaledWidth() - sz) / 2;
				loc[1] = -offY + (scaledResolution.getScaledHeight() - sz);
				break;
			case BOTTOM_RIGHT:
				loc[0] = -offX + (scaledResolution.getScaledWidth() - sz);
				loc[1] = -offY + (scaledResolution.getScaledHeight() - sz);
				break;
		}
		return loc;
	}

	public void setVisible(TouchOverlayRenderer renderer, boolean vis) {
		if(visible != vis) {
			visible = vis;
			invalid = true;
			if(vis) {
				renderer.invalidate();
			}else {
				renderer.invalidateDeep();
			}
		}
	}

	public int getSize() {
		return size;
	}

	public TouchAction getAction() {
		return action;
	}

	public TouchRender getRender() {
		return render;
	}

	protected static EnumTouchLayoutState currentLayout = null;

	public static void setLayoutState(TouchOverlayRenderer renderer, EnumTouchLayoutState layout) {
		if(layout == currentLayout) return;
		switch(layout) {
		case IN_GUI:
			JOYSTICK.setVisible(renderer, false);
			JUMP.setVisible(renderer, false);
			SNEAK.setVisible(renderer, false);
			BACK.setVisible(renderer, true);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, true);
			PAUSE.setVisible(renderer, false);
			CHAT.setVisible(renderer, false);
			F3.setVisible(renderer, false);
			F5.setVisible(renderer, false);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, false);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GUI_TYPING:
			JOYSTICK.setVisible(renderer, false);
			JUMP.setVisible(renderer, false);
			SNEAK.setVisible(renderer, false);
			BACK.setVisible(renderer, true);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, true);
			PAUSE.setVisible(renderer, false);
			CHAT.setVisible(renderer, false);
			F3.setVisible(renderer, false);
			F5.setVisible(renderer, false);
			PASTE.setVisible(renderer, true);
			COPY.setVisible(renderer, true);
			PICK.setVisible(renderer, false);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GUI_NO_BACK:
			JOYSTICK.setVisible(renderer, false);
			JUMP.setVisible(renderer, false);
			SNEAK.setVisible(renderer, false);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, true);
			KEYBOARD.setVisible(renderer, true);
			PAUSE.setVisible(renderer, false);
			CHAT.setVisible(renderer, false);
			F3.setVisible(renderer, false);
			F5.setVisible(renderer, false);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, false);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GAME:
			JOYSTICK.setVisible(renderer, true);
			JUMP.setVisible(renderer, true);
			SNEAK.setVisible(renderer, true);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, false);
			PAUSE.setVisible(renderer, true);
			CHAT.setVisible(renderer, true);
			F3.setVisible(renderer, true);
			F5.setVisible(renderer, true);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, true);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GAME_WALK:
			JOYSTICK.setVisible(renderer, true);
			JUMP.setVisible(renderer, true);
			SNEAK.setVisible(renderer, true);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, false);
			PAUSE.setVisible(renderer, true);
			CHAT.setVisible(renderer, true);
			F3.setVisible(renderer, true);
			F5.setVisible(renderer, true);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, true);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GAME_CAN_FLY:
			JOYSTICK.setVisible(renderer, true);
			JUMP.setVisible(renderer, true);
			SNEAK.setVisible(renderer, true);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, false);
			PAUSE.setVisible(renderer, true);
			CHAT.setVisible(renderer, true);
			F3.setVisible(renderer, true);
			F5.setVisible(renderer, true);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, true);
			FLY.setVisible(renderer, true);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GAME_WALK_CAN_FLY:
			JOYSTICK.setVisible(renderer, true);
			JUMP.setVisible(renderer, true);
			SNEAK.setVisible(renderer, true);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, false);
			PAUSE.setVisible(renderer, true);
			CHAT.setVisible(renderer, true);
			F3.setVisible(renderer, true);
			F5.setVisible(renderer, true);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, true);
			FLY.setVisible(renderer, true);
			FLY_UP.setVisible(renderer, false);
			FLY_DOWN.setVisible(renderer, false);
			FLY_END.setVisible(renderer, false);
			break;
		case IN_GAME_FLYING:
			JOYSTICK.setVisible(renderer, true);
			JUMP.setVisible(renderer, false);
			SNEAK.setVisible(renderer, true);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, false);
			PAUSE.setVisible(renderer, true);
			CHAT.setVisible(renderer, true);
			F3.setVisible(renderer, true);
			F5.setVisible(renderer, true);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, true);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, true);
			FLY_DOWN.setVisible(renderer, true);
			FLY_END.setVisible(renderer, true);
			break;
		case IN_GAME_WALK_FLYING:
			JOYSTICK.setVisible(renderer, true);
			JUMP.setVisible(renderer, false);
			SNEAK.setVisible(renderer, true);
			BACK.setVisible(renderer, false);
			BACK_DISABLED.setVisible(renderer, false);
			KEYBOARD.setVisible(renderer, false);
			PAUSE.setVisible(renderer, true);
			CHAT.setVisible(renderer, true);
			F3.setVisible(renderer, true);
			F5.setVisible(renderer, true);
			PASTE.setVisible(renderer, false);
			COPY.setVisible(renderer, false);
			PICK.setVisible(renderer, true);
			FLY.setVisible(renderer, false);
			FLY_UP.setVisible(renderer, true);
			FLY_DOWN.setVisible(renderer, true);
			FLY_END.setVisible(renderer, true);
			break;
		default:
			throw new IllegalStateException();
		}
		currentLayout = layout;
	}

}