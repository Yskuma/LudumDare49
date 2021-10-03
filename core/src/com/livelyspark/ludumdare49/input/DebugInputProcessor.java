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
import com.livelyspark.ludumdare49.components.CommandComponent;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.gameobj.PowerStation;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class DebugInputProcessor implements InputProcessor {

    private final PowerStation ps;
    private final ScreenState state;

    public DebugInputProcessor(ScreenState state, PowerStation powerStation) {
        this.state = state;
        this.ps = powerStation;
    }

    @Override
    public boolean keyDown(int keycode) {

        // This one is always active
        if (keycode == Input.Keys.O) {
            state.isDebug = !state.isDebug;
        }

        // If we're not in debug turn the other stuff off
        if (!state.isDebug) {
            return false;
        }

        switch (keycode) {
            case Input.Keys.P:
                ps.isPaused = !ps.isPaused;
                break;

            case Input.Keys.LEFT_BRACKET:
                state.completedCommands.add(Commands.ControlRodDecrease);
                break;

            case Input.Keys.RIGHT_BRACKET:
                state.completedCommands.add(Commands.ControlRodIncrease);
                break;

            case Input.Keys.COMMA:
                state.completedCommands.add(Commands.CoolantPumpDecrease);
                break;

            case Input.Keys.PERIOD:
                state.completedCommands.add(Commands.CoolantPumpIncrease);
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
