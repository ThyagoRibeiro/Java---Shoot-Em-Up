package game.entities.huds;

import game.entities.Entity;
import game.entities.constants.EntityTypeEnum;
import game.entities.players.Player;
import game.utils.LocalTime;
import game.utils.Time;

public class Infos extends Entity {

	private Player player;
	private int stageNumber;
	private LocalTime localTime = new LocalTime();

	// Construtor

	public Infos(Player player) {
		super(player.getPosition(), player.getVelocity(), player.getRadius(),
				player.getScreenState());
		this.player = player;
		this.stageNumber = player.getMainGameScreen().getStageNumber();
		this.state = new InfosState(this);
		this.entityType = EntityTypeEnum.HUD;
	}

	/* GETTERS E SETTERS - INICIO */

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public LocalTime getLocalTime() {
		return this.localTime;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	/* GETTERS E SETTERS - FIM */

	// Metodo que devolve o tempo de dura��o do jogo

	public int getTime() {
		return ((int) (Time.getInstance().currentTime() - localTime.getStart())) / 1000;
	}

	// Sobrescrita dos metodos abstratos da classe Entity, para tratar a
	// renderiza��o e a atualiza��o das informa�oes.

	@Override
	public void render() {
		this.state.render();
	}

	@Override
	public void update() {
		this.state.update();
	}

}
