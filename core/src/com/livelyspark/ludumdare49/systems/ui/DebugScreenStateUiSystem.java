package com.livelyspark.ludumdare49.systems.ui;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.livelyspark.ludumdare49.gameobj.ScreenState;

public class DebugScreenStateUiSystem extends EntitySystem {

    private final ScreenState state;
    private Stage stage;
    private Table table;
    private Label rumbleLabel;

    public DebugScreenStateUiSystem(ScreenState state) {
        this.state = state;
    }

    @Override
    public void addedToEngine(Engine engine) {
        stage = new Stage();
        Skin uiSkin = new Skin(Gdx.files.internal("data/ui/plain.json"));
        Drawable tableBackground = uiSkin.getDrawable("textfield");

        table = new Table(uiSkin);
        table.setBackground(tableBackground);

        table.setWidth(200);

        table.columnDefaults(0).pad(5).right();
        table.columnDefaults(1).pad(5).width(100);

        table.add("Rumble:", "small", Color.BLACK);
        rumbleLabel = table.add("", "small", Color.BLACK).getActor();
        table.row();

        table.pack();

        table.setX(0);
        table.setY(stage.getHeight() - table.getHeight() - 50);

        stage.addActor(table);
   }

    @Override
    public void removedFromEngine(Engine engine) {
    }

    @Override
    public void update(float deltaTime) {
        if(state.isDebug) {

            rumbleLabel.setText(state.cameraMode.toString());

            stage.act();
            stage.draw();
        }
    }

    private Drawable backgroundDrawable()
    {
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        return new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
    }

}
