package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class FlyingBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, bird;
	float bw, bh, sw, sh, bx, by;
	float velocity = 0.0f;
	float gravity = 0.4f;
	int state = 0;
	Texture  bee1, bee2, bee3;
	float beex, bee1y, bee2y, bee3y;
	int nbees = 3;
	float beesx[] = new float[nbees];
	float beesy[][] = new float[3][nbees];
	Circle c_bird, c_bee1[], c_bee2[], c_bee3[];

	ShapeRenderer sr;

	int score = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Full-Background.png");
		bird = new Texture("frame-1.png");
		bee1 = new Texture("1.png");
		bee2 = new Texture("1.png");
		bee3 = new Texture("1.png");

		bw = Gdx.graphics.getWidth()/13;
		bh = Gdx.graphics.getHeight()/11;
		sw = Gdx.graphics.getWidth();
		sh = Gdx.graphics.getHeight();
		bx = Gdx.graphics.getWidth()/4;
		by = Gdx.graphics.getHeight();

		c_bird = new Circle();
		c_bee1 = new Circle[nbees];
		c_bee2 = new Circle[nbees];
		c_bee3 = new Circle[nbees];

		sr = new ShapeRenderer();



		beex = sw;
		bee1y =  200;
		bee2y = 400;
		bee3y = 600;

		for (int i = 0; i < nbees; i++)
		{
			beesx[i] = sw + i * sw/2;
			Random r1 = new Random();
			Random r2 = new Random();
			Random r3 = new Random();

			beesy[0][i] = r1.nextFloat() * sh;
			beesy[1][i] = r2.nextFloat() * sh;
			beesy[2][i] = r3.nextFloat() * sh;

			c_bee1[i] = new Circle();
			c_bee2[i] = new Circle();
			c_bee3[i] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bird, bx, by, bw, bh);


		if (state == 1)
		{
			if (Gdx.input.justTouched())
			{
				velocity = -15;
			}
			for (int i = 0; i < nbees; i++)
			{
				if (beesx[i] < 0)
				{
					beesx[i] = nbees * sw/2;
					Random r1 = new Random();
					Random r2 = new Random();
					Random r3 = new Random();

					beesy[0][i] = r1.nextFloat() * sh;
					beesy[1][i] = r2.nextFloat() * sh;
					beesy[2][i] = r3.nextFloat() * sh;
				}

				if (bx > beesx[i])
				{
					score++;
					System.out.println(score);
				}
				batch.draw(bee1, beesx[i], beesy[0][i], bw, bh);
				batch.draw(bee2, beesx[i], beesy[1][i], bw, bh);
				batch.draw(bee3, beesx[i], beesy[2][i], bw, bh);
				beesx[i] = beesx[i] - 10;


			}

			if (by < bh)
			{
				by = sw / 2;
				velocity = 0;
				state = 0;
			}
			else
			{
				velocity = velocity + gravity;
				by = by - velocity;
			}
		}
		else if (state == 2)
		{
			if (Gdx.input.justTouched())
			{
				bx = Gdx.graphics.getWidth()/4;
				by = Gdx.graphics.getHeight();

				for (int i = 0; i < nbees; i++)
				{
					beesx[i] = sw + i * sw/2;
					Random r1 = new Random();
					Random r2 = new Random();
					Random r3 = new Random();

					beesy[0][i] = r1.nextFloat() * sh;
					beesy[1][i] = r2.nextFloat() * sh;
					beesy[2][i] = r3.nextFloat() * sh;

					c_bee1[i] = new Circle();
					c_bee2[i] = new Circle();
					c_bee3[i] = new Circle();
				}

				state = 1;
			}
		}
		else if (state == 0)
		{
			if (Gdx.input.justTouched())
			{
				state = 1;
			}
		}

		c_bird.set(bx + bw / 2, by + bh / 2, bw/2);
		//sr.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < nbees; i++)
		{
			c_bee1[i].set(beesx[i] + bw / 2, beesy[0][i] + bh / 2, bw/2);
			c_bee2[i].set(beesx[i] + bw / 2, beesy[1][i] + bh / 2, bw/2);
			c_bee3[i].set(beesx[i] + bw / 2, beesy[2][i] + bh / 2, bw/2);
			if (Intersector.overlaps(c_bird, c_bee1[i]) || Intersector.overlaps(c_bird, c_bee2[i]) || Intersector.overlaps(c_bird, c_bee3[i]))
			{
				state = 2;

			}

		/*	sr.setColor(Color.BLUE);
			sr.circle(beesx[i] + bw / 2, beesy[0][i] + bh / 2, bw/2);
			sr.circle(beesx[i] + bw / 2, beesy[1][i] + bh / 2, bw/2);
			sr.circle(beesx[i] + bw / 2, beesy[2][i] + bh / 2, bw/2);
			sr.circle(bx + bw / 2, by + bh / 2, bw/2);*/
		}
		//sr.end();
		batch.end();
	}

	
	@Override
	public void dispose () {

	}
}
