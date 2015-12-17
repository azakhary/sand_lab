package com.underwater.lab.sand;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;

public class SandLab extends ApplicationAdapter {

	private PerspectiveCamera camera;

	private Texture heightMap;
	private TriangleStrip triangleStrip;

	private RenderContext renderContext;
	private DefaultShader shader;

	private CameraInputController camController;

	@Override
	public void create () {
		heightMap = new Texture(Gdx.files.internal("heightMap.png"));

		camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(10f, 10f, 10f);
		camera.lookAt(0f, 0f, 0f);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();

		camController = new CameraInputController(camera);
		camController.target.set(camera.position);
		Gdx.input.setInputProcessor(camController);

		triangleStrip = new TriangleStrip();
		triangleStrip.create(1);

		renderContext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		String vert = Gdx.files.internal("shader.vert").readString();
		String frag = Gdx.files.internal("shader.frag").readString();
		shader = new DefaultShader(triangleStrip, new DefaultShader.Config(vert, frag));
		shader.init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

		camController.update();

		renderContext.begin();
		shader.begin(camera, renderContext);
		Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
		Gdx.graphics.getGL20().glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shader.render(triangleStrip);
		shader.end();
		renderContext.end();
	}
}