package ar.edu.unq.americana.components;

import java.awt.Color;
import java.awt.Font;

import ar.edu.unq.americana.GameComponent;
import ar.edu.unq.americana.GameScene;
import ar.edu.unq.americana.appearances.Label;
import ar.edu.unq.americana.colissions.CollitionGroup;
import ar.edu.unq.americana.components.events.ScoreUpEvent;
import ar.edu.unq.americana.events.annotations.Events.Fired;
import ar.edu.unq.americana.scenes.camera.ICamera;
import ar.edu.unq.americana.scenes.camera.StaticCamera;

public class Score<SceneType extends GameScene> extends
		GameComponent<SceneType> {

	private int score;
	private final int deltaScore;
	private final Label label;

	public Score(final int deltaScore, final String font, final int fontSize,
			final Color color) {
		this(deltaScore, new Font(font, Font.BOLD, 24), color);
	}

	@Override
	public void onSceneActivated() {
		super.setX(20);
		super.setY(20);
	}

	@Override
	protected boolean isCollisionable() {
		return false;
	}

	public Score(final int deltaScore, final Font font, final Color color) {
		CollitionGroup.setUncollisionableGroup(this);
		this.score = 0;
		this.deltaScore = deltaScore;
		this.setZ(Integer.MAX_VALUE);
		this.label = new Label(font, color, String.valueOf(this.score));
		this.setAppearance(this.label);
		this.initialize();
	}

	private void initialize() {
		this.setX(10 + (this.label.getWidth() / 2));
		this.setY((this.label.getHeight() / 2));
	}

	@Fired(ScoreUpEvent.class)
	public void addPoint(final ScoreUpEvent event) {
		this.score += this.deltaScore;
		this.label.setText(String.valueOf(this.score));
		this.updateXY();
	}

	private void updateXY() {
		super.setX(10 + (this.label.getWidth() / 2));
		super.setY((this.label.getHeight() / 2));
	}

	public int getScore() {
		return this.score;
	}

	@Override
	public boolean isDestroyPending() {
		return false;
	}

	@Override
	public ICamera getCamera() {
		return new StaticCamera();
	}
}
