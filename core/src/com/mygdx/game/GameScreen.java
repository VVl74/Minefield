package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.math.Rectangle;

import java.util.Iterator;

public class GameScreen implements Screen, InputProcessor {
	//public static final float SCR_WIDTH = 1000, SCR_HEIGHT = 2200;
	//public static final int TYPE_SHIP = 0, TYPE_ENEMY = 1;

	final  MainGame game;

	OrthographicCamera camera;
	SpriteBatch batch;
	Texture imgtank;
	Texture imgmine1;
	Texture imgmine2;
	Texture background;
	Rectangle tank;
	Vector3 touchPos;
	// List <Rectangle> landmines = new ArrayList<>();
	Array <Rectangle> landmines;
	long Time;
	long sh;

	private boolean touching = false;


	public boolean scrolled (float amountX, float amountY) {
		return false;
	}

	public boolean keyDown (int keycode)
	{
		return false;
	}

	public boolean keyUp (int keycode)
	{
		return false;
	}

	public boolean keyTyped (char character)
	{
		return false;
	}

	public boolean touchCancelled (int screenX, int screenY, int pointer, int button)
	{
		return false;
	}

	public boolean mouseMoved (int screenX, int screenY)
	{
		return false;
	}

	//@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touching = true;
		// Преобразуем координаты экрана в мировые координаты
		camera.unproject(touchPos.set(screenX, screenY, 0));
		return true; // Обработка касания продолжается
	}

	//@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touching = false;
		return true; // Обработка отпускания пальца продолжается
	}

	//@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (!touching) return false;
		// Преобразуем координаты экрана в мировые координаты
		camera.unproject(touchPos.set(screenX, screenY, 0));
		return true; // Обработка перетаскивания продолжается
	}

	
	//@Override
	public GameScreen (final MainGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 540, 1000);

		batch = new SpriteBatch();
		touchPos = new Vector3();

		imgtank = new Texture("newtank2.png");
		imgmine1 = new Texture("landmine.png");
		imgmine2 = new Texture("prmine2.png");
		background = new Texture("tekstura-gazon-trava-a04f94a.png");
		// img = new Texture("badlogic.jpg");

		tank = new Rectangle();
		tank.x = 540 / 2;
		tank.y = 20;
		tank.width = 100;
		tank.height = 200;

		landmines = new Array<Rectangle>();

		spawnMine();
	}

	public void spawnMine() {
		Rectangle mine = new Rectangle();
		mine.x = MathUtils.random(0, 476);
		mine.y = 1000;
		mine.height = 64;
		mine.width = 64;

		landmines.add(mine);
		Time = TimeUtils.nanoTime();
	}

	@Override

	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render (float d) {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		game.batch.draw(background, 0, 0);

		game.font.draw(game.batch, "SCORE:" + sh, 400, 950);

		game.batch.draw(imgtank, tank.x, tank.y);

		for (Rectangle mine: landmines) {
			game.batch.draw(imgmine1, mine.x, mine.y);
		}

		game.batch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			tank.x = tank.x - (int)(300 * 10 * Gdx.graphics.getDeltaTime());
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			tank.x = tank.x + (int)(300 * 10 * Gdx.graphics.getDeltaTime());
		}

		if (tank.x < 0) {
			tank.x = 0;
		}
		if (tank.x > 440) {
			tank.x = 440;
		}

		if (touching && touchPos.x > tank.x) { // Проверяем, находится ли точка касания правее танка
			tank.x += (int)(250 * 4 * Gdx.graphics.getDeltaTime()); // Двигаем танк вправо
		} else if (touching && touchPos.x < tank.x) { // Проверяем, находится ли точка касания левее танка
			tank.x -= (int)(250 * 4 * Gdx.graphics.getDeltaTime()); // Двигаем танк влево
		}

		if (TimeUtils.nanoTime() - Time > (1000000000 - (sh * 10000000))) {
			spawnMine();
		}

		Iterator<Rectangle> it = landmines.iterator();

		while(it.hasNext()) {
			Rectangle mine = it.next();
			mine.y = mine.y - (200 * Gdx.graphics.getDeltaTime());
			if (mine.y + 64 < 0) { // проверка на то не прошла ли мина
				// если это произолшло игрок проиграл
				//MainMenuScreen.

				it.remove();
			}

			if (mine.overlaps(tank)) { // проверка на столкновение
				// можно добавить какой-нибудь звук
				sh++;
				it.remove();
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		// batch.dispose();
		//img.dispose();
		imgmine1.dispose();
		imgmine2.dispose();
		imgtank.dispose();
	}

}
