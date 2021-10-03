package com.livelyspark.ludumdare49.input;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.livelyspark.ludumdare49.gameobj.PowerStation;

public class DebugInputProcessor implements InputProcessor {

    private final PowerStation ps;

    public DebugInputProcessor(PowerStation powerStation) {
        this.ps = powerStation;
    }

    @Override
    public boolean keyDown(int keycode) {

        // This one is always active
        if (keycode == Input.Keys.O) {
            ps.isDebug = !ps.isDebug;
        }

        // If we're not in debug turn the other stuff off
        if (!ps.isDebug) {
            return false;
        }

        switch (keycode) {
            case Input.Keys.P:
                ps.isPaused = !ps.isPaused;
                break;

            case Input.Keys.LEFT_BRACKET:
                ps.controlRodPosition -= 0.1f;
                ps.controlRodPosition = MathUtils.clamp(ps.controlRodPosition, 0f, 1f);
                break;

            case Input.Keys.RIGHT_BRACKET:
                ps.controlRodPosition += 0.1f;
                ps.controlRodPosition = MathUtils.clamp(ps.controlRodPosition, 0f, 1f);
                break;

            case Input.Keys.COMMA:
                ps.coolantPumpSpeed -= 0.1f;
                ps.coolantPumpSpeed = MathUtils.clamp(ps.coolantPumpSpeed, 0f, 1f);
                break;

            case Input.Keys.PERIOD:
                ps.coolantPumpSpeed += 0.1f;
                ps.coolantPumpSpeed = MathUtils.clamp(ps.coolantPumpSpeed, 0f, 1f);
                break;

            default:
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
