(function() {
    function setupMobileControls() {
        // Only run on devices with touch capabilities
        if (!('ontouchstart' in window) && navigator.maxTouchPoints === 0) return;

        let overlay = document.createElement('div');
        overlay.id = "mobile-controls-overlay";
        overlay.style.position = 'absolute';
        overlay.style.top = '0';
        overlay.style.left = '0';
        overlay.style.width = '100vw';
        overlay.style.height = '100vh';
        overlay.style.pointerEvents = 'none'; // Let background pass through
        overlay.style.zIndex = '9999';

        // Ensure landscape orientation request if supported
        try {
            if(screen.orientation && screen.orientation.lock) {
                screen.orientation.lock('landscape').catch(e => console.log(e));
            }
        } catch(e) {}

        // --- 1. Movement Pad ---
        let movePadArea = document.createElement('div');
        movePadArea.style.position = 'absolute';
        movePadArea.style.bottom = '10vh';
        movePadArea.style.left = '5vw';
        movePadArea.style.width = '150px';
        movePadArea.style.height = '150px';
        movePadArea.style.borderRadius = '50%';
        movePadArea.style.backgroundColor = 'rgba(255, 255, 255, 0.2)';
        movePadArea.style.pointerEvents = 'auto'; // Catch touches here
        movePadArea.style.touchAction = 'none';

        let moveKnob = document.createElement('div');
        moveKnob.style.position = 'absolute';
        moveKnob.style.top = '50px';
        moveKnob.style.left = '50px';
        moveKnob.style.width = '50px';
        moveKnob.style.height = '50px';
        moveKnob.style.borderRadius = '50%';
        moveKnob.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
        moveKnob.style.pointerEvents = 'none';

        movePadArea.appendChild(moveKnob);
        overlay.appendChild(movePadArea);

        let centerMoveX = 75, centerMoveY = 75;

        const KEY_W = 87;
        const KEY_S = 83;
        const KEY_A = 65;
        const KEY_D = 68;

        let activeKeys = new Set();

        function getCodeStr(keyCode) {
            if (keyCode === 87) return "KeyW";
            if (keyCode === 83) return "KeyS";
            if (keyCode === 65) return "KeyA";
            if (keyCode === 68) return "KeyD";
            if (keyCode === 32) return "Space";
            if (keyCode === 16) return "ShiftLeft";
            return "Key" + String.fromCharCode(keyCode);
        }

        function sendKey(keyCode, isDown) {
            if (isDown && !activeKeys.has(keyCode)) {
                activeKeys.add(keyCode);
                let evt = new KeyboardEvent('keydown', { keyCode: keyCode, which: keyCode, code: getCodeStr(keyCode), bubbles: true, cancelable: true });
                Object.defineProperty(evt, 'keyCode', {get: () => keyCode});
                Object.defineProperty(evt, 'which', {get: () => keyCode});
                window.dispatchEvent(evt);
            } else if (!isDown && activeKeys.has(keyCode)) {
                activeKeys.delete(keyCode);
                let evt = new KeyboardEvent('keyup', { keyCode: keyCode, which: keyCode, code: getCodeStr(keyCode), bubbles: true, cancelable: true });
                Object.defineProperty(evt, 'keyCode', {get: () => keyCode});
                Object.defineProperty(evt, 'which', {get: () => keyCode});
                window.dispatchEvent(evt);
            }
        }

        movePadArea.addEventListener('touchstart', handleMove, {passive: false});
        movePadArea.addEventListener('touchmove', handleMove, {passive: false});
        movePadArea.addEventListener('touchend', handleMoveEnd, {passive: false});
        movePadArea.addEventListener('touchcancel', handleMoveEnd, {passive: false});

        function handleMove(e) {
            e.preventDefault();
            let touch = e.changedTouches[0];
            let rect = movePadArea.getBoundingClientRect();
            let x = touch.clientX - rect.left;
            let y = touch.clientY - rect.top;

            let dx = x - centerMoveX;
            let dy = y - centerMoveY;
            let dist = Math.sqrt(dx*dx + dy*dy);
            let maxDist = 75;

            if (dist > maxDist) {
                x = centerMoveX + (dx / dist) * maxDist;
                y = centerMoveY + (dy / dist) * maxDist;
            }

            moveKnob.style.left = (x - 25) + 'px';
            moveKnob.style.top = (y - 25) + 'px';

            // 360 movement to 8-way WASD mapping
            // Deadzone of 20px
            sendKey(KEY_W, dy < -20);
            sendKey(KEY_S, dy > 20);
            sendKey(KEY_A, dx < -20);
            sendKey(KEY_D, dx > 20);
        }

        function handleMoveEnd(e) {
            e.preventDefault();
            moveKnob.style.left = '50px';
            moveKnob.style.top = '50px';
            sendKey(KEY_W, false);
            sendKey(KEY_S, false);
            sendKey(KEY_A, false);
            sendKey(KEY_D, false);
        }

        // --- 2. Action Buttons ---
        let buttonsArea = document.createElement('div');
        buttonsArea.style.position = 'absolute';
        buttonsArea.style.bottom = '10vh';
        buttonsArea.style.right = '5vw';
        buttonsArea.style.width = '200px';
        buttonsArea.style.height = '150px';
        buttonsArea.style.pointerEvents = 'none';

        function createBtn(text, x, y, size, keyOrAction) {
            let btn = document.createElement('div');
            btn.style.position = 'absolute';
            btn.style.left = x + 'px';
            btn.style.top = y + 'px';
            btn.style.width = size + 'px';
            btn.style.height = size + 'px';
            btn.style.borderRadius = '50%';
            btn.style.backgroundColor = 'rgba(255, 255, 255, 0.2)';
            btn.style.color = 'white';
            btn.style.display = 'flex';
            btn.style.alignItems = 'center';
            btn.style.justifyContent = 'center';
            btn.style.pointerEvents = 'auto';
            btn.style.touchAction = 'none';
            btn.style.userSelect = 'none';
            btn.style.fontWeight = 'bold';
            btn.style.fontFamily = 'sans-serif';
            btn.style.fontSize = (size * 0.25) + 'px';
            btn.innerText = text;

            let isToggled = false;

            btn.addEventListener('touchstart', e => {
                e.preventDefault();
                btn.style.backgroundColor = 'rgba(255, 255, 255, 0.5)';
                if (typeof keyOrAction === 'number') {
                    if (keyOrAction === 16) { // Shift toggle
                        isToggled = !isToggled;
                        btn.style.backgroundColor = isToggled ? 'rgba(255, 255, 255, 0.8)' : 'rgba(255, 255, 255, 0.5)';
                        sendKey(keyOrAction, isToggled);
                    } else {
                        sendKey(keyOrAction, true);
                    }
                } else if (typeof keyOrAction === 'function') {
                    keyOrAction(true, e);
                }
            }, {passive: false});

            btn.addEventListener('touchend', e => {
                e.preventDefault();
                if (typeof keyOrAction === 'number') {
                    if (keyOrAction !== 16) { // Shift is toggle, so don't reset on touchend
                        btn.style.backgroundColor = 'rgba(255, 255, 255, 0.2)';
                        sendKey(keyOrAction, false);
                    }
                } else if (typeof keyOrAction === 'function') {
                    btn.style.backgroundColor = 'rgba(255, 255, 255, 0.2)';
                    keyOrAction(false, e);
                }
            }, {passive: false});

            return btn;
        }

        // Jump (Space: 32)
        buttonsArea.appendChild(createBtn('JUMP', 120, 80, 60, 32));

        // Shift (Shift: 16)
        buttonsArea.appendChild(createBtn('SHIFT', 0, 80, 50, 16));

        let gameCanvas = null;
        function getCanvas() {
            if(!gameCanvas) gameCanvas = document.querySelector('canvas');
            return gameCanvas;
        }

        function simMouse(btn, isDown, e) {
            let c = getCanvas();
            if (!c) return;
            let rect = c.getBoundingClientRect();
            // Try to aim at the center of the screen
            let cx = rect.left + rect.width / 2;
            let cy = rect.top + rect.height / 2;

            let evt = new MouseEvent(isDown ? 'mousedown' : 'mouseup', {
                clientX: cx,
                clientY: cy,
                button: btn,
                buttons: isDown ? (btn===0?1:(btn===2?2:0)) : 0,
                bubbles: true,
                cancelable: true
            });
            c.dispatchEvent(evt);

            // For Eaglercraft sometimes we need contextmenu for right click
            if (btn === 2 && isDown) {
                let ctxEvt = new MouseEvent('contextmenu', {
                    clientX: cx,
                    clientY: cy,
                    button: 2,
                    buttons: 2,
                    bubbles: true,
                    cancelable: true
                });
                c.dispatchEvent(ctxEvt);
            }
        }

        buttonsArea.appendChild(createBtn('L-CLK', 60, 20, 50, (down, e) => simMouse(0, down, e)));
        buttonsArea.appendChild(createBtn('R-CLK', 120, 0, 50, (down, e) => simMouse(2, down, e)));

        overlay.appendChild(buttonsArea);
        document.body.appendChild(overlay);
    }

    // Wait until game canvas might exist
    window.addEventListener('load', () => {
        setTimeout(setupMobileControls, 2000);
    });
})();
