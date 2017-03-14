package mx.rmr.demo201711;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Muestra el menú principal con escenas (Stage)
 */

class PantallaMenu extends Pantalla
{
    private Demo juego;

    // Textura del fondo
    private Texture texturaFondo;

    // Posición del fondo (estará en movimiento, scroll horizontal)
    private float xFondo = 0;
    private float velocidadFondo = 20f; // pixeles por segundo

    // Escena
    private Stage escena;

    // AssetManager global
    private final AssetManager manager;

    public PantallaMenu(Demo juego) {
        this.juego = juego;
        manager = juego.getAssetManager();
    }

    @Override
    public void show() {
        cargarTexturas();
        crearEscenaMenu();

        Gdx.input.setInputProcessor(escena);
        Gdx.input.setCatchBackKey(false);   // Aquí regresa al SO con Back
    }

    private void crearEscenaMenu() {
        escena = new Stage(vista);
        // Botón Mario
        // Carga las texturas desde el assetManager 'global'
        Texture texturaMarioNormal = manager.get("menu/btnJugarMario.png");
        Texture texturaMarioPresionado = manager.get("menu/btnJugarMarioP.png");
        ImageButton btnPlayMario = crearBoton(texturaMarioNormal, texturaMarioPresionado);
        btnPlayMario.setPosition(ANCHO/2-btnPlayMario.getWidth()/2, 2*ALTO/3);
        btnPlayMario.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Iniciar juego Mario
                juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL_MARIO));
            }
        });
        escena.addActor(btnPlayMario);
        // Botón Runner
        Texture texturaRunnerNormal = manager.get("menu/btnJugarRunner.png");
        Texture texturaRunnerPresionado = manager.get("menu/btnJugarRunnerP.png");
        ImageButton btnPlayRunner = crearBoton(texturaRunnerNormal, texturaRunnerPresionado);
        btnPlayRunner.setPosition(ANCHO/2-btnPlayRunner.getWidth()/2, ALTO/3);
        btnPlayRunner.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                // Iniciar juego Runner
            }
        });
        escena.addActor(btnPlayRunner);
    }

    private ImageButton crearBoton(Texture texturaNormal, Texture texturaPresionado) {
        TextureRegionDrawable trdNormal = new TextureRegionDrawable(new TextureRegion(texturaNormal));
        TextureRegionDrawable trdPresionado = new TextureRegionDrawable(new TextureRegion(texturaPresionado));
        ImageButton boton = new ImageButton(trdNormal, trdPresionado);
        return boton;
    }

    private void cargarTexturas() {
        // Carga las texturas desde el assetManager 'global'
        texturaFondo = manager.get("menu/fondo.jpg");
    }

    @Override
    public void render(float delta) {
        // Actualizar

        // Dibujar
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        dibujarFondo(batch, delta);
        batch.end();

        escena.draw();
    }

    private void dibujarFondo(SpriteBatch batch, float delta) {
        // Dibujar en posición actual
        batch.draw(texturaFondo, xFondo, 0);    // Primer fondo
        batch.draw(texturaFondo, xFondo+ANCHO, 0); // Segundo fondo
        // Nueva posición
        xFondo -= velocidadFondo*delta;
        if (xFondo <= -texturaFondo.getWidth()) {
            xFondo += ANCHO;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        manager.unload("menu/btnJugarMario.png");
        manager.unload("menu/btnJugarMarioP.png");
        manager.unload("menu/btnJugarRunner.png");
        manager.unload("menu/btnJugarRunnerP.png");
        manager.unload("menu/fondo.jpg");
    }
}