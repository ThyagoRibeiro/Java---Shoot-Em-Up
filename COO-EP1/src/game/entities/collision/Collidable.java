package game.entities.collision;

import game.entities.Entity;

public interface Collidable {

	// Interface para ser implementada nas classes em que � necessario checar
	// colisoes.

	public boolean checkCollision(Entity other);

}
