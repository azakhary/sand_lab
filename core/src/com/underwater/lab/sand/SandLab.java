package com.underwater.lab.sand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class SandLab extends ApplicationAdapter {

	private PerspectiveCamera camera;

	private Texture heightMap;
	private Texture normalMap;


	private TriangleStrip triangleStrip;

	private RenderContext renderContext;
	private DefaultShader shader;

	private CameraInputController camController;

	private Vector3 lightDirection;
	private Color lightColor;
	private float time;

	@Override
	public void create () {


		lightDirection = new Vector3(-1, 1, -1);
		lightColor = new Color(1, 1, 1, 0.6f);

		heightMap = new Texture(Gdx.files.internal("jrn2.png"));
		normalMap = new Texture(Gdx.files.internal("jrn2neb.png"));

		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(128f, 30f, 128f);
		camera.lookAt(256f, 0f, 256f);
		camera.near = 0.1f;
		camera.far = 600f;
		camera.update();

		camController = new CameraInputController(camera);
		camController.translateUnits = 100f;
		camController.target.set(camera.position);
		Gdx.input.setInputProcessor(camController);

		triangleStrip = new TriangleStrip();
		triangleStrip.create(heightMap.getWidth());

		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		String vert = Gdx.files.internal("shader.vert").readString();
		String frag = Gdx.files.internal("shader.frag").readString();
		shader = new DefaultShader(triangleStrip, new DefaultShader.Config(vert, frag));
		//shader.program.pedantic = false;
		shader.init();

		Vector3 v1 = new Vector3(0, 0.7f, 0.4f); // normal
		Vector3 v2 = new Vector3(-1, 1, -1); // light
		System.out.println(v1.dot(v2));
	}

	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime();

		//lightDirection = new Vector3(MathUtils.cosDeg(time*20f), 1, MathUtils.sinDeg(time*20f));
		//camera.position.set(new Vector3(lightDirection.x*128+128, lightDirection.y*90, lightDirection.z*128+128));
		//camera.update();

		Gdx.gl.glClearColor(233 / 255f, 223 / 255f, 189 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

		camController.update();


		renderContext.begin();
		shader.begin(camera, renderContext);

		normalMap.bind(2);
		shader.program.setUniformi("u_noise_texture", 2);
		normalMap.bind(1);
		shader.program.setUniformi("u_normal_map_texture", 1);
		heightMap.bind(0);
		shader.program.setUniformi("u_height_map_texture", 0);

		shader.program.setUniformf("u_light", lightDirection);
		shader.program.setUniformf("u_light_color", lightColor);
		shader.program.setUniformf("camera_dir", camera.direction);

		shader.render(triangleStrip);
		shader.end();
		renderContext.end();
	}
}
