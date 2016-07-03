package game.entities.player;

import java.awt.Color;

import game.GameLib;
import game.entities.Entity;
import game.entities.collision.Collidable;
import game.entities.collision.CollisionChecker;
import game.entities.hud.Infos;
import game.entities.hud.LifeBar;
import game.entities.projectiles.Projectile;
import game.entities.weapons.Weapon;
import game.entities.weapons.WeaponsFactory.WeaponType;
import game.screenstate.MainGameScreen;
import game.screenstate.ScreenState;
import geometry.Vector2D;

public class Player extends Entity implements Collidable {

	protected LifeBar _lifeBar;
	protected Weapon _weapon;
	protected boolean hasShield;
	protected Infos infos;
	protected Color normalColor = Color.BLUE, getHitColor = Color.WHITE, currentColor;
	protected Projectile shield;
	private MainGameScreen mainGameScreen;

	public MainGameScreen getMainGameScreen() {
		return mainGameScreen;
	}

	public Player(Vector2D position, Vector2D velocity, double radius, ScreenState screenState, MainGameScreen mainGameScreen) {

		super(position, velocity, radius, screenState);
		this.mainGameScreen = mainGameScreen; 
		position.setXY((GameLib.WIDTH - (float) radius) / 2.0f, GameLib.HEIGHT * 0.9f);
		this.setState(new ActivePlayerState(this));

		_screenState.getEntityManager().setPlayer(this);
		this._collision = new PlayerCollisionState(this);

		currentColor = normalColor;
		this._type = EntityType.Player;
		_lifeBar = new LifeBar(mainGameScreen.getPlayerHealthPoints(), this);
		infos = new Infos(this);

	}

	public void addShield() {

		shield = new Projectile(null, new Vector2D(0, 0), getScreenState(), true, WeaponType.PlayerShield,
				(int) getRadius(), this);
	}

	@Override
	public boolean CheckCollision(Entity other) {
		return CollisionChecker.CheckCollision(this, other);
	}

	public Color getColor() {
		return currentColor;
	}

	public void getHit() {
		// TODO Auto-generated method stub
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 4; i++) {
					if (i % 2 == 0)
						currentColor = getHitColor;
					else
						currentColor = normalColor;

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		t.start();
	}

	public boolean isDead() {
		return _lifeBar.getCurrentHealthPoints() == 0;
	}

	@Override
	public void Render() {
		this._state.Render();
	}

	public void setWeapon(Weapon weapon) {
		this._weapon = weapon;
	}

	public void TryShoot() {
		if (_weapon == null)
			return;
		_weapon.Shoot();
	}

	@Override
	public void Update() {
		this._state.Update();
	}

}
